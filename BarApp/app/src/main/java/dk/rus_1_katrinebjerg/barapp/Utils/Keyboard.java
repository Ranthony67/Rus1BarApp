package dk.rus_1_katrinebjerg.barapp.Utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

// Class used to enable and disable keyboard behavior
public class Keyboard {
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.findViewById(android.R.id.content);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static View.OnFocusChangeListener defaultFocusListener(final Activity activity) {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Keyboard.hideKeyboard(activity);
                }
            }
        };
    }
}
