package com.example.andoridpos.model.repo;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.andoridpos.model.dao.CategoryDao;
import com.example.andoridpos.model.dao.ProductDao;
import com.example.andoridpos.model.entity.Category;
import com.example.andoridpos.model.entity.Product;

import java.util.List;

public class ProductRepo extends CudRepo<Product> {

    private ProductDao dao;

    public ProductRepo(ProductDao dao) {
        super(dao);
        this.dao = dao;
    }

    public LiveData<Product> getProduct(int id) {

        return dao.findById(id);
    }

    @WorkerThread
    public Product getProductSync(int id) {

        return dao.findByIdSync(id);
    }

    public LiveData<PagedList<Product>> getAll() {

        return new LivePagedListBuilder<>(dao.findAll(), 25).build();
    }


}
