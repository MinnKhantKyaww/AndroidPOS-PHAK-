package com.team.androidpos.ui.category;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.team.androidpos.R;
import com.team.androidpos.model.entity.Category;
import com.team.androidpos.model.vo.CategoryAndProductCountVO;
import com.team.androidpos.ui.ListFragment;
import com.team.androidpos.ui.SwipeDeleteGestureCallback;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends ListFragment {

    private CategoriesViewModel viewModel;
    private CategoryAndProductCountAdapter adapter;
    private CategoryAndProductCountVO category;
    private int categoryPosition;
    private int oldId;
    private String oldName;

    @Override
    protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter() {
        if (adapter == null) {
            adapter = new CategoryAndProductCountAdapter();
            adapter.setAdapterItemClickListener(vo -> {
                Bundle args = new Bundle();
                args.putInt(CategoryEditFragment.KEY_CATEGORY_ID, vo.getCategory().getId());
                navigateEdit(args);
            });
        }
        return adapter;
    }

    @Override
    protected boolean listenSwipeDelete() {
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        viewModel.getCategories().observe(this, list -> {
            adapter.submitList(list);
        });
    }

    @Override
    protected void onFabClick(View v) {
        navigateEdit(null);
    }

    @Override
    protected void deleteItemAt(int position) {
//        category = adapter.getItemAt(position);
//        categoryPosition = position;
//        oldId = adapter.getItemAt(position).getCategory().getId();
//        oldName = adapter.getItemAt(position).getCategory().getName();
        viewModel.delete(adapter.getItemAt(position).getCategory().getId());
       // viewModel.getCategories().getValue().get(position).getCategory().setId(oldId);
        //adapter.notifyItemRemoved(position);
    }

//    @Override
//    protected void restoreItemAt() {
//        //    viewModel.getCategories().getValue().get(categoryPosition).getCategory().setId(oldId);
//        viewModel.getCategories().getValue().add(categoryPosition, category);
//        //viewModel.getCategories().observe();
//        // viewModel.getCategories().getValue().add(categoryPosition, category);
//
//        //viewModel.getCategories().getValue().add(categoryPosition, category);
//        // viewModel.getCategories().getValue().add(categoryPosition, adapter.getItemAt(oldData));
//    }



    private void navigateEdit(Bundle args) {
        FragmentTransaction ft = requireFragmentManager().beginTransaction();
        Fragment prev = requireFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }

        DialogFragment dialogFragment = new CategoryEditFragment();

        if (args != null) {
            dialogFragment.setArguments(args);
        }

        dialogFragment.show(ft, "dialog");
    }
}
