package dk.rus_1_katrinebjerg.barapp.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.ButterKnife;
import dk.rus_1_katrinebjerg.barapp.Activities.Base.BaseWithDrawer;
import dk.rus_1_katrinebjerg.barapp.Adapters.HomeActivity_BarItem_RecyclerViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Adapters.HomeTuterRecyclerViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Adapters.NewTourTutorListRecyclerViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Adapters.RecyclerViewOnTuchListener;
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

    public static HomeTuterRecyclerViewAdapter homeTuterRecyclerViewAdapter;
    public static RecyclerView tutorList_RecyclerView;
    public Realm realm;

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();


        thisTourId = getIntent().getIntExtra("tourId",intentValue);
        Toast.makeText(getApplicationContext(), "Main Activity says Hi, rustur id is: " + Integer.toString(thisTourId), Toast.LENGTH_SHORT).show();

        tutorList_RecyclerView = (RecyclerView) findViewById(R.id.Home_tutorList_recycler_view);
        tutorList_RecyclerView.hasFixedSize();
        LinearLayoutManager tutorListLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        tutorList_RecyclerView.setLayoutManager(tutorListLinearLayoutManager);
        final RealmResults<Tutor> tutors = realm.where(Tutor.class).equalTo("tripId", thisTourId).findAll();

        tutorList_RecyclerView.addOnItemTouchListener(  new RecyclerViewOnTuchListener(getApplicationContext(), tutorList_RecyclerView, new RecyclerViewOnTuchListener.ClickListener() {

            @Override
            public void onClick(View view, int pos) {
                Toast.makeText(getApplicationContext(), "onClick mm", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int pos) {
                Toast.makeText(getApplicationContext(), "onLongClick mm", Toast.LENGTH_SHORT).show();
            }


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


    public static class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int pos = tutorList_RecyclerView.indexOfChild(v);
            Toast.makeText(v.getContext(), "MyOnClickListener, pos: " + pos, Toast.LENGTH_SHORT).show();
        }
    }

}
