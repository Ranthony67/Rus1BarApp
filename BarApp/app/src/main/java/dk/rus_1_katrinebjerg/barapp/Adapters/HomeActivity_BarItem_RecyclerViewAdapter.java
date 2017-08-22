package dk.rus_1_katrinebjerg.barapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import dk.rus_1_katrinebjerg.barapp.Model.BarItem;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.RealmList;

public class HomeActivity_BarItem_RecyclerViewAdapter extends RecyclerView.Adapter<HomeActivity_BarItem_RecyclerViewAdapter.ViewHolder>
{
    private final RealmList<BarItem> mValues;
    private final Context context;
    private HashMap<Integer,Integer> barItemsBought = new HashMap<>();

    public HashMap<Integer, Integer> getBarItemsBought() {
        return barItemsBought;
    }

    public HomeActivity_BarItem_RecyclerViewAdapter(RealmList<BarItem> mValues, Context context)
    {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.buy_baritem_listview , parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = mValues.get(position).name;
        final double price = mValues.get(position).price;
        final int barItemId = mValues.get(position).id;
        final int[] count = {0};

        holder.Name.setText(name);
        holder.Price.setText(String.valueOf(price));
        holder.Edittxt.setText(String.valueOf(count[0]));

        holder.BtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View mView = (View) v.getParent();
                EditText mET = (EditText) mView.findViewById(R.id.buyBarItemEditTextItemCount);
                count[0] = Integer.parseInt(mET.getText().toString()) + 1;

                mET.setText(String.valueOf(count[0]));
                barItemsBought.put(barItemId,count[0]);
            }
        });

        holder.BtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View mView = (View) v.getParent();
                EditText mET = (EditText) mView.findViewById(R.id.buyBarItemEditTextItemCount);
                count[0] = Integer.parseInt(mET.getText().toString()) - 1;

                mET.setText(String.valueOf(count[0]));
                barItemsBought.put(barItemId,count[0]);
            }
        });
    }

    @Override
    public int getItemCount() { return mValues.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Name;
        public TextView Price;
        public Button BtnPlus;
        public Button BtnMinus;
        public EditText Edittxt;

        public ViewHolder(View itemView)
        {
            super(itemView);

            Name = (TextView) itemView.findViewById(R.id.buyBarItemName);
            Price = (TextView) itemView.findViewById(R.id.buyBarItemPrice);
            BtnPlus = (Button) itemView.findViewById(R.id.buyBarItemBtnPlus);
            BtnMinus = (Button) itemView.findViewById(R.id.buyBarItemBtnMinus);
            Edittxt = (EditText) itemView.findViewById(R.id.buyBarItemEditTextItemCount);
        }
    }





}
