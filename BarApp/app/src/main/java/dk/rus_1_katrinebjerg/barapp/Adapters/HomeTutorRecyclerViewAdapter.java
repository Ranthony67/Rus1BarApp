package dk.rus_1_katrinebjerg.barapp.Adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import dk.rus_1_katrinebjerg.barapp.Model.Tutor;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.RealmList;

public class HomeTutorRecyclerViewAdapter extends RecyclerView.Adapter<HomeTutorRecyclerViewAdapter.ViewHolder>
{
    public final RealmList<Tutor> mValues;
    private final Context context;

    public HomeTutorRecyclerViewAdapter(RealmList<Tutor> mValues, Context context)
    {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutor_list_listview, parent, false);
        final HomeTutorRecyclerViewAdapter.ViewHolder viewHolder = new HomeTutorRecyclerViewAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = mValues.get(position).name;
        final String streetName = mValues.get(position).streetName;

        Drawable NewTourTutorImageDrawable = Drawable.createFromPath(mValues.get(position).imagePath);
        BitmapDrawable NewTourTutorImageBitmapDrawable = (BitmapDrawable) NewTourTutorImageDrawable;

        if(NewTourTutorImageBitmapDrawable != null){
            holder.imgTutor.setImageBitmap(NewTourTutorImageBitmapDrawable.getBitmap());
        }else{
            BitmapDrawable tutorImageBitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_person);
            holder.imgTutor.setImageBitmap(tutorImageBitmapDrawable.getBitmap());
        }

        holder.txtName.setText(name);
        holder.txtStreetName.setText(streetName);
        holder.checkBox.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView txtStreetName;
        public ImageView imgTutor;
        public CheckBox checkBox;

        public ViewHolder(final View itemView)
        {
            super(itemView);

            imgTutor = (ImageView) itemView.findViewById(R.id.newTour_tutorListView_imageView);
            txtName = (TextView) itemView.findViewById(R.id.newTour_tutorListView_name);
            txtStreetName = (TextView) itemView.findViewById(R.id.newTour_tutorListView_Streetname);
            checkBox = (CheckBox) itemView.findViewById(R.id.newTour_tutorListView_checkBox);
        }
    }
}
















