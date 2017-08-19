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
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.HashMap;
import java.util.Map;

import dk.rus_1_katrinebjerg.barapp.Activities.CreateDrinkActivity;
import dk.rus_1_katrinebjerg.barapp.Activities.CreateRusTourActivity;
import dk.rus_1_katrinebjerg.barapp.Activities.CreateTutorActivity;
import dk.rus_1_katrinebjerg.barapp.Activities.MainActivity;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;

public class BaseWithDrawer extends AppCompatActivity {

    public Drawer drawer;
    private Map<Integer, Class> fragmentMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        fragmentMap = setupFragments();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(generateDrawerItems(R.array.menu_items))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(position == 1)
                        {
                            Intent intent = new Intent(getBaseContext(), CreateRusTourActivity.class);
                            startActivity(intent);
                        }
                        else if(position == 2){
                            Intent intent = new Intent(getApplicationContext(), CreateDrinkActivity.class);
                            startActivity(intent);
                        }
                        else if(position == 3){
                            Intent intent = new Intent(getApplicationContext(), CreateTutorActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                        return true;
                    }
                })
                .withToolbar(toolbar)
                .build();
    }

    public Class defaultFragment() {
        //return HomeFragment.class;
        return null;
    }

    public Map<Integer, Class> setupFragments() {
        Map<Integer, Class> fragmentMap = new HashMap<>();
        //fragmentMap.put(0, HomeFragment.class);
        //fragmentMap.put(1, RusTourMasterFragment.class);
        //fragmentMap.put(2, DrinkMasterFragment.class);
        //fragmentMap.put(3, TutorMasterFragment.class);
        return fragmentMap;
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

    public void loadFragmentFromClass(Class mClass) {
        mClass = mClass == null ? defaultFragment() : mClass;

        Fragment fragment = null;
        try {
            fragment = (Fragment) mClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void loadFragmentFromPosition(int position) {
        Class mClass = fragmentMap.get(position);
        loadFragmentFromClass(mClass);
        drawer.closeDrawer();
    }
}
