package dk.rus_1_katrinebjerg.barapp.Fragments.RusTour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.rus_1_katrinebjerg.barapp.R;

/**
 *
 */
public class RusTurListOfBarItems extends Fragment {


    public RusTurListOfBarItems() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rus_tour_list_of_bar_items, container, false);
    }

}
