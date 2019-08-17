package com.example.andoridpos.ui.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andoridpos.BR;
import com.example.andoridpos.R;
import com.example.andoridpos.model.vo.CategoryAndProductCountVO;

public class CategoryAndProductAdapter extends ListAdapter<CategoryAndProductCountVO, CategoryAndProductAdapter.CategoryAndProductAdapterViewHolder> {

    private static final DiffUtil.ItemCallback<CategoryAndProductCountVO> DIFF_UTIL = new DiffUtil.ItemCallback<CategoryAndProductCountVO>() {
        @Override
        public boolean areItemsTheSame(@NonNull CategoryAndProductCountVO oldItem, @NonNull CategoryAndProductCountVO newItem) {
            return oldItem.getCategory().getId() == newItem.getCategory().getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CategoryAndProductCountVO oldItem, @NonNull CategoryAndProductCountVO newItem) {
            return oldItem.equals(newItem);
        }
    };

    CategoryAndProductAdapter() {
        super(DIFF_UTIL);
    }

    @NonNull
    @Override
    public CategoryAndProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding dataBindingUtil = DataBindingUtil.inflate(layoutInflater, R.layout.layout_category, parent, false);
        return new CategoryAndProductAdapterViewHolder(dataBindingUtil);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAndProductAdapterViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class CategoryAndProductAdapterViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding viewDataBinding;

         CategoryAndProductAdapterViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

         void bind(CategoryAndProductCountVO obj) {
            viewDataBinding.setVariable(BR.obj, obj);
            viewDataBinding.executePendingBindings();
        }

    }
}
