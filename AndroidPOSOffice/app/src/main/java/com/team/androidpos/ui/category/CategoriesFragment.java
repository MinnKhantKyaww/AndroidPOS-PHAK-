package com.team.androidpos.ui.category;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.team.androidpos.R;
import com.team.androidpos.model.vo.CategoryAndProductCountVO;
import com.team.androidpos.ui.ListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView nodData = view.findViewById(R.id.tvNoData);
        viewModel.getCategories().observe(getViewLifecycleOwner(), list -> {
            if(list.isEmpty()) {
                nodData.setVisibility(View.VISIBLE);
            } else {
                nodData.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onFabClick(View v) {
        navigateEdit(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void deleteItemAt(int position) {
       View listCategoryView = getView().findViewById(R.id.frag_list_item);
        if(adapter.getItemAt(position).getProductCount() > 0) {
            adapter.notifyItemChanged(position);
            Snackbar snackbar = Snackbar.make(listCategoryView, "Can't delete category.", Snackbar.LENGTH_LONG);
            ViewGroup.LayoutParams params = snackbar.getView().getLayoutParams();
            snackbar.show();
        } else {
            viewModel.delete(adapter.getItemAt(position).getCategory().getId());
        }
    }

  @Override
   protected void restoreItemAt() {
//        //    viewModel.getCategories().getValue().get(categoryPosition).getCategory().setId(oldId);
//        viewModel.getCategories().getValue().add(categoryPosition, category);
//        //viewModel.getCategories().observe();
//        // viewModel.getCategories().getValue().add(categoryPosition, category);
//
//        viewModel.getCategories().getValue().add(categoryPosition, category);
//         viewModel.getCategories().getValue().add(categoryPosition, adapter.getItemAt(oldData));
  }



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
