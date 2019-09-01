package com.example.andoridpos.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.andoridpos.R;
import com.example.andoridpos.databinding.ProductEditBinding;
import com.example.andoridpos.model.entity.Category;
import com.example.andoridpos.model.entity.Product;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class ProductEditFragment extends Fragment {

    static final String KEY_PRODUCT_ID = "product_id";

    private ProductsEditViewModel viewModel;
    private ProductEditBinding binding;

    private final ChipGroup.OnCheckedChangeListener chipCheckListener = (chipGroup, id) -> {
        if(getView() == null) return;
        Chip chip = getView().findViewById(id);

        Product product = viewModel.product.getValue();
        product.setCategoryId((Integer) chip.getTag());
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = ViewModelProviders.of(this).get(ProductsEditViewModel.class);

        viewModel.categories.observe(this, categories -> {
            View view = getView();

            if(view == null) {

            }

            ChipGroup categoryGroup = view.findViewById(R.id.chipGroupCategories);
            categoryGroup.removeAllViews();

            Product product = viewModel.product.getValue();

            for(Category c : categories) {
                Chip chip = new Chip(view.getContext());
                chip.setCheckable(true);
                chip.setText(c.getName());
                chip.setTag(c.getId());

                if (product.getCategoryId() == c.getId()) {
                    chip.setChecked(true);
                }

                categoryGroup.addView(chip);
            }

            categoryGroup.invalidate();
            categoryGroup.setOnCheckedChangeListener(chipCheckListener);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = ProductEditBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_save) {

            viewModel.save();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.operation.observe(this, op -> {
            if (op) requireActivity().onBackPressed();
        });

        int id = getArguments() != null ? getArguments().getInt(KEY_PRODUCT_ID) : 0;
        viewModel.init(id);
    }
}
