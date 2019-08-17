package com.example.andoridpos.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andoridpos.R;
import com.example.andoridpos.ui.ListFragment;

public class CategoriesFragment extends ListFragment {

    private CategoriesViewMode viewModel;
    private CategoryAndProductAdapter adapter;

    @Override
    protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter() {
        if(adapter == null) {
            adapter = new CategoryAndProductAdapter();
        }
        return adapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CategoriesViewMode.class);
        viewModel.getCategories().observe(this, list -> {
            adapter.submitList(list);
        });
    }

    @Override
    protected void onFabClick(View view) {
        FragmentTransaction ft = requireFragmentManager().beginTransaction();
        Fragment pref = requireFragmentManager().findFragmentByTag("dialog");
        if(pref != null) {
            ft.remove(pref);
        }

        DialogFragment dialogFragment = new CategorieEditFragment();
        dialogFragment.show(ft, "dialog");
    }
}
