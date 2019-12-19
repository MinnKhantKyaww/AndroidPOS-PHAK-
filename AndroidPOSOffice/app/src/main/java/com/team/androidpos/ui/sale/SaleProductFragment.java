package com.team.androidpos.ui.sale;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.androidpos.R;
import com.team.androidpos.ui.ListFragment;
import com.team.androidpos.ui.MainActivity;
import com.team.androidpos.ui.SharePref;
import com.team.androidpos.ui.product.ProductAndCategoryAdapter;
import com.team.androidpos.util.PermissionUtil;

public class SaleProductFragment extends ListFragment {

    private ProductAndCategoryAdapter productAndCategoryAdapter;
    private SaleProductViewModel viewModel;

    private SaleActionViewModel saleActionViewModel;

    private View notiView;

    public SharePref sharePref;

    private TextView tvCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        sharePref = new SharePref(this);

        productAndCategoryAdapter = new ProductAndCategoryAdapter();
        productAndCategoryAdapter.setAdapterItemClickListener(vo -> {
            saleActionViewModel.addProduct(vo);

        });

        saleActionViewModel = ViewModelProviders.of(requireActivity()).get(SaleActionViewModel.class);

        viewModel = ViewModelProviders.of(this).get(SaleProductViewModel.class);
        viewModel.products.observe(this, productAndCategoryAdapter::submitList);


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sale, menu);

        notiView = menu.findItem(R.id.action_cart).getActionView();


        notiView.setOnClickListener(v -> {

            Navigation.findNavController(getView()).navigate(R.id.action_saleProductFragment_to_saleDetailFragment);
        });

        saleActionViewModel.sale.observe(this, sale -> {
            SwitchCompat switchCompat = getView().findViewById(R.id.drawer_switch);
            if(notiView == null) {
                return;
            }

            tvCount = notiView.findViewById(R.id.tvSaleProductCount);
            if(sale.getTotalProduct() > 0) {
                tvCount.setText(String.valueOf(sale.getTotalProduct()));
                sharePref.setSaleNoti(sale.getTotalProduct());
                tvCount.setVisibility(View.VISIBLE);
            } else {
                tvCount.setVisibility(View.GONE);
            }


        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void restartApp() {
        Intent intent = new Intent(getContext().getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_cart:
                Navigation.findNavController(getView()).navigate(R.id.action_saleProductFragment_to_saleDetailFragment);
                return true;
            case  R.id.action_filter:
                Navigation.findNavController(getView()).navigate(R.id.action_saleProductFragment_to_saleSearchFilterFragment);
                return true;

            case R.id.action_scan:
                if (!PermissionUtil.hasCameraPermission(this)) {

                    return true;
                }
                Navigation.findNavController(getView()).navigate(R.id.action_saleProductFragment_to_barcodeScanFragment);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.hide();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.categoryId.setValue(null);
        saleActionViewModel.init();

        MainActivity activity = (MainActivity) requireActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.switchToggle(true);

        sharePref = new SharePref(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.PERMISSION_CAMERA) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Navigation.findNavController(getView()).navigate(R.id.action_saleProductFragment_to_barcodeScanFragment);
            }
        }
    }

    @Override
    protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter() {
/*

        if(productAndCategoryAdapter == null) {
            productAndCategoryAdapter = new ProductAndCategoryAdapter();
            productAndCategoryAdapter.setAdapterItemClickListener(vo -> {
                // TODO
            });
        }
*/

        return productAndCategoryAdapter;
    }

    @Override
    protected boolean listenSwipeDelete() {
        return false;
    }
}
