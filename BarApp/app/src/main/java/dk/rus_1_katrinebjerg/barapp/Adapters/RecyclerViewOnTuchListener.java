package dk.rus_1_katrinebjerg.barapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import dk.rus_1_katrinebjerg.barapp.R;

public class RecyclerViewOnTuchListener implements RecyclerView.OnItemTouchListener{
    private GestureDetector gestureDetector;
    private ClickListener clickListener;
    private Context mContext;

    public RecyclerViewOnTuchListener(final Context context, final RecyclerView recyclerView, final ClickListener onClickListener){
        this.clickListener = onClickListener;
        mContext = context;

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                //super.onLongPress(e);
                Toast.makeText(context, "Long Click is not suported", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());

        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {

            clickListener.onClick(child, rv.indexOfChild(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface ClickListener{
        void onClick(View view, int pos);
        void onLongClick(View view, int pos);
    }
}
