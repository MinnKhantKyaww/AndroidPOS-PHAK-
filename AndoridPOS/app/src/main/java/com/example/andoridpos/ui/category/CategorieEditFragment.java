package com.example.andoridpos.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.andoridpos.R;
import com.example.andoridpos.databinding.CategoryEditBinding;

public class CategorieEditFragment extends DialogFragment {

    public static final String KEY_CATEGORY_ID = "category_id";

    private CategoriesEditViewModel viewModel;
    private CategoryEditBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         binding = CategoryEditBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.btnCancel.setOnClickListener(v -> dismiss());
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CategoriesEditViewModel.class);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


            viewModel.nameError.observe(this, error -> {
                binding.textInputLayoutCategoryName.setError(error);
            });

            viewModel.error.observe(this, error -> {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            });

            viewModel.operation.observe(this, op -> {
                if(op) dismiss();
            });

            int categoryId = getArguments() != null ? getArguments().getInt(KEY_CATEGORY_ID, 0) : 0;
            viewModel.init(categoryId);
    }
}
