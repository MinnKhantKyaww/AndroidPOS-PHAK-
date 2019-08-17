package com.example.andoridpos.ui.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.andoridpos.R;
import com.example.andoridpos.ServiceLocator;
import com.example.andoridpos.model.entity.Category;
import com.example.andoridpos.model.repo.CategoryRepo;
import com.example.andoridpos.util.AppExecutors;

public class CategoriesEditViewModel extends AndroidViewModel {

    private CategoryRepo repo;
    public final MutableLiveData<Category> category = new MutableLiveData<>();

    public final MutableLiveData<String> error = new MutableLiveData<>();
    public final MutableLiveData<String> nameError = new MutableLiveData<>();
    public final MutableLiveData<Boolean> operation = new MutableLiveData<>();
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
        if(isValid()) {
            AppExecutors.io().execute(() -> {
                try {
                    repo.save(category.getValue());
                    operation.postValue(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    operation.postValue(false);
                    error.postValue(e.getMessage());
                }

            });
        }
    }

    private boolean isValid() {
        String name = category.getValue().getName();
        if(name == null || name.isEmpty()) {
            nameError.setValue(getApplication().getString(R.string.require_cateogry_name));
            return false;
        }
        return true;
    }
}
