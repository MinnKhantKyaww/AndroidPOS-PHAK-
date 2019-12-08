package com.team.androidpos.ui.sale;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.team.androidpos.BR;
import com.team.androidpos.R;
import com.team.androidpos.model.entity.Sale;
import com.team.androidpos.model.entity.SaleProduct;
import com.team.androidpos.model.vo.ProductAndCategoryVO;
import com.team.androidpos.ui.AdapterItemClickListener;

import java.util.Objects;

public class SaleProductAdapter extends ListAdapter<SaleProduct, SaleProductAdapter.SaleProductViewHolder> {

    private static final DiffUtil.ItemCallback<SaleProduct> DIFF_CALLBACK = new DiffUtil.ItemCallback<SaleProduct>() {

        @Override
        public boolean areItemsTheSame(@NonNull SaleProduct oldItem, @NonNull SaleProduct newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull SaleProduct oldItem, @NonNull SaleProduct newItem) {
            return oldItem.equals(newItem);
        }
    };

    private AdapterItemClickListener<SaleProduct> adapterItemClickListener;

    SaleProductAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public SaleProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_sale_product, parent, false);
        return new SaleProductViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleProductViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public SaleProduct getItemAt(int position) {
        return getItem(position);
    }

    class SaleProductViewHolder extends RecyclerView.ViewHolder {


        private ViewDataBinding binding;

        public SaleProductViewHolder(@NonNull ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.binding = viewDataBinding;
            itemView.setOnClickListener(v -> {
                if (adapterItemClickListener != null) {
                    adapterItemClickListener.onClick(getItem(getAdapterPosition()));
                }
            });
        }

        void bind(SaleProduct obj) {
            binding.setVariable(BR.obj, obj);
        }
    }

    public void setAdapterItemClickListener(AdapterItemClickListener<SaleProduct> adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }
}
