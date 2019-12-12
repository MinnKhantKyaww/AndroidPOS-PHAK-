package com.team.androidpos.ui.product;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.team.androidpos.BR;
import com.team.androidpos.R;
import com.team.androidpos.model.vo.ProductAndCategoryVO;
import com.team.androidpos.ui.AdapterItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ProductAndCategoryAdapter extends PagedListAdapter<ProductAndCategoryVO, ProductAndCategoryAdapter.ProductAndCategoryViewHolder> {

    private static final DiffUtil.ItemCallback<ProductAndCategoryVO> DIFF_CALLBACK = new DiffUtil.ItemCallback<ProductAndCategoryVO>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProductAndCategoryVO oldItem, @NonNull ProductAndCategoryVO newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductAndCategoryVO oldItem, @NonNull ProductAndCategoryVO newItem) {
            return oldItem.equals(newItem);
        }
    };

    private AdapterItemClickListener<ProductAndCategoryVO> adapterItemClickListener;

    public ProductAndCategoryAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ProductAndCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_product, parent, false);
        return new ProductAndCategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAndCategoryViewHolder holder, int position) {
        holder.bind(getItem(position));

        //fade_transition
        ImageView imageView = holder.itemView.findViewById(R.id.imageView);

        imageView.setAnimation(AnimationUtils.loadAnimation(
                holder.itemView.getContext(), R.anim.item_anim_fade_transition));

        //fade_scale
        TextView textCategory = holder.itemView.findViewById(R.id.categoryName);
        TextView textItemName = holder.itemView.findViewById(R.id.itemName);
        TextView textPrice = holder.itemView.findViewById(R.id.price);

        textCategory.setAnimation(AnimationUtils.loadAnimation(
                holder.itemView.getContext(), R.anim.item_anim_fade_scale));
        textItemName.setAnimation(AnimationUtils.loadAnimation(
                holder.itemView.getContext(), R.anim.item_anim_fade_scale));
        textPrice.setAnimation(AnimationUtils.loadAnimation(
                holder.itemView.getContext(), R.anim.item_anim_fade_scale));
    }

    public void setAdapterItemClickListener(AdapterItemClickListener<ProductAndCategoryVO> adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    public ProductAndCategoryVO getItemAt(int position) {
        return getItem(position);
    }

    class ProductAndCategoryViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        ProductAndCategoryViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                if (adapterItemClickListener != null) {
                    adapterItemClickListener.onClick(getItem(getAdapterPosition()));
                }
            });
        }

        void bind(ProductAndCategoryVO obj) {
            binding.setVariable(BR.obj, obj);
            binding.executePendingBindings();
        }
    }

}
