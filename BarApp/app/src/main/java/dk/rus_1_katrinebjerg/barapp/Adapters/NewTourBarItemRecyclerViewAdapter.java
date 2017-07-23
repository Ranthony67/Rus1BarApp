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

import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmResults;

public class NewTourBarItemRecyclerViewAdapter extends RecyclerView.Adapter<NewTourBarItemRecyclerViewAdapter.ViewHolder>
{
    private final RealmResults<BarItem> mValues;
    private final Context context;
    private Realm realm;

    public NewTourBarItemRecyclerViewAdapter(RealmResults<BarItem> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.baritem_list_listview, parent, false);

        NewTourBarItemRecyclerViewAdapter.ViewHolder viewHolder =
                new NewTourBarItemRecyclerViewAdapter.ViewHolder(view);

        Realm.init(context);
        realm = Realm.getDefaultInstance();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String name = mValues.get(position).name;
        final String price = Double.toString(mValues.get(position).price);

        holder.txtName.setText(name);
        holder.txtPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView txtPrice;

        public ViewHolder(View itemView)
        {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.newTour_tutorListView_BarItemName);
            txtPrice = (TextView) itemView.findViewById(R.id.newTour_tutorListView_BarItemPrice);
        }
    }
}
