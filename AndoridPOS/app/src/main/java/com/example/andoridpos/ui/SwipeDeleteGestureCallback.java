package com.example.andoridpos.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andoridpos.R;

public class SwipeDeleteGestureCallback extends ItemTouchHelper.SimpleCallback {

    public interface OnSwipeDeleteListener {
        void onSwiped(int position);
    }

    private final Paint paint = new Paint();

    private Bitmap icon;

    private Context context;

    private OnSwipeDeleteListener onSwipeDeleteListener;

    public void setOnSwipeDeleteListener(OnSwipeDeleteListener onSwipeDeleteListener) {
        this.onSwipeDeleteListener = onSwipeDeleteListener;
    }

    public SwipeDeleteGestureCallback(Context context) {
        super(0, ItemTouchHelper.LEFT);
        this.context = context;
        paint.setColor(Color.parseColor("#f13a37"));
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d("TAG", "Swipe at:" + viewHolder.getAdapterPosition());
        if (onSwipeDeleteListener != null) {
            onSwipeDeleteListener.onSwiped(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View view = viewHolder.itemView;


        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float width = view.getHeight() / 3;
            //float tX = (dX * view.getHeight() / view.getWidth());
            float tX=dX;
            Log.d("TX", "TX at:" + tX);

            //background_rectangle
            RectF background = new RectF(view.getRight() + tX, view.getTop(), view.getRight(), view.getBottom());
            c.drawRect(background, paint);

            Log.d("background", "background at:" + background);

            //icon_rectangle
            RectF iconDest = new RectF(view.getRight() - 2 * width, view.getTop() + width,
                    view.getRight() - width, view.getBottom() - width);
            c.drawBitmap(getIcon(), null, iconDest, paint);

            Log.d("iconDest", "iconDest at:" + iconDest);

            super.onChildDraw(c, recyclerView, viewHolder, tX, dY, actionState, isCurrentlyActive);
        }

    }

    // Lazy init
    public Bitmap getIcon() {
        if(icon == null) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.ic_delete, null);
            icon = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(icon);
            drawable.setBounds(0,0,canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }

        return icon;
    }

}
