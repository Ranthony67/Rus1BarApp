package dk.rus_1_katrinebjerg.barapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmResults;

public class NewTourBarItemRecyclerViewAdapter extends RecyclerView.Adapter<NewTourBarItemRecyclerViewAdapter.ViewHolder>
{
    private final RealmResults<BarItem> mValues;
    private final Context context;
    private Realm realm;
    public List<Integer> listOfBarItems = new ArrayList<>();

    // getting the ArrayList value
    public ArrayList getlistOfBarItems()
    {
        return (ArrayList) listOfBarItems;
    }

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
        final String price = Double.toString(mValues.get(position).price) + ",-";
        final String id = String.valueOf(mValues.get(position).id);

        holder.txtName.setText(name);
        holder.txtPrice.setText(price);
        holder.checkBox.setText(id);
        holder.checkBox.setTextSize(0);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    listOfBarItems.add(Integer.parseInt(id));
                }
                else{
                    int idToRemove = Integer.parseInt(id);
                    int pos = listOfBarItems.indexOf(idToRemove);
                    listOfBarItems.remove(pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView txtPrice;
        public CheckBox checkBox;

        public ViewHolder(View itemView)
        {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.newTour_tutorListView_BarItemName);
            txtPrice = (TextView) itemView.findViewById(R.id.newTour_tutorListView_BarItemPrice);
            checkBox = (CheckBox) itemView.findViewById(R.id.newTour_tutorListView_BarItemcheckBox);
        }
    }
}
