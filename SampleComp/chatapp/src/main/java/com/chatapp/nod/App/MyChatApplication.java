package com.chatapp.nod.App;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Don Chummar on 11/19/2015.
 */
public class MyChatApplication extends Application {
    public static final String YOUR_APPLICATION_ID = "pD08Bq21G2k8QVOyT1OIR5sCgJzRo0ymjhhRrD7W";
    public static final String YOUR_CLIENT_KEY = "VZNIiTY0nNkTjSzaEXUTEVpXL2iATrAlkJlnOtBy";
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
    }
}
