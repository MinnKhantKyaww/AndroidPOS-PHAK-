package com.example.andoridpos.ui.product;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.andoridpos.ServiceLocator;
import com.example.andoridpos.model.entity.Product;
import com.example.andoridpos.model.repo.ProductRepo;
import com.example.andoridpos.model.vo.ProductAndCategoryVO;
import com.example.andoridpos.util.AppExecutors;

public class ProductsViewModel extends AndroidViewModel {

    private ProductRepo repo;

    private LiveData<PagedList<ProductAndCategoryVO>> products;

    public ProductsViewModel(@NonNull Application application) {
        super(application);
        this.repo = ServiceLocator.getInstance(application).productRepo();
    }

    LiveData<PagedList<ProductAndCategoryVO>> getProducts() {

        if(products == null) {
            products = repo.getProductAndCategory();
        }

        return products;
    }

    void delete(int id) {
        try {
            AppExecutors.io().execute(() -> {
                repo.deleteById(id);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
