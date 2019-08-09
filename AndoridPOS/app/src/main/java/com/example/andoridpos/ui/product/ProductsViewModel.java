package com.example.andoridpos.ui.product;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.andoridpos.ServiceLocator;
import com.example.andoridpos.model.entity.Product;
import com.example.andoridpos.model.repo.ProductRepo;

public class ProductsViewModel extends AndroidViewModel {

    private ProductRepo repo;

    private LiveData<PagedList<Product>> products;

    public ProductsViewModel(@NonNull Application application) {
        super(application);
        this.repo = ServiceLocator.getInstance(application).productRepo();
    }

    public LiveData<PagedList<Product>> getProducts() {

        if(products == null) {
            products = repo.getAll();
        }

        return products;
    }
}
