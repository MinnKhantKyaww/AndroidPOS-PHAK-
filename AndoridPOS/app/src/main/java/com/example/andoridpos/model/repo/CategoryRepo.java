package com.example.andoridpos.model.repo;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.example.andoridpos.model.dao.CategoryDao;
import com.example.andoridpos.model.dao.CudDao;
import com.example.andoridpos.model.entity.Category;
import com.example.andoridpos.model.vo.CategoryAndProductCountVO;

import java.util.List;

public class CategoryRepo extends CudRepo<Category> {

    private CategoryDao dao;

    public CategoryRepo(CategoryDao dao) {
        super(dao);
        this.dao = dao;
    }

    public LiveData<Category> getCategory(int id) {

        return dao.findById(id);
    }

    public LiveData<List<Category>> getAll() {

        return dao.findAll();
    }

    public List<Category> getAllSync() {

        return dao.findAllSync();
    }

    @WorkerThread
    public Category getCategorySync(int id) {

        return dao.findBySync(id);
    }

    public LiveData<List<CategoryAndProductCountVO>> getCategoryAndProductCount() {

        return dao.findCategoryAndProductCount();
    }

    public void save(Category category) {
        if(category.getId() > 0) {
            super.update(category);
        } else {
            super.insert(category);
        }

    }
}
