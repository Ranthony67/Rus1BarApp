package dk.rus_1_katrinebjerg.barapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import dk.rus_1_katrinebjerg.barapp.Model.Trip;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmResults;

public class TourListRecycleViewAdapter extends RecyclerView.Adapter<TourListRecycleViewAdapter.ViewHolder>
{
    private final RealmResults<Trip> mValues;
    private final Context context;
    private Realm realm;

    public TourListRecycleViewAdapter(RealmResults<Trip> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tour_list_carsview, parent, false);

        TourListRecycleViewAdapter.ViewHolder viewHolder =
                new TourListRecycleViewAdapter.ViewHolder(view);
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final int id = mValues.get(position).id;
        final String name = mValues.get(position).name;

        holder.txtName.setText(name);

        holder.btnEditTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btnDeleteTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                RealmResults<Trip> tutor = realm.where(Trip.class).equalTo("id", id ).findAll();
                tutor.deleteAllFromRealm();
                realm.commitTransaction();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public ImageButton btnEditTour;
        public ImageButton btnDeleteTour;


        public ViewHolder(View itemView)
        {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtTourname);
            btnEditTour = (ImageButton) itemView.findViewById(R.id.btnEditTour);
            btnDeleteTour = (ImageButton) itemView.findViewById(R.id.btnDeleteTutor);
        }
    }
}
