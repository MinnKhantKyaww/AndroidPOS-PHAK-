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
    public final MutableLiveData<Category> category = new MutableLiveData<>();

    private LiveData<List<Category>> categories;

    public ProductsEditViewModel(@NonNull Application application) {
        super(application);
        this.repo = ServiceLocator.getInstance(application).productRepo();
        this.categoryRepo = ServiceLocator.getInstance(application).categoryRepo();
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
    public LiveData<List<Category>> getCategories() {
        if (categories == null) {
            categories = categoryRepo.getAll();
        }

        return categories;
    }

    public void save() {
        //TODO
    }

    public boolean isValid() {
        //TODO
        return false;
    }
}
