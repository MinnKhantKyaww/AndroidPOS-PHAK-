package com.example.andoridpos.ui.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.andoridpos.ServiceLocator;
import com.example.andoridpos.model.entity.Category;
import com.example.andoridpos.model.repo.CategoryRepo;
import com.example.andoridpos.util.AppExecutors;

public class CategoriesEditViewModel extends AndroidViewModel {

    private CategoryRepo repo;
    private MutableLiveData<Category> category = new MutableLiveData<>();

    public CategoriesEditViewModel(@NonNull Application application) {
        super(application);
        this.repo = ServiceLocator.getInstance(application).categoryRepo();
    }

    public void init(int id) {

        AppExecutors.io().execute(() -> {
            Category c = repo.getCategorySync(id);
            if(c != null) {
                category.postValue(c);
            } else {
                category.postValue(new Category());
            }
        });
    }

    public void save() {
        //TODO
    }

    public boolean isValid() {
        // TODO
        return false;
    }
}
