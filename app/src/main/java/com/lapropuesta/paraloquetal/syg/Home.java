package com.lapropuesta.paraloquetal.syg;

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
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
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
public class Home extends FragmentActivity {

    ListView plistview;
    ListView rwlistview;
    List<ParseObject> pob;
    List<ParseObject> rwob;
    ProgressDialog mProgressDialog;
    ArrayList<Points> pArrayList;
    ArrayList<Rewards> rwArrayList;
    ViewPager viewPager;
    String userID = App.appUser;

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
            // Locate the class table named "Points" in Parse.com
            ParseQuery<ParseObject> pquery = new ParseQuery<>(
                    "Points");
            pquery.whereEqualTo("owner",userID);
            pquery.orderByDescending("createdAt");
            // Locate the class table named "Rewards" in Parse.com
            ParseQuery<ParseObject> rwquery = new ParseQuery<>(
                    "Rewards");
            rwquery.whereEqualTo("owner",userID);
            rwquery.orderByDescending("createdAt");

            try {
                pob = pquery.find();
                rwob = rwquery.find();
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            plistview = (ListView) findViewById(R.id.PointsListView);
            rwlistview = (ListView) findViewById(R.id.RewardsListView);
            //Crea el array de tipo Points
            pArrayList = new ArrayList<>();
            rwArrayList = new ArrayList<>();

            // Obtiene los datos de cada registro de puntaje y los añade al array
            Points pt;
            for (ParseObject puntos : pob) {
                pt = new Points();
                pt.setDescription((String) puntos.get("description"));
                pt.setPoints(puntos.get("amount").toString());
                pt.setPointsDate((String) puntos.get("giver"));
                pArrayList.add(pt);
            }
            // Binds the Adapter to the ListView
            plistview.setAdapter(new PointsAdapter(Home.this, pArrayList));

            // Obtiene los datos de cada registro de premios y los añade al array
            Rewards rw;
            for (ParseObject premios : rwob) {
                rw = new Rewards();
                rw.setDescription((String) premios.get("description"));
                rw.setPoints(premios.get("amount").toString());
                rw.setPointsDate((String) premios.get("giver"));
                rwArrayList.add(rw);
            }
            // Close the progressdialog
            mProgressDialog.dismiss();
            // Capture button clicks on ListView items
            plistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // Send single item click data to DetallePuntos Class
                    Intent i = new Intent(Home.this,
                            DetallePuntos.class);
                    // Pass data "description" followed by the position
                    i.putExtra("description", plistview.getItemAtPosition(position).toString());
                    // Open DetallePuntos.java Activity
                    startActivity(i);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager(),
                Home.this));

        // Give the TabLayout the ViewPager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        //query.fromLocalDatastore();
        // query.whereEqualTo("playerName", "Dan Stemkoski");

        query.getInBackground(userID, new GetCallback<ParseObject>() {

            public void done(ParseObject object, ParseException e) {
                // Locate the column named "userimg" and set the string
                Toast.makeText(getBaseContext(), object.get("username").toString() + object.get("email").toString(), Toast.LENGTH_LONG).show();
                ParseFile fileObject = object.getParseFile("userimg");

                fileObject.getDataInBackground(new GetDataCallback() {
                    public void done(byte[] data, ParseException e) {
                        if (e == null) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                            // Get the ImageView from main.xml
                            ImageView image = (ImageView) findViewById(R.id.profile_picture);
                            // Set the Bitmap into the ImageView. El getclip es para que sea redonda
                            image.setImageBitmap(getclip(bmp));
                        } else {
                            ImageView image = (ImageView) findViewById(R.id.profile_picture);
                        }
                    }
                });
            }
        });
        //TODO: Revisar esto, funciona pero debe poder hacerse mejor
        //Listener añadido para permitir la carga del listview del segundo fragmento
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Si es la tab de premios, llena el listview correspondiente
                if (tab.getPosition() == 1)
                    rwlistview.setAdapter(new RewardsAdapter(Home.this, rwArrayList));
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        new PointsDataTask().execute();

        //TODO: Este botón debería dentro de un menú, por ahora se muestra así dado que es la única opción
        //Botón para cerrar sesión
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