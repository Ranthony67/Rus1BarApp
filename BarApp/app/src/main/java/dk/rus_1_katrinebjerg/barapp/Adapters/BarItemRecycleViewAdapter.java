package dk.rus_1_katrinebjerg.barapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmResults;

public class BarItemRecycleViewAdapter extends RecyclerView.Adapter<BarItemRecycleViewAdapter.ViewHolder>{

    private final RealmResults<BarItem> mValues;
    private final Context context;
    private Realm realm;

    public BarItemRecycleViewAdapter(RealmResults<BarItem> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.baritem_list_cardview, parent, false);

        BarItemRecycleViewAdapter.ViewHolder viewHolder = new BarItemRecycleViewAdapter.ViewHolder(view);
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int id = mValues.get(position).id;
        final String name = mValues.get(position).name;
        final String price = Double.toString(mValues.get(position).price);

        holder.txtBarItemName.setText(name);
        holder.txtBarItemPrice.setText(price);

        holder.btnDeleteBarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                RealmResults<BarItem> barItem = realm.where(BarItem.class).equalTo("id", id).findAll();
                barItem.deleteAllFromRealm();
                realm.commitTransaction();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtBarItemName;
        public TextView txtBarItemPrice;
        public ImageButton btnEditBarItem;
        public ImageButton btnDeleteBarItem;

        public ViewHolder(View itemView) {
            super(itemView);

            txtBarItemName = (TextView) itemView.findViewById(R.id.txtBarItemName);
            txtBarItemPrice = (TextView) itemView.findViewById(R.id.txtBarItemPrice);
            btnEditBarItem = (ImageButton) itemView.findViewById(R.id.btnEditBarItem);
            btnDeleteBarItem = (ImageButton) itemView.findViewById(R.id.btnDeleteBarItem);
        }
    }
}
