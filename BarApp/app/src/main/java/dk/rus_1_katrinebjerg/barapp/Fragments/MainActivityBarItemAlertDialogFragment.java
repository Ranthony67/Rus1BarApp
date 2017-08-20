package dk.rus_1_katrinebjerg.barapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import dk.rus_1_katrinebjerg.barapp.R;

public class MainActivityBarItemAlertDialogFragment extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // When ok is clicked
                        Toast.makeText(getContext(),"dialog says hi",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // When cancle is clicked
                        Toast.makeText(getContext(),"dialog says godbuy",Toast.LENGTH_SHORT).show();

                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
