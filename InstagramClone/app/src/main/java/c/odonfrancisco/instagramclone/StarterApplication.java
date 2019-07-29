package c.odonfrancisco.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

public class StarterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore
        Parse.enableLocalDatastore(this);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("0ad98b51540098f7e7e6e395b3d30f9f76ac1d7b")
            .clientKey("4d2020694aa21618f7d18c9fb851d57bd8fa7b97")
            .server("http://3.15.19.71:80/parse/")
            .build()
        );

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
