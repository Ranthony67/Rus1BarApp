package dk.rus_1_katrinebjerg.barapp.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import dk.rus_1_katrinebjerg.barapp.Activities.Base.BaseWithDrawer;
import dk.rus_1_katrinebjerg.barapp.Adapters.BarItemRecycleViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import dk.rus_1_katrinebjerg.barapp.R;
import dk.rus_1_katrinebjerg.barapp.Utils.Keyboard;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class CreateDrinkActivity extends BaseWithDrawer {

    @BindView(R.id.drinkName)
    EditText editTxtBarItemName;

    @BindView(R.id.drinkPrice)
    EditText editTxtBarItemPrice;

    @BindView(R.id.btnAddDrink)
    Button btnAddDrink;

    @BindView(R.id.btnCancelDrink)
    Button btnCancelDrink;

    Realm realm;
    RecyclerView mRecyclerView;
    BarItemRecycleViewAdapter barItemListRecycleViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_drink);
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        editTxtBarItemName.setOnFocusChangeListener(Keyboard.defaultFocusListener(this));
        editTxtBarItemPrice.setOnFocusChangeListener(Keyboard.defaultFocusListener(this ));

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

        mRecyclerView = (RecyclerView) findViewById(R.id.baritem_list_recycler_view);
        mRecyclerView.hasFixedSize();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        final RealmResults<BarItem> barItems = realm.where(BarItem.class).findAll();

        barItemListRecycleViewAdapter = new BarItemRecycleViewAdapter(barItems, getApplicationContext());

        barItems.addChangeListener(new RealmChangeListener<RealmResults<BarItem>>() {
            @Override
            public void onChange(RealmResults<BarItem> element) {
                barItemListRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView.setAdapter(barItemListRecycleViewAdapter);
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
