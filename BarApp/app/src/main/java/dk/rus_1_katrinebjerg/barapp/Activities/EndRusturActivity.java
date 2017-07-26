package dk.rus_1_katrinebjerg.barapp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import dk.rus_1_katrinebjerg.barapp.Activities.Base.BaseWithDrawer;
import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import dk.rus_1_katrinebjerg.barapp.Model.Trip;
import dk.rus_1_katrinebjerg.barapp.Model.Tutor;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class EndRusturActivity extends BaseWithDrawer {

    @BindView(R.id.btnEndTrip)
    Button EndTrip;

    Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_end_rustur);
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Realm.init(getApplicationContext());
        Realm.getDefaultInstance();

        EndTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setMessage("Are you sure you want to end the tour?!")
                        .setTitle("Warning - End Tour");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "The tour is now ended. GO HOME!", Toast.LENGTH_SHORT).show();
                        
                        try {
                            endTrip();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "Klatre guderne!", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void endTrip() throws IOException {

        int tripId = realm.where(Trip.class).max("id").intValue();

        Trip trip = realm.where(Trip.class).equalTo("id", tripId).findFirst();

        RealmResults<Tutor> tutors = realm.where(Tutor.class).equalTo("trip.id", tripId).findAll();
        RealmResults<BarItem> barItems = realm.where(BarItem.class).equalTo("trip.id", tripId).findAll();

        File folder = new File(Environment.getExternalStorageDirectory() + "/RusTour");
        FileWriter fw = new FileWriter(folder.toString() + trip.name + ".csv");

        fw.append("Tutor name");
        fw.append(",");
        fw.append("Total amount");
        fw.append(",");

        for(int i = 0; i < tutors.size(); i++){
            Tutor tutor = tutors.get(i);

            fw.append(tutor.name);

            double price = 0;
            for(int j = 0; j < tutor.BarItemsBought.size(); j++){
                price += tutor.BarItemsBought.get(j).price;
            }
            fw.append(String.valueOf(price));
            fw.append(",");
        }

        fw.close();
    }
}
