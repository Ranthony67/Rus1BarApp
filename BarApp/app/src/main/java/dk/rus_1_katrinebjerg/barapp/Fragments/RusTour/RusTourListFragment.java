package dk.rus_1_katrinebjerg.barapp.Fragments.RusTour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.rus_1_katrinebjerg.barapp.Adapters.TourListRecycleViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Model.Trip;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class RusTourListFragment extends Fragment {

    public RusTourListFragment() { }

    private RecyclerView mRecyclerView;
    private Realm realm;
    private TourListRecycleViewAdapter tourListRecycleViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rus_tour_list_of_tours, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rus_tour_list_recycler_view);
        mRecyclerView.hasFixedSize();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        realm = Realm.getDefaultInstance();
        final RealmResults<Trip> rustours = realm.where(Trip.class).findAll();

        //TODO have to sort list the right way
        rustours.sort("name", Sort.DESCENDING);

        tourListRecycleViewAdapter = new TourListRecycleViewAdapter(rustours, getContext());

        rustours.addChangeListener(new RealmChangeListener<RealmResults<Trip>>() {
            @Override
            public void onChange(RealmResults<Trip> element) {
                tourListRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView.setAdapter(tourListRecycleViewAdapter);
        return view;
    }

}



























