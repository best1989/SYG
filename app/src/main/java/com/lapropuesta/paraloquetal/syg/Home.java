package com.lapropuesta.paraloquetal.syg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gguzman on 10/19/15.
 */
public class Home extends Activity {

    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ArrayList<Points> pArrayList;

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

    // RemoteDataTask AsyncTask
    private class PointsDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Home.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Obteniendo datos");
            // Set progressdialog message
            mProgressDialog.setMessage("Cargando...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the class table named "Country" in Parse.com
            ParseQuery<ParseObject> query = new ParseQuery<>(
                    "Points");
            query.orderByDescending("createdAt");
            try {
                ob = query.find();
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listView);
            pArrayList = new ArrayList<>();
            // Pass the results into an ArrayAdapter

            // Retrieve object "name" from Parse.com database
            Points pt;
            for (ParseObject puntos : ob) {
                pt = new Points();
                pt.setDescription((String) puntos.get("description"));
                pt.setPoints(puntos.get("amount").toString());
                pt.setPointsDate((String) puntos.get("giver"));
                pArrayList.add(pt);
            }
            // Binds the Adapter to the ListView
            listview.setAdapter(new PointsAdapter(Home.this, pArrayList));
            // Close the progressdialog
            mProgressDialog.dismiss();
            // Capture button clicks on ListView items
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // Send single item click data to SingleItemView Class
                    Intent i = new Intent(Home.this,
                            DetallePuntos.class);
                    // Pass data "name" followed by the position
                    i.putExtra("description", listview.getItemAtPosition(position).toString());
                    // Open SingleItemView.java Activity
                    startActivity(i);
                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        //query.fromLocalDatastore();
        // query.whereEqualTo("playerName", "Dan Stemkoski");

        query.getInBackground("72AJ7gyRc5", new GetCallback<ParseObject>() {

            public void done(ParseObject object, ParseException e) {
                // Locate the column named "ImageName" and set the string
                Toast.makeText(getBaseContext(), object.get("username").toString() + object.get("email").toString(), Toast.LENGTH_LONG).show();
                ParseFile fileObject = object.getParseFile("userimg");

                fileObject.getDataInBackground(new GetDataCallback() {
                    public void done(byte[] data, ParseException e) {
                        if (e == null) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                            // Get the ImageView from main.xml
                            ImageView image = (ImageView) findViewById(R.id.profile_picture);
                            // Set the Bitmap into the ImageView
                            image.setImageBitmap(getclip(bmp));
                        } else {
                            ImageView image = (ImageView) findViewById(R.id.profile_picture);
                        }
                    }
                });
            }
        });
        new PointsDataTask().execute();

        Button logoutBt = (Button) findViewById(R.id.logout_bt);

        logoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout(){
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}