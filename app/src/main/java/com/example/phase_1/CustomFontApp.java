package com.example.phase_1;

import android.app.Application;


public class CustomFontApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OverrideFonts.setDefaultFont(this, "Sans", "fonts/google1.ttf");
    }
}
