package dk.rus_1_katrinebjerg.barapp.Fragments.Tutor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import dk.rus_1_katrinebjerg.barapp.Model.Tutor;
import dk.rus_1_katrinebjerg.barapp.R;
import dk.rus_1_katrinebjerg.barapp.Utils.Keyboard;
import io.realm.Realm;

public class TutorInputFragment extends Fragment {

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


    public TutorInputFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tutor_input, container, false);

        ButterKnife.bind(this, root);
        Realm.init(getContext());
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        realm = Realm.getDefaultInstance();
        editTxtName.setOnFocusChangeListener(Keyboard.defaultFocusListener(getActivity()));
        editTxtStreetName.setOnFocusChangeListener(Keyboard.defaultFocusListener(getActivity()));

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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_PICKER_SELECT && resultCode == Activity.RESULT_OK){
            tutorImagePath = getPathFromCameraData(data, getContext());

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
        if (realm.where(Tutor.class).max("id") != null) {
            primaryKeyValue = realm.where(Tutor.class).max("id").intValue() + 1;
        } else{
            primaryKeyValue = 0;
        }

        tutor.id = primaryKeyValue;
        tutor.name = editTxtName.getText().toString();
        tutor.streetName = editTxtStreetName.getText().toString();
        tutor.imagePath = tutorImagePath != null ? tutorImagePath : "";

        realm.beginTransaction();
        realm.copyToRealm(tutor);
        realm.commitTransaction();

        realm.close();
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
}
