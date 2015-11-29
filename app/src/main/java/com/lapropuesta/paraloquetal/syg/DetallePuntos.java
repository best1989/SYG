package com.lapropuesta.paraloquetal.syg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Gabriel on 08/11/2015.
 */
public class DetallePuntos extends Activity {

    TextView txtname;
    String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.detalle_puntos);

        // Retrieve data from MainActivity on item click event
        Intent i = getIntent();

        // Get the name
        name = i.getStringExtra("description");

        // Locate the TextView in singleitemview.xml
        txtname = (TextView) findViewById(R.id.titulo);

        // Load the text into the TextView
        txtname.setText(name);

    }
}
