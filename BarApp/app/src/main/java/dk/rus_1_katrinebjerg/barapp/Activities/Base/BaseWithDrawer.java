package dk.rus_1_katrinebjerg.barapp.Activities.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.HashMap;
import java.util.Map;

import dk.rus_1_katrinebjerg.barapp.Activities.CreateRusturActivity;
import dk.rus_1_katrinebjerg.barapp.Activities.DrinkActivity;
import dk.rus_1_katrinebjerg.barapp.Activities.MainActivity;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;

public class BaseWithDrawer extends AppCompatActivity {

    public Drawer drawer;
    private Map<Integer, Class> ActivityMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        ActivityMap = setupActivities();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(generateDrawerItems(R.array.menu_items))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        LoadActivity(position);
                        return true;
                    }
                })
                .withToolbar(toolbar)
                .build();
    }

    public Map<Integer, Class> setupActivities(){
        Map<Integer, Class> ActivityMap = new HashMap<>();
        ActivityMap.put(0, MainActivity.class);
        ActivityMap.put(1, CreateRusturActivity.class);
        ActivityMap.put(2, DrinkActivity.class);
        return ActivityMap;
    }

    private IDrawerItem[] generateDrawerItems(int resourceId) {
        String[] menuItems = getResources().getStringArray(resourceId);
        IDrawerItem[] items = new IDrawerItem[menuItems.length];

        for (int i = 0; i < menuItems.length; i++) {
            String name = menuItems[i];
            items[i] = new PrimaryDrawerItem().withIdentifier(1).withName(name);
        }
        return items;
    }

    private void LoadActivity(int pos){
        final String[] menuItems = getResources().getStringArray(R.array.menu_items);
        String title = menuItems[pos];
        Class mClass = ActivityMap.get(pos);

        Intent intent = new Intent(this, mClass);
        intent.putExtra("TITLE", title);
        startActivity(intent);
    }
}
