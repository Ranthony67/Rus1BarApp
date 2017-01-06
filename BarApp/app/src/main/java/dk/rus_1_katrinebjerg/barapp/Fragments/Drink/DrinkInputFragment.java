package dk.rus_1_katrinebjerg.barapp.Fragments.Drink;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.rus_1_katrinebjerg.barapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkInputFragment extends Fragment {


    public DrinkInputFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drink_input, container, false);
    }

}
