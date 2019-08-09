package com.example.andoridpos.ui.product;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.andoridpos.ServiceLocator;
import com.example.andoridpos.model.entity.Product;
import com.example.andoridpos.model.repo.ProductRepo;
import com.example.andoridpos.util.AppExecutors;

public class ProductsEditViewModel extends AndroidViewModel {

    private ProductRepo repo;

    private MutableLiveData<Product> product = new MutableLiveData<>();

    public ProductsEditViewModel(@NonNull Application application) {
        super(application);
        this.repo = ServiceLocator.getInstance(application).productRepo();
    }

    public void init(int id) {
        AppExecutors.io().execute(() -> {
            Product p = repo.getProductSync(id);
            if(p == null) {
                product.postValue(p);
            } else {
                product.postValue(new Product());
            }
        });
    }

    public void save() {
        //TODO
    }

    public boolean isValid() {
        //TODO
        return false;
    }
}
