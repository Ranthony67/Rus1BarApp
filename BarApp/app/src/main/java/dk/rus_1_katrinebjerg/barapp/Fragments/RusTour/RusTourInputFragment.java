package dk.rus_1_katrinebjerg.barapp.Fragments.RusTour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import dk.rus_1_katrinebjerg.barapp.Activities.MainActivity;
import dk.rus_1_katrinebjerg.barapp.Model.Trip;
import dk.rus_1_katrinebjerg.barapp.R;
import dk.rus_1_katrinebjerg.barapp.Utils.Keyboard;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class RusTourInputFragment extends Fragment {

    @BindView(R.id.editTripName)
    EditText editTripName;

    @BindView(R.id.btnAddTrip)
    Button btnAddTrip;

    @BindView(R.id.btnCancelTrip)
    Button btnCancelTrip;

    Realm realm;

    public RusTourInputFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_rus_tour_input, container, false);

        ButterKnife.bind(this, root);
        Realm.init(getContext());
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        realm = Realm.getDefaultInstance();
        editTripName.setOnFocusChangeListener(Keyboard.defaultFocusListener(getActivity()));

        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addTrip();
            }
        });
        btnCancelTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    private void addTrip()
    {
        Trip trip = new Trip();
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

        realm.beginTransaction();
        realm.copyToRealm(trip);
        realm.commitTransaction();

        realm.close();
        clearFields();
    }

    private void clearFields()
    {
        editTripName.setText("");
    }
}
