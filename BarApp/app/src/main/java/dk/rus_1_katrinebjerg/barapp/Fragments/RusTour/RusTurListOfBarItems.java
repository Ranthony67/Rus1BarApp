package dk.rus_1_katrinebjerg.barapp.Fragments.RusTour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.rus_1_katrinebjerg.barapp.Adapters.NewTourBarItemRecyclerViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class RusTurListOfBarItems extends Fragment {

    public RusTurListOfBarItems() { }

    private RecyclerView mRecyclerView;
    private Realm realm;
    private NewTourBarItemRecyclerViewAdapter newTourBarItemRecyclerViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rus_tour_list_of_bar_items, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rus_tour_barItem_recycler_view);
        mRecyclerView.hasFixedSize();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        realm = Realm.getDefaultInstance();
        final RealmResults<BarItem> barItem = realm.where(BarItem.class).findAll();

        //TODO have to sort list the right way, alphabetic
        barItem.sort("name", Sort.ASCENDING);

        newTourBarItemRecyclerViewAdapter = new NewTourBarItemRecyclerViewAdapter(barItem, getContext());

        barItem.addChangeListener(new RealmChangeListener<RealmResults<BarItem>>() {
            @Override
            public void onChange(RealmResults<BarItem> element) {
                newTourBarItemRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView.setAdapter(newTourBarItemRecyclerViewAdapter);


        return view;
    }

}















































