package dk.rus_1_katrinebjerg.barapp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dk.rus_1_katrinebjerg.barapp.Activities.Base.BaseWithDrawer;
import dk.rus_1_katrinebjerg.barapp.Adapters.NewTourBarItemRecyclerViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Adapters.NewTourTutorListRecyclerViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Adapters.TourListRecycleViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import dk.rus_1_katrinebjerg.barapp.Model.Trip;
import dk.rus_1_katrinebjerg.barapp.Model.Tutor;
import dk.rus_1_katrinebjerg.barapp.R;
import dk.rus_1_katrinebjerg.barapp.Utils.Keyboard;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class CreateRusTourActivity extends BaseWithDrawer {

    @BindView(R.id.editTripName)
    EditText editTripName;

    @BindView(R.id.btnAddTrip)
    Button btnAddTrip;

    @BindView(R.id.btnCancelTrip)
    Button btnCancelTrip;

    private RecyclerView tourList_RecyclerView;
    private RecyclerView tutorList_RecyclerView;
    private RecyclerView barItemList_RecyclerView;
    private TourListRecycleViewAdapter tourListRecycleViewAdapter;
    private NewTourBarItemRecyclerViewAdapter newTourBarItemRecyclerViewAdapter;
    private NewTourTutorListRecyclerViewAdapter newTourTutorListRecyclerViewAdapter;

    private Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_create_rus_tour);
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        //_____________________________ Input Buttons and editText ______________________________
        //_______________________________________________________________________________________

        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setMessage("Are you shour that everything is added to the tour. Features can't be added when created").setTitle("Warning - Start Tour");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        addTrip();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        btnCancelTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
        //_________________________________________ End _________________________________________
        //______________________________________ Tour List ______________________________________

        tourList_RecyclerView = (RecyclerView) findViewById(R.id.rus_tour_list_recycler_view);
        tourList_RecyclerView.hasFixedSize();
        editTripName.setOnFocusChangeListener(Keyboard.defaultFocusListener(this));
        LinearLayoutManager tourListLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        tourList_RecyclerView.setLayoutManager(tourListLinearLayoutManager);
        final RealmResults<Trip> rustours = realm.where(Trip.class).findAll();

        //TODO have to sort list the right way

        tourListRecycleViewAdapter = new TourListRecycleViewAdapter(rustours, getApplicationContext());

        rustours.addChangeListener(new RealmChangeListener<RealmResults<Trip>>() {
            @Override
            public void onChange(RealmResults<Trip> element) {
                tourListRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        tourList_RecyclerView.setAdapter(tourListRecycleViewAdapter);
        //_________________________________________ End _________________________________________
        //______________________________________ Tutor List _____________________________________

        tutorList_RecyclerView = (RecyclerView) findViewById(R.id.rus_tour_tutorList_recycler_view);
        tutorList_RecyclerView.hasFixedSize();
        LinearLayoutManager tutorListLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        tutorList_RecyclerView.setLayoutManager(tutorListLinearLayoutManager);
        final RealmResults<Tutor> tutors = realm.where(Tutor.class).findAll();

        //TODO have to sort list the right way, alphabetic
        tutors.sort("name", Sort.DESCENDING);

        newTourTutorListRecyclerViewAdapter = new NewTourTutorListRecyclerViewAdapter(tutors, getApplicationContext());

        tutors.addChangeListener(new RealmChangeListener<RealmResults<Tutor>>() {
            @Override
            public void onChange(RealmResults<Tutor> element) {
                newTourTutorListRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        tutorList_RecyclerView.setAdapter(newTourTutorListRecyclerViewAdapter);

        // final CheckBox checkBox = (CheckBox)findViewById(R.id.newTour_tutorListView_checkBox);
        // final ArrayList<Integer> tutorIdList = new ArrayList<>();



        //_________________________________________ End _________________________________________
        //___________________________________ Bar Item List _____________________________________

        barItemList_RecyclerView = (RecyclerView) findViewById(R.id.rus_tour_barItem_recycler_view);
        barItemList_RecyclerView.hasFixedSize();
        LinearLayoutManager barItemListLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        barItemList_RecyclerView.setLayoutManager(barItemListLinearLayoutManager);
        final RealmResults<BarItem> barItem = realm.where(BarItem.class).findAll();

        //TODO have to sort list the right way, alphabetic
        barItem.sort("name", Sort.ASCENDING);

        newTourBarItemRecyclerViewAdapter = new NewTourBarItemRecyclerViewAdapter(barItem, getApplicationContext());

        barItem.addChangeListener(new RealmChangeListener<RealmResults<BarItem>>() {
            @Override
            public void onChange(RealmResults<BarItem> element) {
                newTourBarItemRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        barItemList_RecyclerView.setAdapter(newTourBarItemRecyclerViewAdapter);

        //_________________________________________ End _________________________________________
    }

    private void addTrip()
    {
        Trip trip = realm.where(Trip.class).equalTo("isActive", true).findFirst();

        if(trip != null){
            Intent tourCreatedIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(tourCreatedIntent);

            Toast.makeText(getApplicationContext(), "There is already an active tour.", Toast.LENGTH_SHORT).show();
            return;
        }

        trip = new Trip();
        int primaryKeyValue;
        if (realm.where(Trip.class).max("id") != null)
        {
            primaryKeyValue = realm.where(Trip.class).max("id").intValue() + 1;
        }
        else
        {
            primaryKeyValue = 0;
        }

        trip.id = primaryKeyValue;
        trip.name = editTripName.getText().toString();
        trip.isActive = true;

        realm.beginTransaction();
        realm.copyToRealm(trip);
        realm.commitTransaction();

        clearFields();

        addTutorsAndBarItemsToTour();

        // Make intent to alert activity that a tour is created  (MAinActivity have to be changed to tousMasterActivity
        Intent tourCreatedIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(tourCreatedIntent);
    }

    private void clearFields()
    {
        editTripName.setText("");
    }

    private void addTutorsAndBarItemsToTour(){
        Trip trip = realm.where(Trip.class).equalTo("isActive", true).findFirst();

        ArrayList<Integer> checkedListOfTutor = newTourTutorListRecyclerViewAdapter.getlistOfTutor();
        ArrayList<Integer> checkedListOfBarItems = newTourBarItemRecyclerViewAdapter.getlistOfBarItems();

        realm.beginTransaction();
        if(!checkedListOfTutor.isEmpty())
        {
            RealmResults<Tutor> tutors  = realm.where(Tutor.class).findAll();
            for(Tutor tutor : tutors)
            {
                if (checkedListOfTutor.contains(tutor.id))
                {
                    trip.tutors.add(tutor);
                }
            }
        }

        if(!checkedListOfBarItems.isEmpty())
        {
            RealmResults<BarItem> barItems = realm.where(BarItem.class).findAll();
            for(BarItem barItem : barItems)
            {
                if (checkedListOfBarItems.contains(barItem.id))
                {
                    trip.barItems.add(barItem);
                }
            }
        }

        realm.copyToRealm(trip);
        realm.commitTransaction();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
