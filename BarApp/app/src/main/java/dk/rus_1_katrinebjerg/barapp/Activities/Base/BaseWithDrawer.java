package dk.rus_1_katrinebjerg.barapp.Activities.Base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;

public class BaseWithDrawer extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        new DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(1).withName(R.string.mainActivity)
                )
                .withToolbar(toolbar)
                .build();
    }
}
