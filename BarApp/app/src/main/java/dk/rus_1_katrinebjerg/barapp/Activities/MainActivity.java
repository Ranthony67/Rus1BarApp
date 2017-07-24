package dk.rus_1_katrinebjerg.barapp.Activities;

import android.os.Bundle;

import dk.rus_1_katrinebjerg.barapp.Activities.Base.BaseWithDrawer;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends BaseWithDrawer {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        setRealmConfiguration();
    }

    // Update realm migration
    public void setRealmConfiguration(){
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("realmConfig")
                .schemaVersion(2)
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
