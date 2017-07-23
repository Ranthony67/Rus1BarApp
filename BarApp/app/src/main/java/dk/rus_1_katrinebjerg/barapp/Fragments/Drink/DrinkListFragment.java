package dk.rus_1_katrinebjerg.barapp.Fragments.Drink;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import dk.rus_1_katrinebjerg.barapp.Adapters.BarItemRecycleViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class DrinkListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Realm realm;
    private BarItemRecycleViewAdapter barItemListRecycleViewAdapter;

    public DrinkListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drink_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.baritem_list_recycler_view);
        mRecyclerView.hasFixedSize();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        realm = Realm.getDefaultInstance();
        final RealmResults<BarItem> barItems = realm.where(BarItem.class).findAll();

        barItemListRecycleViewAdapter = new BarItemRecycleViewAdapter(barItems, getContext());

        barItems.addChangeListener(new RealmChangeListener<RealmResults<BarItem>>() {
            @Override
            public void onChange(RealmResults<BarItem> element) {
                barItemListRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView.setAdapter(barItemListRecycleViewAdapter);

        return view;
    }

}
