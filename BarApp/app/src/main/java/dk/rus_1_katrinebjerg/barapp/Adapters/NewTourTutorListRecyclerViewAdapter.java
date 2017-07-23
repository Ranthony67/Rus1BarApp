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

import dk.rus_1_katrinebjerg.barapp.Model.Tutor;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmResults;

public class NewTourTutorListRecyclerViewAdapter extends RecyclerView.Adapter<NewTourTutorListRecyclerViewAdapter.ViewHolder>
{
    private final RealmResults<Tutor> mValues;
    private final Context context;
    private Realm realm;

    public NewTourTutorListRecyclerViewAdapter(RealmResults<Tutor> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tutor_list_listview, parent, false);

        NewTourTutorListRecyclerViewAdapter.ViewHolder viewHolder =
                new NewTourTutorListRecyclerViewAdapter.ViewHolder(view);
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String name = mValues.get(position).name;
        final String streetName = mValues.get(position).streetName;

        holder.txtName.setText(name);
        holder.txtStreetName.setText(streetName);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView txtStreetName;

        public ViewHolder(View itemView)
        {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.newTour_tutorListView_name);
            txtStreetName = (TextView) itemView.findViewById(R.id.newTour_tutorListView_Streetname);
        }
    }
}



































