package com.example.phase_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jgabrielfreitas.core.BlurImageView;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Classifier extends AppCompatActivity {

    private StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseStorage storage;

    // presets for rgb conversion
    private static final int RESULTS_TO_SHOW = 3;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;

    // options for model interpreter
    private final Interpreter.Options tfliteOptions = new Interpreter.Options();
    // tflite graph
    private Interpreter tflite;
    // holds all the possible labels for model
    private List<String> labelList;
    // holds the selected image data as bytes
    private ByteBuffer imgData = null;
    // holds the probabilities of each label for non-quantized graphs
    private float[][] labelProbArray = null;
    // holds the probabilities of each label for quantized graphs
    private byte[][] labelProbArrayB = null;
    // array that holds the labels with the highest probabilities
    private String[] topLables = null;
    // array that holds the highest probabilities
    private String[] topConfidence = null;


    // selected classifier information received from extras
    private String chosen;
    private boolean quant;

    // input image dimensions for the Inception Model
    private int DIM_IMG_SIZE_X = 299;
    private int DIM_IMG_SIZE_Y = 299;
    private int DIM_PIXEL_SIZE = 3;

    // int array to hold image data
    private int[] intValues;

    // activity elements
    private BlurImageView background;
    private ImageView selected_image;
    private Button classify_button;
    private Button back_button,moredetails;
    private TextView label1 , labeltext;
    private TextView Confidence1 , Confidencetext;

    // priority queue that will hold the top results from the CNN
    private PriorityQueue<Map.Entry<String, Float>> sortedLabels =
            new PriorityQueue<>(
                    RESULTS_TO_SHOW,
                    new Comparator<Map.Entry<String, Float>>() {
                        @Override
                        public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                            return (o1.getValue()).compareTo(o2.getValue());
                        }
                    });

    OutputStream outputStream;

    long maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Cropifier</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        chosen = (String) getIntent().getStringExtra("chosen");
        quant = (boolean) getIntent().getBooleanExtra("quant", false);

        // initialize array that holds image data
        intValues = new int[DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y];

        super.onCreate(savedInstanceState);

        //initilize graph and labels
        try{
            tflite = new Interpreter(loadModelFile(), tfliteOptions);
            labelList = loadLabelList();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        // initialize byte array. The size depends if the input data needs to be quantized or not
        if(quant){
            imgData =
                    ByteBuffer.allocateDirect(
                            DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE);
        } else {
            imgData =
                    ByteBuffer.allocateDirect(
                            4 * DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE);
        }
        imgData.order(ByteOrder.nativeOrder());

        // initialize probabilities array. The datatypes that array holds depends if the input data needs to be quantized or not
        if(quant){
            labelProbArrayB= new byte[1][labelList.size()];
        } else {
            labelProbArray = new float[1][labelList.size()];
        }

        setContentView(R.layout.activity_classifier);

        label1 = (TextView) findViewById(R.id.label1);
        Confidence1 = (TextView) findViewById(R.id.Confidence1);
        labeltext = findViewById(R.id.lableText);
        Confidencetext = findViewById(R.id.ConfidenceText);
        moredetails = findViewById(R.id.moredetails);
        background = findViewById(R.id.back_imageview);

        // initialize imageView that displays selected image to the user
        selected_image = (ImageView) findViewById(R.id.selected_image);


        // initialize array to hold top labels
        topLables = new String[RESULTS_TO_SHOW];
        // initialize array to hold top probabilities
        topConfidence = new String[RESULTS_TO_SHOW];

        // allows user to go back to activity to select a different image
//        back_button = (Button)findViewById(R.id.back_button);
//        back_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(camera.this, MainActivity.class);
//                startActivity(i);
//            }
//        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        moredetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BitmapHelper.getInstance().getBitmap() == null) {
                    Toast.makeText(Classifier.this, "Bitmap is not null", Toast.LENGTH_SHORT).show();
                }else {
                    String Label = label1.getText().toString();
                    Intent intent = new Intent(Classifier.this,MoreDetails.class);
                    intent.putExtra("retrieve",Label);
                    startActivity(intent);
//                    Toast.makeText(Classifier.this, Label, Toast.LENGTH_LONG).show();

                    //firebase upload
                    uploadPicture();

                    File filePath = Environment.getExternalStorageDirectory();
                    File dir = new File(filePath.getAbsolutePath() + "/Cropifier/");
                    dir.mkdir();


                    File file = new File(dir,System.currentTimeMillis()+".jpg");
                    try {

                        outputStream = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap_save = ((BitmapDrawable) selected_image.getDrawable()).getBitmap();
                    bitmap_save.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                    Toast.makeText(getApplicationContext(), "Image Saved to Gallery!!  PATH:" +dir, Toast.LENGTH_LONG).show();
                    try {
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // classify current dispalyed image
        classify_button = (Button)findViewById(R.id.classify_image);
        classify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get current bitmap from imageView
                Bitmap bitmap_orig = ((BitmapDrawable)selected_image.getDrawable()).getBitmap();
                // resize the bitmap to the required input size to the CNN
                Bitmap bitmap = getResizedBitmap(bitmap_orig, DIM_IMG_SIZE_X, DIM_IMG_SIZE_Y);
                // convert bitmap to byte array
                convertBitmapToByteBuffer(bitmap);
                // pass byte data to the graph
                if(quant){
                    tflite.run(imgData, labelProbArrayB);
                } else {
                    tflite.run(imgData, labelProbArray);
                }
                // display the results
                printTopKLabels();
                label1.setVisibility(View.VISIBLE);
                Confidence1.setVisibility(View.VISIBLE);
                labeltext.setVisibility(View.VISIBLE);
                Confidencetext.setVisibility(View.VISIBLE);
                classify_button.setVisibility(View.INVISIBLE);
                moredetails.setVisibility(View.VISIBLE);

//                    File filePath = Environment.getExternalStorageDirectory();
//                    File dir = new File(filePath.getAbsolutePath() + "/Cropifier/");
//                    dir.mkdir();
//
//
//                    File file = new File(dir,System.currentTimeMillis()+".jpg");
//                    try {
//
//                        outputStream = new FileOutputStream(file);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    Bitmap bitmap_save = ((BitmapDrawable) selected_image.getDrawable()).getBitmap();
//                    bitmap_save.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
//                    Toast.makeText(getApplicationContext(), "Image Saved to Gallery!!  PATH:" +dir, Toast.LENGTH_LONG).show();
//                    try {
//                        outputStream.flush();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        outputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

//
            }
        });

        // get image from previous activity to show in the imageView
        Uri uri = (Uri)getIntent().getParcelableExtra("resID_uri");
        try {
//            selected_image.setImageURI(imageUri);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            selected_image.setImageBitmap(bitmap);
            background.setImageBitmap(bitmap);
            background.setBlur(5);
            BitmapHelper.getInstance().setBitmap(bitmap);
            // not sure why this happens, but without this the image appears on its side
            selected_image.setRotation(selected_image.getRotation() + 0);
            background.setRotation(background.getRotation() + 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadPicture() {
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String locCoords = sharedPreferences.getString("value", "");

        Uri uri1 = (Uri)getIntent().getParcelableExtra("resID_uri");
        String crop_id = String.valueOf(maxid+1);
        String crop = label1.getText().toString();



        final String Crop_key = crop + crop_id;


        switch (crop) {
            case "Bitter Gourd":

                StorageReference riversRef = storageReference.child("images/bitter_gourd/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Bitter_Gourd" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Chilly":

                riversRef = storageReference.child("images/chilly/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Chilly" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Coffee":

                riversRef = storageReference.child("images/coffee/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Coffee" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Cotton":

                riversRef = storageReference.child("images/cotton/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Cotton" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Lentil":

                riversRef = storageReference.child("images/lentil/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Lentil" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Maize":

                riversRef = storageReference.child("images/maize/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Maize" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Fenugreek":

                riversRef = storageReference.child("images/fenugreek/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Fenugreek" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Indian Mustard":

                riversRef = storageReference.child("images/indian_mustard/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Indian_Mustard" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Peas":

                riversRef = storageReference.child("images/peas/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Peas" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Pumpkin":

                riversRef = storageReference.child("images/pumpkin/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Pumpkin" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Rice":

                riversRef = storageReference.child("images/rice/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Rice" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Sesame":

                riversRef = storageReference.child("images/sesame/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Sesame" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Sorghum":

                riversRef = storageReference.child("images/sorghum/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Sorghum" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Soybean":

                riversRef = storageReference.child("images/soybean/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Soybean" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Sugarcane":

                riversRef = storageReference.child("images/sugarcane/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Sugarcane" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Tea":

                riversRef = storageReference.child("images/tea/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Tea" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Tobacco":

                riversRef = storageReference.child("images/tobacco/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Tobacco" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Turmeric":

                riversRef = storageReference.child("images/turmeric/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Turmeric" + crop_id).setValue(imageUploadInfo);
                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

            case "Wheat":

                riversRef = storageReference.child("images/wheat/" + Crop_key);
                riversRef.putFile(uri1)
                        .addOnSuccessListener(taskSnapshot -> {

//                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded", Snackbar.LENGTH_LONG).show();
                            uploadinfo imageUploadInfo = new uploadinfo(Crop_key, locCoords);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("Wheat" + crop_id).setValue(imageUploadInfo);

                        })
                        .addOnFailureListener(exception -> {
                            // Handle unsuccessful uploads
                            // ...
                        });
                break;

        }

    }

    // loads tflite grapg from file
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd(chosen);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // converts bitmap to byte array which is passed in the tflite graph
    private void convertBitmapToByteBuffer(Bitmap bitmap) {
        if (imgData == null) {
            return;
        }
        imgData.rewind();
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        // loop through all pixels
        int pixel = 0;
        for (int i = 0; i < DIM_IMG_SIZE_X; ++i) {
            for (int j = 0; j < DIM_IMG_SIZE_Y; ++j) {
                final int val = intValues[pixel++];
                // get rgb values from intValues where each int holds the rgb values for a pixel.
                // if quantized, convert each rgb value to a byte, otherwise to a float
                if(quant){
                    imgData.put((byte) ((val >> 16) & 0xFF));
                    imgData.put((byte) ((val >> 8) & 0xFF));
                    imgData.put((byte) (val & 0xFF));
                } else {
                    imgData.putFloat((((val >> 16) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
                    imgData.putFloat((((val >> 8) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
                    imgData.putFloat((((val) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
                }

            }
        }
    }

    // loads the labels from the label txt file in assets into a string array
    private List<String> loadLabelList() throws IOException {
        List<String> labelList = new ArrayList<String>();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(this.getAssets().open("retrained_labels_2907.txt")));
        String line;
        while ((line = reader.readLine()) != null) {
            labelList.add(line);
        }
        reader.close();
        return labelList;
    }

    // print the top labels and respective confidences
    private void printTopKLabels() {
        // add all results to priority queue
        for (int i = 0; i < labelList.size(); ++i) {
            if(quant){
                sortedLabels.add(
                        new AbstractMap.SimpleEntry<>(labelList.get(i), (labelProbArrayB[0][i] & 0xff) / 255.0f));
            } else {
                sortedLabels.add(
                        new AbstractMap.SimpleEntry<>(labelList.get(i), labelProbArray[0][i]));
            }
            if (sortedLabels.size() > RESULTS_TO_SHOW) {
                sortedLabels.poll();
            }
        }

        // get top results from priority queue
        final int size = sortedLabels.size();
        for (int i = 0; i < size; ++i) {
            Map.Entry<String, Float> label = sortedLabels.poll();
            topLables[i] = label.getKey();
            topConfidence[i] = String.format("%.0f%%",label.getValue()*100);
        }

        // set the corresponding textviews with the results
        label1.setText(""+topLables[2]);
        Confidence1.setText(topConfidence[2]);

    }


    // resizes bitmap to given dimensions
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

}
