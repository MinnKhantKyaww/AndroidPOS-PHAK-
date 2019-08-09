package com.example.andoridpos.ui.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.andoridpos.ServiceLocator;
import com.example.andoridpos.model.entity.Category;
import com.example.andoridpos.model.repo.CategoryRepo;

import java.util.List;

public class CategoriesViewMode extends AndroidViewModel {

    private CategoryRepo repo;

    private LiveData<List<Category>> categories;

    public CategoriesViewMode(@NonNull Application application) {
        super(application);
        this.repo = ServiceLocator.getInstance(application).categoryRepo();
    }

    public LiveData<List<Category>> getCategories() {
        if (categories == null) {
            categories = repo.getAll();
        }

        return categories;
    }
}
