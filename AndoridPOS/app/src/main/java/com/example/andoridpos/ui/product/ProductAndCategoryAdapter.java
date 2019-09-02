package com.example.andoridpos.ui.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andoridpos.BR;
import com.example.andoridpos.R;
import com.example.andoridpos.model.vo.ProductAndCategoryVO;
import com.example.andoridpos.ui.AdapterItemClickListener;

public class ProductAndCategoryAdapter extends PagedListAdapter<ProductAndCategoryVO, ProductAndCategoryAdapter.ProductAndCategoryViewHolder> {

    private static final DiffUtil.ItemCallback<ProductAndCategoryVO> DIFF_UTIL = new DiffUtil.ItemCallback<ProductAndCategoryVO>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProductAndCategoryVO oldItem, @NonNull ProductAndCategoryVO newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductAndCategoryVO oldItem, @NonNull ProductAndCategoryVO newItem) {
            return oldItem.equals(newItem);
        }
    };

    ProductAndCategoryAdapter() {
        super(DIFF_UTIL);
    }

    private AdapterItemClickListener<ProductAndCategoryVO> adapterItemClickListener;

    @NonNull
    @Override
    public ProductAndCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_product, parent, false);
        return new ProductAndCategoryViewHolder(viewDataBinding);
    }

    public ProductAndCategoryVO getItemAt(int position) {
        return getItem(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAndCategoryViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public AdapterItemClickListener<ProductAndCategoryVO> getAdapterItemClickListener() {
        return adapterItemClickListener;
    }

    public void setAdapterItemClickListener(AdapterItemClickListener<ProductAndCategoryVO> adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    class ProductAndCategoryViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding viewDataBinding;

        ProductAndCategoryViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
            viewDataBinding.getRoot().setOnClickListener(v -> {
                if(adapterItemClickListener != null) {
                    adapterItemClickListener.onClick(getItem(getAdapterPosition()));
                }
            });
        }

        void bind(ProductAndCategoryVO obj) {
            viewDataBinding.setVariable(BR.obj, obj);
            viewDataBinding.executePendingBindings();
        }
    }
}
