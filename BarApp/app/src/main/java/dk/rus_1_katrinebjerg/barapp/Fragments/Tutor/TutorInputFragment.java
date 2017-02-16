package dk.rus_1_katrinebjerg.barapp.Fragments.Tutor;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

    @BindView(R.id.txtName)
    EditText txtName;

    @BindView(R.id.txtStreetname)
    EditText txtStreetName;

    @BindView(R.id.btnAddTutor)
    Button btnAddTutor;

    @BindView(R.id.btnCancelTutor)
    Button btnCancelTutor;

    public TutorInputFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tutor_input, container, false);

        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtName.setOnFocusChangeListener(Keyboard.defaultFocusListener(getActivity()));
        txtStreetName.setOnFocusChangeListener(Keyboard.defaultFocusListener(getActivity()));

        btnAddTutor.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
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

    public void addTutor(){
        Tutor tutor = new Tutor();
        tutor.name = txtName.getText().toString();
        tutor.streetName = txtStreetName.getText().toString();

        Realm.init(getContext());

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(tutor);
        realm.commitTransaction();

        clearFields();
    }

    public void clearFields(){
        txtName.setText("");
        txtStreetName.setText("");
    }
}
