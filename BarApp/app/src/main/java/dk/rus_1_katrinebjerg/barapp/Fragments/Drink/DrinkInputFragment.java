package dk.rus_1_katrinebjerg.barapp.Fragments.Drink;


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
import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import dk.rus_1_katrinebjerg.barapp.R;
import dk.rus_1_katrinebjerg.barapp.Utils.Keyboard;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkInputFragment extends Fragment {

    @BindView(R.id.drinkName)
    EditText editTxtBarItemName;

    @BindView(R.id.drinkPrice)
    EditText editTxtBarItemPrice;

    @BindView(R.id.btnAddDrink)
    Button btnAddDrink;

    @BindView(R.id.btnCancelDrink)
    Button btnCancelDrink;

    Realm realm;

    public DrinkInputFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_drink_input, container, false);
        ButterKnife.bind(this, root);
        Realm.init(getContext());
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);

        realm = Realm.getDefaultInstance();
        editTxtBarItemName.setOnFocusChangeListener(Keyboard.defaultFocusListener(getActivity()));
        editTxtBarItemPrice.setOnFocusChangeListener(Keyboard.defaultFocusListener(getActivity()));

        btnAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDrink();
            }
        });
        btnCancelDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
            }
        });
    }

    private void addDrink() {
        BarItem barItem = new BarItem();
        int primaryKeyValue;
        if (realm.where(BarItem.class).max("id") != null) {
            primaryKeyValue = realm.where(BarItem.class).max("id").intValue() + 1;
        } else{
            primaryKeyValue = 0;
        }

        barItem.id = primaryKeyValue;
        barItem.name = editTxtBarItemName.getText().toString();
        barItem.price = Double.parseDouble(editTxtBarItemPrice.getText().toString());

        realm.beginTransaction();
        realm.copyToRealm(barItem);
        realm.commitTransaction();

        realm.close();
        clearFields();
    }

    public void clearFields() {
        editTxtBarItemName.setText("");
        editTxtBarItemPrice.setText("");
    }
}
