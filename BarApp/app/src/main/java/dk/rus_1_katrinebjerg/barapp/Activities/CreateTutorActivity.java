package dk.rus_1_katrinebjerg.barapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import dk.rus_1_katrinebjerg.barapp.Activities.Base.BaseWithDrawer;
import dk.rus_1_katrinebjerg.barapp.Adapters.TutorListRecycleViewAdapter;
import dk.rus_1_katrinebjerg.barapp.Model.Tutor;
import dk.rus_1_katrinebjerg.barapp.R;
import dk.rus_1_katrinebjerg.barapp.Utils.Keyboard;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class CreateTutorActivity extends BaseWithDrawer {

    private static final int IMAGE_PICKER_SELECT = 1;

    @BindView(R.id.editTxtName)
    EditText editTxtName;

    @BindView(R.id.editTxtStreetname)
    EditText editTxtStreetName;

    @BindView(R.id.btnAddTutor)
    Button btnAddTutor;

    @BindView(R.id.btnCancelTutor)
    Button btnCancelTutor;

    @BindView(R.id.tutorinput_tutorImage)
    ImageButton imgBtnChooseTutorImage;

    Realm realm;
    String tutorImagePath;
    RecyclerView mRecyclerView;
    TutorListRecycleViewAdapter tutorListRecycleViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_tutor);
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        editTxtName.setOnFocusChangeListener(Keyboard.defaultFocusListener(this));
        editTxtStreetName.setOnFocusChangeListener(Keyboard.defaultFocusListener(this));

        btnAddTutor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addTutor();
            }
        });
        btnCancelTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
        imgBtnChooseTutorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_PICKER_SELECT);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.tutor_list_recycler_view);
        mRecyclerView.hasFixedSize();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        final RealmResults<Tutor> tutors = realm.where(Tutor.class).findAll();

        tutorListRecycleViewAdapter = new TutorListRecycleViewAdapter(tutors, getApplicationContext());

        tutors.addChangeListener(new RealmChangeListener<RealmResults<Tutor>>() {
            @Override
            public void onChange(RealmResults<Tutor> element) {
                tutorListRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView.setAdapter(tutorListRecycleViewAdapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_PICKER_SELECT && resultCode == Activity.RESULT_OK){
            tutorImagePath = getPathFromCameraData(data, getApplicationContext());

            if(tutorImagePath != null){
                Drawable tutorImageDrawable = Drawable.createFromPath(tutorImagePath);
                BitmapDrawable tutorImageBitmapDrawable = (BitmapDrawable) tutorImageDrawable;
                imgBtnChooseTutorImage.setImageBitmap(tutorImageBitmapDrawable.getBitmap());
            }
        }
    }

    public void addTutor() {
        Tutor tutor = new Tutor();
        int primaryKeyValue;
        if (realm.where(Tutor.class).max("id") != null)
        {
            primaryKeyValue = realm.where(Tutor.class).max("id").intValue() + 1;
        }
        else
        {
            primaryKeyValue = 0;
        }

        tutor.id = primaryKeyValue;
        tutor.name = editTxtName.getText().toString();
        tutor.streetName = editTxtStreetName.getText().toString();
        tutor.imagePath = tutorImagePath != null ? tutorImagePath : "";

        realm.beginTransaction();
        realm.copyToRealm(tutor);
        realm.commitTransaction();

        clearFields();
    }

    public void clearFields() {
        BitmapDrawable tutorImageBitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_person);
        imgBtnChooseTutorImage.setImageBitmap(tutorImageBitmapDrawable.getBitmap());
        editTxtName.setText("");
        editTxtStreetName.setText("");
    }

    public static String getPathFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
