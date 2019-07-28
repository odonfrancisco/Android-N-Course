/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

//    ParseObject score = new ParseObject("Score");
//    score.put("username", "odon");
//    score.put("score", 69);
//    score.saveInBackground(new SaveCallback() {
//      @Override
//      public void done(ParseException e) {
//        if(e == null){
//          Log.i("SaveInBackground", "Successful");
//        } else {
//          Log.i("SaveInBackground", "Failed. Error: " + e.toString());
//        }
//
//      }
//    });

//    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//    query.getInBackground("RyuhdUWyMN", new GetCallback<ParseObject>() {
//      @Override
//      public void done(ParseObject parseObject, ParseException e) {
//        if (e == null && parseObject != null){
//
//          parseObject.put("score", 203);
//          parseObject.saveInBackground();
//
//          Log.i("ObjectValue", parseObject.getString("username"));
//          Log.i("ObjectValue", Integer.toString(parseObject.getInt("score")));
//
//        } else {
//          Log.i("ObjectValue", "Failed to retrieve object value");
//        }
//      }
//    });

//    ParseObject tweet = new ParseObject("Tweet");
//    tweet.put("username", "chreast");
//    tweet.put("tweet", "Yeast Infection");
//
//    tweet.saveInBackground(new SaveCallback() {
//      @Override
//      public void done(ParseException e) {
//        if (e == null) {
//          Log.i("Tweet Status", "Saved Successfully");
//        } else {
//          Log.i("Tweet Status", "Failed to save");
//        }
//      }
//    });


//    ParseQuery<ParseObject> tweetQuery = ParseQuery.getQuery("Tweet");
//    tweetQuery.getInBackground("FQBlk2vv3z", new GetCallback<ParseObject>() {
//      @Override
//      public void done(ParseObject parseObject, ParseException e) {
//        if (e == null && parseObject != null){
//
//          parseObject.put("tweet", "yeast is no longer an infection");
//          parseObject.saveInBackground();
//
//
//          Log.i("Tweet Retrieved", "Username: " + parseObject.get("username"));
//
//          Log.i("Tweet Retrieved", "Tweet: " + parseObject.get("tweet"));
//        } else {
//          Log.i("Tweet Retrieved", "Failed to retrieve tweet");
//        }
//      }
//    });


    // Advanced Queries
//
//    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//    query.whereGreaterThan("score", 200);
//
//    query.findInBackground(new FindCallback<ParseObject>() {
//      @Override
//      public void done(List<ParseObject> list, ParseException e) {
//        if (e == null) {
//          Log.i("findInBackground", "Retrieved " + list.size() + " objects");
//
//          if (list.size() > 0) {
//            for(ParseObject object : list){
//              Log.i("findInBackgroundResult", object.getString("username"));
//              Log.i("findInBackgroundResult", Integer.toString(object.getInt("score")));
//
////              object.put("score", object.getInt("score") + 50);
////              object.saveInBackground();
//
//              Log.i("updated score", Integer.toString(object.getInt("score")));
//            }
//          }
//        }
//      }
//    });

    // create user

//    ParseUser user = new ParseUser();
//
//    user.setUsername("odon");
//    user.setPassword("chreast");
//
//    user.signUpInBackground(new SignUpCallback() {
//      @Override
//      public void done(ParseException e) {
//        if (e == null){
//          Log.i("Signup", "successful");
//        } else {
//          Log.i("Signup", "Failed");
//        }
//      }
//    });
//

    // log in user
//    ParseUser.logInInBackground("odon", "chreast", new LogInCallback() {
//      @Override
//      public void done(ParseUser parseUser, ParseException e) {
//        if (parseUser != null){
//          Log.i("Login", "succesful");
//        } else {
//          Log.i("Login", "Failed " + e.toString());
//        }
//      }
//    });

    // log out user
    ParseUser.logOut();

    // check if user is logged in
    if(ParseUser.getCurrentUser() != null){
      Log.i("currentUser", "User logged in " + ParseUser.getCurrentUser().getUsername());
    } else {
      Log.i("currentUser", "User not logged in");
    }


    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}