package com.lapropuesta.paraloquetal.syg;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
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

    public static Bitmap getclip(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

//    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
//        int width = bm.getWidth();
//        int height = bm.getHeight();
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//        // CREATE A MATRIX FOR THE MANIPULATION
//        Matrix matrix = new Matrix();
//        // RESIZE THE BIT MAP
//        matrix.postScale(scaleWidth, scaleHeight);
//
//        // "RECREATE" THE NEW BITMAP
//        Bitmap resizedBitmap = Bitmap.createBitmap(
//                bm, 0, 0, width, height, matrix, false);
//        bm.recycle();
//        return resizedBitmap;
//    }

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
                ParseFile fileObject = object.getParseFile("userimg");


                fileObject.getDataInBackground(new GetDataCallback() {
                    public void done(byte[] data, ParseException e) {
                        if (e == null) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                            // Get the ImageView from
                            // main.xml
                            ImageView image = (ImageView) findViewById(R.id.profile_picture);
                            // Set the Bitmap into the
                            // ImageView
                            image.setImageBitmap(getclip(bmp));
                        } else {
                            ImageView image = (ImageView) findViewById(R.id.profile_picture);
//                            image.setImageDrawable(getDrawable(R.drawable.sg));
                            //  Log.d("score", "Error: " + e.getMessage());
                        }
                    }

                });
            }
        });
    }
}