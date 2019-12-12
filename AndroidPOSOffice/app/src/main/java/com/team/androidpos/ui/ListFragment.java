package com.team.androidpos.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.team.androidpos.R;

public abstract class ListFragment extends Fragment {

    protected abstract RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter();
    protected abstract boolean listenSwipeDelete();

    private ConstraintLayout constraintLayout;

    private int positionID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_list_item, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        int resId = R.anim.layout_anim_slide_bottom;
        /*LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), resId);
        recyclerView.setLayoutAnimation(animationController);
        recyclerView.scheduleLayoutAnimation();*/
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        constraintLayout = view.findViewById(R.id.frag_list_item);

        /*int resId = R.anim.layout_anim_slide_right;
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), resId);*/

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        //recyclerView.setLayoutAnimation(animationController);
        //recyclerView.scheduleLayoutAnimation();
        recyclerView.setAdapter(adapter());

        if (listenSwipeDelete()) {
            SwipeDeleteGestureCallback callback = new SwipeDeleteGestureCallback(requireContext());
            callback.setOnSwipeDeleteListener(position -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure to delete?")
                        .setCancelable(false)
                        .setNegativeButton(android.R.string.cancel, (di, i) -> {
                            adapter().notifyItemChanged(position);
                            di.dismiss();
                        })
                        .setPositiveButton(R.string.delete, (di, i) -> {
                            //positionID = position;
                            deleteItemAt(position);
                            di.dismiss();
                            //adapter().notifyItemRemoved(position);
                           // adapter().notifyItemChanged(position);
                           // undoDelete();
                        })
                        .show();
            });

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }

        FloatingActionButton fab = view.findViewById(R.id.fab);

        if(fab != null) {
            fab.setOnClickListener(this::onFabClick);
        }
        // TODO hide/show on recyclerView scroll

    }

    private boolean undoDelete() {
            Snackbar snackbar = Snackbar.make(constraintLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    restoreItemAt();
                    //adapter().notifyItemRemoved(positionID);
                    //adapter().notifyItemInserted(positionID);
                }
            });
            snackbar.show();
            return true;
    }

    protected void onFabClick(View v) {}

    protected void deleteItemAt(int position) {}

    protected void restoreItemAt(){}
}
