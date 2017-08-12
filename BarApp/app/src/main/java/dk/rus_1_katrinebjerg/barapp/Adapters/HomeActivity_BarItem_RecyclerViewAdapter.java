package dk.rus_1_katrinebjerg.barapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import io.realm.Realm;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.RealmResults;

public class HomeActivity_BarItem_RecyclerViewAdapter extends RecyclerView.Adapter<HomeActivity_BarItem_RecyclerViewAdapter.ViewHolder>
{
    private final RealmResults<BarItem> mValues;
    private final Context context;
    private Realm realm;


    public HomeActivity_BarItem_RecyclerViewAdapter(RealmResults<BarItem> mValues, Context context)
    {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(dk.rus_1_katrinebjerg.barapp.R.layout.baritem_listview , parent, false);

        HomeActivity_BarItem_RecyclerViewAdapter.ViewHolder viewHolder = new HomeActivity_BarItem_RecyclerViewAdapter.ViewHolder(view);

        Realm.init(context);
        realm = Realm.getDefaultInstance();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeActivity_BarItem_RecyclerViewAdapter.ViewHolder holder, final int position) {
        final String name = mValues.get(position).name;
        final double price = mValues.get(position).price;

        holder.Name.setText(name);
        holder.Price.setText(String.valueOf(price));
    }



    @Override
    public int getItemCount() { return mValues.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Name;
        public TextView Price;

        public ViewHolder(View itemView)
        {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.mainActivity_BarItem_name);
            Price = (TextView) itemView.findViewById(R.id.mainActivity_BarItem_price);
        }
    }
}
