package dk.rus_1_katrinebjerg.barapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import dk.rus_1_katrinebjerg.barapp.Activities.Base.BaseWithDrawer;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends BaseWithDrawer {

    // ####### Have to be mooved to RusTourMasterFragment when this is changed to Activity
    int intentValue = 99;
    int thisTourId;
    int oldTourId;
    //RusTurListOfTutor rusTurListOfTutor;
    // #######


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        //rusTurListOfTutor = new RusTurListOfTutor();

        // ####### Have to be mooved to RusTourMasterFragment when this is changed to Activity
        thisTourId = getIntent().getIntExtra("tourId",intentValue);

        if(thisTourId != intentValue && thisTourId != oldTourId)
        {
            // insert turId to tutors sceleted in tutor fragment
            SetTurIdAtTuters(thisTourId);
            // insert turId to barItems sceleted in barItems fragment
            SetTurIdAtBarItems(thisTourId);
            // inshure that this is only done once
            oldTourId = thisTourId;
        }
        // #######

        setRealmConfiguration();
    }

    // Update realm migration
    public void setRealmConfiguration(){
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("realmConfig")
                .schemaVersion(3)
                .build();

        Realm.setDefaultConfiguration(config);
    }
    private void SetTurIdAtTuters(int thisTourId)
    {
        Toast.makeText(getApplicationContext(), "SetTurIdAtTuters_MainActivity", Toast.LENGTH_SHORT).show();

    }

    private void SetTurIdAtBarItems(int thisTourId)
    {

    }
}
