package dk.rus_1_katrinebjerg.barapp.Fragments.Tutor;

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
import dk.rus_1_katrinebjerg.barapp.Model.Tutor;
import dk.rus_1_katrinebjerg.barapp.R;
import dk.rus_1_katrinebjerg.barapp.Utils.Keyboard;
import io.realm.Realm;

public class TutorInputFragment extends Fragment {

    @BindView(R.id.editTxtName)
    EditText editTxtName;

    @BindView(R.id.editTxtStreetname)
    EditText editTxtStreetName;

    @BindView(R.id.btnAddTutor)
    Button btnAddTutor;

    @BindView(R.id.btnCancelTutor)
    Button btnCancelTutor;

    Realm realm;

    public TutorInputFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tutor_input, container, false);

        ButterKnife.bind(this, root);
        Realm.init(getContext());
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        realm = Realm.getDefaultInstance();
        editTxtName.setOnFocusChangeListener(Keyboard.defaultFocusListener(getActivity()));
        editTxtStreetName.setOnFocusChangeListener(Keyboard.defaultFocusListener(getActivity()));

        btnAddTutor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addTutor();
            }
        });
        btnCancelTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    public void addTutor() {
        Tutor tutor = new Tutor();
        int primaryKeyValue;
        if (realm.where(Tutor.class).max("id") != null) {
            primaryKeyValue = realm.where(Tutor.class).max("id").intValue() + 1;
        } else{
            primaryKeyValue = 0;
        }

        tutor.id = primaryKeyValue;
        tutor.name = editTxtName.getText().toString();
        tutor.streetName = editTxtStreetName.getText().toString();

        realm.beginTransaction();
        realm.copyToRealm(tutor);
        realm.commitTransaction();

        realm.close();
        clearFields();
    }

    public void clearFields() {
        editTxtName.setText("");
        editTxtStreetName.setText("");
    }
}
