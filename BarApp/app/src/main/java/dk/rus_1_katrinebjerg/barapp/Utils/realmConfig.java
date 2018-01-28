package dk.rus_1_katrinebjerg.barapp.Utils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

// Class used for setting and getting the Realm Configuration
public class realmConfig {
    private static RealmConfiguration config;

    // Set realm configuration
    public static void setRealmConfiguration(){
        config = new RealmConfiguration.Builder()
                .name("realmConfig")
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(config);
    }

    public static RealmConfiguration getRealmConfiguration(){
        return config;
    }
}
