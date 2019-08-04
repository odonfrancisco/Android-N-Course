package c.odonfrancisco.instagramclone;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    ParseUser user;
//    ArrayList<String> imageUrls;
    ArrayList<Bitmap> imageBitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        imageBitmaps = new ArrayList<>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        try {
            user = query.get(getIntent().getStringExtra("id"));
            Toast.makeText(getApplicationContext(), "User found: " + user.getUsername(), Toast.LENGTH_SHORT).show();

            setTitle(user.getUsername().toUpperCase() + "'s Feed");

            getUserPhotos();

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Unable to find the user you are looking for", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserPhotos(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");
        query.whereEqualTo("userID", user.getObjectId());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                for(final ParseObject file : objects){
                        Log.i("number of images", Integer.toString(objects.size()));
                        ParseFile image = (ParseFile) file.get("image");
//                        String url = image.getUrl();
//                        imageUrls.add(url);
                        image.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e == null & data != null) {
//                                    ImageView iv = new ImageView(getApplicationContext());

                                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                                    imageBitmaps.add(bmp);
//                                    iv.setImageBitmap(bmp);
//                                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                    lp.addRule(RelativeLayout.BELOW);
//                                    iv.setLayoutParams(lp);
//                                    RelativeLayout rl = findViewById(R.id.relativeLayout);
//                                    int childrenSize = rl.getChildCount();
//
//                                    if (rl.getChildAt(childrenSize) == null) {
//                                        rl.addView(iv);
//                                    } else {
//                                        rl.addView(iv, rl.getChildAt(childrenSize).getId());
//                                    }

                                    if(objects.get(objects.size()-1) == file){
                                        setRecyclerView();
                                    }

                                } else {
                                    Log.i("Failure", "Downloading the data");
                                }
                            }

                        });
                }
            }
        });
    }

    private void setRecyclerView(){
        Log.i("RecyclerViewSet", "true");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(imageBitmaps, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}
