package c.odonfrancisco.uberclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

public class StarterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Enable Local Datastore
        Parse.enableLocalDatastore(this);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("4bb48de03c9ac1f30662af46548fb2259ef4a273")
            .clientKey("4fa488106373ec4394309c36dda5a48175361f77")
            .server("http://18.219.227.92:80/parse/")
            .build()
        );

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
