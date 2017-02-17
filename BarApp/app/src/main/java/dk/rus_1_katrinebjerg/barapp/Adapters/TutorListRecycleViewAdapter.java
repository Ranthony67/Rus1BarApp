package dk.rus_1_katrinebjerg.barapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import dk.rus_1_katrinebjerg.barapp.Model.Tutor;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.RealmResults;

public class TutorListRecycleViewAdapter extends RecyclerView.Adapter<TutorListRecycleViewAdapter.ViewHolder> {

    private final RealmResults<Tutor> mValues;

    public TutorListRecycleViewAdapter(RealmResults<Tutor> mValues) {
        this.mValues = mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tutor_list_cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtName.setText(mValues.get(position).name);
        holder.txtStreetName.setText(mValues.get(position).streetName);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView txtStreetName;
        public Button btnEditTutor;
        public Button btnDeleteTutor;

        public ViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtStreetName = (TextView) itemView.findViewById(R.id.txtStreetname);
            btnEditTutor = (Button) itemView.findViewById(R.id.btnEditTutor);
            btnDeleteTutor = (Button) itemView.findViewById(R.id.btnDeleteTutor);
        }
    }
}
