package dk.rus_1_katrinebjerg.barapp.Activities;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.ButterKnife;
import dk.rus_1_katrinebjerg.barapp.Activities.Base.BaseWithDrawer;
import dk.rus_1_katrinebjerg.barapp.Adapters.HomeActivity_BarItem_RecyclerViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Adapters.HomeTuterRecyclerViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Adapters.NewTourTutorListRecyclerViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Adapters.RecyclerViewOnTuchListener;
import dk.rus_1_katrinebjerg.barapp.Fragments.MainActivityBarItemAlertDialogFragment;
import dk.rus_1_katrinebjerg.barapp.Model.Tutor;
import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends BaseWithDrawer {

    int intentValue = 99;
    int thisTourId;
    View mDialogView;
    Tutor tutor;
    int amount;


    public static HomeTuterRecyclerViewAdapter homeTuterRecyclerViewAdapter;
    public static RecyclerView tutorList_RecyclerView;

    HomeActivity_BarItem_RecyclerViewAdapter buyBarItemRecyclerViewAdapter;
    private RecyclerView buyBarItem_RecyclerView;
    public Realm realm;

    @Override
    public void onCreate(final Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment f = getFragmentManager().findFragmentById(R.id.AlertDialogBuy);
        final FragmentManager fm = getFragmentManager();

        thisTourId = getIntent().getIntExtra("tourId",intentValue);
        Toast.makeText(getApplicationContext(), "Main Activity says Hi, rustur id is: " + Integer.toString(thisTourId), Toast.LENGTH_SHORT).show();

        tutorList_RecyclerView = (RecyclerView) findViewById(R.id.Home_tutorList_recycler_view);
        tutorList_RecyclerView.hasFixedSize();
        LinearLayoutManager tutorListLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        tutorList_RecyclerView.setLayoutManager(tutorListLinearLayoutManager);
        final RealmResults<Tutor> tutors = realm.where(Tutor.class).equalTo("tripId", thisTourId).findAll();

        tutorList_RecyclerView.addOnItemTouchListener(  new RecyclerViewOnTuchListener(getApplicationContext(), tutorList_RecyclerView,
                new RecyclerViewOnTuchListener.ClickListener() {

            @Override
            public void onClick(View view, int pos) {
                tutor = tutors.get(pos);
                String thisTuter = tutors.get(pos).streetName;

                AlertDialog.Builder adBuilder = new AlertDialog.Builder(MainActivity.this);
                mDialogView = MainActivity.this.getLayoutInflater().inflate(R.layout.fragment_main_activity_bar_item_alert_dialog, null);
                TextView mHeader = (TextView) mDialogView.findViewById(R.id.alertDialog_header);

                buyBarItem_RecyclerView = (RecyclerView) mDialogView.findViewById(R.id.alertDialog_barItems_RecyclerView);
                buyBarItem_RecyclerView.hasFixedSize();
                LinearLayoutManager buyBarItem_LayoutManager = new LinearLayoutManager(adBuilder.getContext());
                buyBarItem_RecyclerView.setLayoutManager(buyBarItem_LayoutManager);
                RealmResults<BarItem> barItems = realm.where(BarItem.class).equalTo("tripId", thisTourId).findAll();

                buyBarItemRecyclerViewAdapter = new HomeActivity_BarItem_RecyclerViewAdapter(barItems, adBuilder.getContext());

                barItems.addChangeListener(new RealmChangeListener<RealmResults<BarItem>>() {
                    @Override
                    public void onChange(RealmResults<BarItem> element) {
                        buyBarItemRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });

                buyBarItem_RecyclerView.setAdapter(buyBarItemRecyclerViewAdapter);

                mHeader.setText(thisTuter + " is thirsty!!");
                adBuilder.setView(mDialogView);

                adBuilder.setPositiveButton(
                        "Buy",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HashMap<Integer,Integer> boughtBarItems = buyBarItemRecyclerViewAdapter.getBarItemsBought();

                                // for each baritem bought add BarItem on barItemBought list at tutor
                                for (Integer key : boughtBarItems.keySet()) {
                                    // get barItem with key
                                    BarItem thisBarItem = realm.where(BarItem.class).equalTo("id", key).findFirst();

                                    realm.beginTransaction();
                                    // add all to list of items bought (e.g. amount = 5)
                                    for(amount = boughtBarItems.get(key) ; amount > 0 ; --amount){
                                        tutor.BarItemsBought.add(thisBarItem);
                                    }

                                    realm.copyToRealm(tutor);
                                    realm.commitTransaction();
                                }
                                Toast.makeText(getApplicationContext(), "Cheers god sir", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });
                adBuilder.setNegativeButton(
                        "Back out",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(), "What a WUZZ!", Toast.LENGTH_SHORT).show();
                                // the buy is cancled, do nothing
                                dialog.cancel();
                            }
                        });

                AlertDialog ad = adBuilder.create();
                ad.show();
            }

            @Override
            public void onLongClick(View view, int pos) {  }

        }));

        homeTuterRecyclerViewAdapter = new HomeTuterRecyclerViewAdapter(tutors, getApplicationContext());

        tutors.addChangeListener(new RealmChangeListener<RealmResults<Tutor>>() {
            @Override
            public void onChange(RealmResults<Tutor> element) {
                homeTuterRecyclerViewAdapter.notifyDataSetChanged();
            }
        });


        tutorList_RecyclerView.setAdapter(homeTuterRecyclerViewAdapter);
        setRealmConfiguration();
    }

    // Update realm migration
    public void setRealmConfiguration(){
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("realmConfig")
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(config);
    }
}

