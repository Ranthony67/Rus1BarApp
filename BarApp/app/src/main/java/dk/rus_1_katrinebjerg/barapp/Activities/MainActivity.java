package dk.rus_1_katrinebjerg.barapp.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.ButterKnife;
import dk.rus_1_katrinebjerg.barapp.Activities.Base.BaseWithDrawer;
import dk.rus_1_katrinebjerg.barapp.Adapters.HomeActivity_BarItem_RecyclerViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Adapters.HomeTutorRecyclerViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Adapters.RecyclerViewOnTouchListener;
import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import dk.rus_1_katrinebjerg.barapp.Model.Trip;
import dk.rus_1_katrinebjerg.barapp.Model.Tutor;
import dk.rus_1_katrinebjerg.barapp.R;
import dk.rus_1_katrinebjerg.barapp.Utils.realmConfig;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class MainActivity extends BaseWithDrawer {

    View mDialogView;
    int amount;

    public static HomeTutorRecyclerViewAdapter homeTutorRecyclerViewAdapter;
    public static RecyclerView tutorList_RecyclerView;

    HomeActivity_BarItem_RecyclerViewAdapter buyBarItemRecyclerViewAdapter;
    private RecyclerView buyBarItem_RecyclerView;
    public Realm realm;
    public RealmConfiguration config;
    private RealmList<Tutor> tutors;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Realm.init(getApplicationContext());
        realmConfig.setRealmConfiguration();
        config = realmConfig.getRealmConfiguration();
        realm = Realm.getInstance(config);

        tutorList_RecyclerView = (RecyclerView) findViewById(R.id.Home_tutorList_recycler_view);
        tutorList_RecyclerView.hasFixedSize();
        LinearLayoutManager tutorListLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        tutorList_RecyclerView.setLayoutManager(tutorListLinearLayoutManager);

        final Trip trip = realm.where(Trip.class).equalTo("isActive", true).findFirst();

        if(trip != null) {
            tutors = trip.tutors;
        }

        if(tutors != null){
            homeTutorRecyclerViewAdapter = new HomeTutorRecyclerViewAdapter(tutors, getApplicationContext());

            tutors.addChangeListener(new RealmChangeListener<RealmList<Tutor>>(){
                @Override
                public void onChange(RealmList<Tutor> tutors) {
                    homeTutorRecyclerViewAdapter.notifyDataSetChanged();
                }
            });
        }

        tutorList_RecyclerView.setAdapter(homeTutorRecyclerViewAdapter);

        tutorList_RecyclerView.addOnItemTouchListener(  new RecyclerViewOnTouchListener(getApplicationContext(), tutorList_RecyclerView,
                new RecyclerViewOnTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int pos) {
                final Tutor tutor = tutors.get(pos);
                String thisTutor = tutors.get(pos).streetName;

                AlertDialog.Builder adBuilder = new AlertDialog.Builder(MainActivity.this);
                mDialogView = MainActivity.this.getLayoutInflater().inflate(R.layout.fragment_main_activity_bar_item_alert_dialog, null);
                TextView mHeader = (TextView) mDialogView.findViewById(R.id.alertDialog_header);

                buyBarItem_RecyclerView = (RecyclerView) mDialogView.findViewById(R.id.alertDialog_barItems_RecyclerView);
                buyBarItem_RecyclerView.hasFixedSize();
                LinearLayoutManager buyBarItem_LayoutManager = new LinearLayoutManager(adBuilder.getContext());
                buyBarItem_RecyclerView.setLayoutManager(buyBarItem_LayoutManager);
                RealmList<BarItem> barItems = trip.barItems;

                buyBarItemRecyclerViewAdapter = new HomeActivity_BarItem_RecyclerViewAdapter(barItems, adBuilder.getContext());

                barItems.addChangeListener(new RealmChangeListener<RealmList<BarItem>>() {
                    @Override
                    public void onChange(RealmList<BarItem> element) {
                        buyBarItemRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });

                buyBarItem_RecyclerView.setAdapter(buyBarItemRecyclerViewAdapter);

                mHeader.setText(thisTutor + " is thirsty!!");
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
                                        tutor.barItemsBought.add(thisBarItem);
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
                                // the buy is cancelled
                                dialog.cancel();
                            }
                        });

                AlertDialog ad = adBuilder.create();
                ad.show();
            }

            @Override
            public void onLongClick(View view, int pos) {  }

        }));
    }
}

