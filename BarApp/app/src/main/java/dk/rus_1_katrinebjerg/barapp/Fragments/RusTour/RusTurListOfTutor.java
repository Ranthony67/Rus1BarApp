package dk.rus_1_katrinebjerg.barapp.Fragments.RusTour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.rus_1_katrinebjerg.barapp.Adapters.NewTourTutorListRecyclerViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Model.Tutor;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class RusTurListOfTutor extends Fragment {

    public RusTurListOfTutor() { }

    private RecyclerView mRecyclerView;
    private Realm realm;
    private NewTourTutorListRecyclerViewAdapter newTourTutorListRecyclerViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rus_tour_list_of_tutor, container, false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.rus_tour_tutorList_recycler_view);
        mRecyclerView.hasFixedSize();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        realm = Realm.getDefaultInstance();
        final RealmResults<Tutor> tutors = realm.where(Tutor.class).findAll();

        //TODO have to sort list the right way
        tutors.sort("name", Sort.DESCENDING);

        newTourTutorListRecyclerViewAdapter = new NewTourTutorListRecyclerViewAdapter(tutors, getContext());

        tutors.addChangeListener(new RealmChangeListener<RealmResults<Tutor>>() {
            @Override
            public void onChange(RealmResults<Tutor> element) {
                newTourTutorListRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView.setAdapter(newTourTutorListRecyclerViewAdapter);

        return view;
    }

}




























