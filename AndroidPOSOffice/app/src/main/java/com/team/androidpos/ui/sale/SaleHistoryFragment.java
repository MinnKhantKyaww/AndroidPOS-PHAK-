package com.team.androidpos.ui.sale;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.androidpos.R;
import com.team.androidpos.ui.ListFragment;
import com.team.androidpos.ui.MainActivity;

public class SaleHistoryFragment extends ListFragment {

    private SaleAdapter saleAdapter;
    private SaleHistoryViewModel viewModel;

    @Override
    protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter() {
        return saleAdapter;
    }

    @Override
    protected boolean listenSwipeDelete() {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saleAdapter = new SaleAdapter();
        saleAdapter.setAdapterItemClickListener(v -> {
            Bundle args = new Bundle();
            args.putLong(SaleReceiptFragment.KEY_SALE_ID, v.getId());
            args.putInt(SaleReceiptFragment.KEY_NAV_BACk, SaleReceiptFragment.NAV_SALE_HISTORY);
            Navigation.findNavController(getView()).navigate(R.id.action_saleHistoryFragment_to_saleReceiptFragment, args);
        });

        viewModel = ViewModelProviders.of(this).get(SaleHistoryViewModel.class);
        viewModel.sales.observe(this, saleAdapter::submitList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.dateTimes.setValue(null);

        MainActivity activity = (MainActivity) requireActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.switchToggle(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.hide();
        TextView nodData = view.findViewById(R.id.tvNoData);
        viewModel.sales.observe(getViewLifecycleOwner(), list -> {
            if(list.isEmpty()) {
                nodData.setVisibility(View.VISIBLE);
            } else {
                nodData.setVisibility(View.GONE);
            }
        });
    }
}
