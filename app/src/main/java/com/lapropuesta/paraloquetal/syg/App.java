package com.lapropuesta.paraloquetal.syg;

/**
 * Created by Gabriel on 29/11/2015.
        */
 import android.app.Application;
 import com.parse.Parse;

public class App extends Application {

    //Mantendra el objectID del usuario a lo largo de la aplicacion
    public static String appUser;
    //Esta clase se crea para que la inicializacion de Parse se ejecute una vez y funcione en toda
    //la aplicacion

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "pj27q68kqg9iV9mtZhLhNcZjKqgOW9z2JHqdG8OL", "KFqpMQPhS6UkTgUrBd04KziACwJwBxocjU8zoZTX");
    }

}
