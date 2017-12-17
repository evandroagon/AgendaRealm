package br.edu.ifspsaocarlos.agenda.Apllication;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Evandro on 16/12/2017.
 */

public class CustomApplication extends Application {

    private static final String DATABASE_NAME = "agenda.realm";
    private static final long   DATABASE_VERSION = 1L;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(DATABASE_NAME)
                .schemaVersion(DATABASE_VERSION)
               // .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
