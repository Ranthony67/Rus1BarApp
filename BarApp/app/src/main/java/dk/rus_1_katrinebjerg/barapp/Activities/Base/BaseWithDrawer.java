package dk.rus_1_katrinebjerg.barapp.Activities.Base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.HashMap;
import java.util.Map;
import dk.rus_1_katrinebjerg.barapp.Activities.CreateDrinkActivity;
import dk.rus_1_katrinebjerg.barapp.Activities.CreateRusTourActivity;
import dk.rus_1_katrinebjerg.barapp.Activities.CreateTutorActivity;
import dk.rus_1_katrinebjerg.barapp.Activities.EndRusturActivity;
import dk.rus_1_katrinebjerg.barapp.Activities.MainActivity;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;

public class BaseWithDrawer extends AppCompatActivity {

    public Drawer drawer;
    private Map<Integer, Class> activityMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        activityMap = setupFragments();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(generateDrawerItems(R.array.menu_items))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        loadActivityFromPosition(position);
                        return true;
                    }
                })
                .withToolbar(toolbar)
                .build();

        drawer.addStickyFooterItem(
                new PrimaryDrawerItem()
                        .withIdentifier(2)
                        .withName("End Trip")
                        .withTextColorRes(R.color.md_red_A700)
                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener(){
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                Intent intent = new Intent(getBaseContext(), EndRusturActivity.class);
                                startActivity(intent);

                                return true;
                            }
                        }));
    }

    public Class defaultActivity() {
        return MainActivity.class;
    }

    public Map<Integer, Class> setupFragments() {
        Map<Integer, Class> map = new HashMap<>();
        map.put(0, MainActivity.class);
        map.put(1, CreateRusTourActivity.class);
        map.put(2, CreateDrinkActivity.class);
        map.put(3, CreateTutorActivity.class);
        return map;
    }

    private IDrawerItem[] generateDrawerItems(int resourceId)
    {
        String[] menuItems = getResources().getStringArray(resourceId);
        IDrawerItem[] items = new IDrawerItem[menuItems.length];

        for (int i = 0; i < menuItems.length; i++)
        {
            String name = menuItems[i];
            items[i] = new PrimaryDrawerItem().withIdentifier(1).withName(name);
        }
        return items;
    }

    public void loadActivityFromClass(Class mClass) {
        mClass = mClass == null ? defaultActivity() : mClass;

        Activity activity = null;
        try {
            activity = (Activity) mClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(), activity.getClass());
        startActivity(intent);
    }

    public void loadActivityFromPosition(int position) {
        Class mClass = activityMap.get(position);
        loadActivityFromClass(mClass);
        drawer.closeDrawer();
    }
}
