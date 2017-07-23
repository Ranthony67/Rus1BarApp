package dk.rus_1_katrinebjerg.barapp.Fragments.Tutor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.rus_1_katrinebjerg.barapp.Adapters.TutorListRecycleViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Model.Tutor;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class TutorListFragment extends Fragment {

    public TutorListFragment() {}

    private RecyclerView mRecyclerView;
    private Realm realm;
    private TutorListRecycleViewAdapter tutorListRecycleViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutor_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.tutor_list_recycler_view);
        mRecyclerView.hasFixedSize();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        realm = Realm.getDefaultInstance();
        final RealmResults<Tutor> tutors = realm.where(Tutor.class).findAll();

        tutorListRecycleViewAdapter = new TutorListRecycleViewAdapter(tutors, getContext());

        tutors.addChangeListener(new RealmChangeListener<RealmResults<Tutor>>() {
            @Override
            public void onChange(RealmResults<Tutor> element) {
                tutorListRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView.setAdapter(tutorListRecycleViewAdapter);
        return view;
    }
}
























