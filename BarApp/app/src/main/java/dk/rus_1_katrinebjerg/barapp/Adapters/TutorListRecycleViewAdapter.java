package dk.rus_1_katrinebjerg.barapp.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import dk.rus_1_katrinebjerg.barapp.Model.Trip;
import dk.rus_1_katrinebjerg.barapp.Model.Tutor;
import dk.rus_1_katrinebjerg.barapp.R;
import io.realm.Realm;
import io.realm.RealmResults;

public class TutorListRecycleViewAdapter extends RecyclerView.Adapter<TutorListRecycleViewAdapter.ViewHolder>
{
    private final RealmResults<Tutor> mValues;
    private final Context context;
    private Realm realm;

    public TutorListRecycleViewAdapter(RealmResults<Tutor> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tutor_list_cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final int id = mValues.get(position).id;
        final String name = mValues.get(position).name;
        final String streetName = mValues.get(position).streetName;

        holder.txtName.setText(name);
        holder.txtStreetName.setText(streetName);


        if(mValues.get(position).imagePath.equals("")){
            BitmapDrawable tutorImageBitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_person);
            holder.imgTutor.setImageBitmap(tutorImageBitmapDrawable.getBitmap());
        }
        else {
            Drawable tutorImageDrawable = Drawable.createFromPath(mValues.get(position).imagePath);
            BitmapDrawable tutorImageBitmapDrawable = (BitmapDrawable) tutorImageDrawable;
            holder.imgTutor.setImageBitmap(tutorImageBitmapDrawable.getBitmap());
        }

        holder.btnDeleteTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                RealmResults<Tutor> tutor = realm.where(Tutor.class).equalTo("id", id).findAll();
                tutor.deleteAllFromRealm();
                realm.commitTransaction();
            }
        });

        holder.btnEditTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgTutor;
        public TextView txtName;
        public TextView txtStreetName;
        public ImageButton btnEditTutor;
        public ImageButton btnDeleteTutor;

        public ViewHolder(View itemView)
        {
            super(itemView);

            imgTutor = (ImageView) itemView.findViewById(R.id.tutorImageView);
            txtName = (TextView) itemView.findViewById(R.id.txtTutorName);
            txtStreetName = (TextView) itemView.findViewById(R.id.txtStreetname);
            btnEditTutor = (ImageButton) itemView.findViewById(R.id.btnEditTutor);
            btnDeleteTutor = (ImageButton) itemView.findViewById(R.id.btnDeleteTutor);
        }
    }
}
