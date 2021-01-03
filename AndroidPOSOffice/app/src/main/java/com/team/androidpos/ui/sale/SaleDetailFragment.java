package com.team.androidpos.ui.sale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.team.androidpos.R;
import com.team.androidpos.model.entity.SaleProduct;
import com.team.androidpos.ui.ListFragment;
import com.team.androidpos.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SaleDetailFragment extends ListFragment {

    private SaleProductAdapter adapter;
    private SaleActionViewModel viewModel;

    @Override
    protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter() {
        if(adapter == null) {
            adapter = new SaleProductAdapter();
            adapter.setAdapterItemClickListener(saleProduct -> {
                viewModel.editSaleProduct.setValue(saleProduct.copy());
                Navigation.findNavController(getView()).navigate(R.id.action_saleDetailFragment_to_saleProductEditFragment);
            });
        }
        return adapter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.saleProducts.observe(getViewLifecycleOwner(), map -> {
            List<SaleProduct> list = new ArrayList<>(map.values());
            adapter.submitList(list);

        });

        viewModel.sale.observe(getViewLifecycleOwner(), sale -> {
            View view = getView();
            if(view == null) {
                return;
            }

            TextView tvTotalprice = view.findViewById(R.id.tvTotalPrice);
            tvTotalprice.setText(String.valueOf(sale.getTotalPrice()));

            Button btnCheckOut = view.findViewById(R.id.btnCheckout);
            btnCheckOut.setEnabled(sale.getTotalPrice() > 0);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnCheckout = view.findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_saleDetailFragment_to_completeSaleFragment);
        });
    }

    @Override
    protected boolean listenSwipeDelete() {
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MainActivity activity = (MainActivity) requireActivity();
//        activity.switchToggle(false);
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewModel = ViewModelProviders.of(requireActivity()).get(SaleActionViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sale_detail, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) requireActivity();
        activity.switchToggle(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void deleteItemAt(int position) {
        super.deleteItemAt(position);
        viewModel.removeProduct(adapter.getItemAt(position));
    }
}
