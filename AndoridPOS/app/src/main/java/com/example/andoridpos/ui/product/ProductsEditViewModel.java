package com.example.andoridpos.ui.product;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.andoridpos.ServiceLocator;
import com.example.andoridpos.model.entity.Category;
import com.example.andoridpos.model.entity.Product;
import com.example.andoridpos.model.repo.CategoryRepo;
import com.example.andoridpos.model.repo.ProductRepo;
import com.example.andoridpos.util.AppExecutors;

import java.util.List;

public class ProductsEditViewModel extends AndroidViewModel {

    private ProductRepo repo;
    private CategoryRepo categoryRepo;
    public MutableLiveData<Product> product = new MutableLiveData<>();

    final MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    final MutableLiveData<Boolean> operation = new MutableLiveData<>();

    public ProductsEditViewModel(@NonNull Application application) {
        super(application);
        this.repo = ServiceLocator.getInstance(application).productRepo();
        this.categoryRepo = ServiceLocator.getInstance(application).categoryRepo();
    }

    void init(int id) {
        AppExecutors.io().execute(() -> {
            Product p = repo.getProductSync(id);
            if(p != null) {
                product.postValue(p);
            } else {
                product.postValue(new Product());
            }

            categories.postValue(categoryRepo.getAllSync());
        });
    }

    public void save() {
        if (isValid()) {
            AppExecutors.io().execute(() -> {
                try {
                    repo.save(product.getValue());
                    operation.postValue(true);
                } catch (Exception e) {
                    operation.postValue(false);
                }
            });
        }
    }

    public boolean isValid() {
        //TODO
        return true;
    }
}
