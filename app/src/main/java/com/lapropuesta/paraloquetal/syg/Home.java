package com.lapropuesta.paraloquetal.syg;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by gguzman on 10/19/15.
 */
public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        //query.fromLocalDatastore();
        // query.whereEqualTo("playerName", "Dan Stemkoski");

        query.getInBackground("72AJ7gyRc5", new GetCallback<ParseObject>() {

            public void done(ParseObject object, ParseException e) {
                // Locate the column named "ImageName" and set
                // the string
                Toast.makeText(getBaseContext(),object.get("username").toString()+object.get("email").toString(),Toast.LENGTH_LONG).show();
                ParseFile fileObject = (ParseFile) object.get("usrimg");


//                fileObject.getDataInBackground(new GetDataCallback() {
//                    public void done(byte[] data, ParseException e) {
//                        if (e == null) {
//                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//                            // Get the ImageView from
//                            // main.xml
//                            ImageView image = (ImageView) findViewById(R.id.profile_picture);
//                            // Set the Bitmap into the
//                            // ImageView
//                            image.setImageBitmap(bmp);
//                        } else {
//                            ImageView image = (ImageView) findViewById(R.id.profile_picture);
//                            image.setImageDrawable(getDrawable(R.drawable.sg));
//                            //  Log.d("score", "Error: " + e.getMessage());
//                        }
//                    }
//
//                });
            }
        });
    }
}