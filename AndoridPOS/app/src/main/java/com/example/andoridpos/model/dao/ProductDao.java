package com.example.andoridpos.model.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.andoridpos.model.entity.Product;

import java.util.List;

@Dao
public interface ProductDao extends CudDao<Product> {

    @Query("SELECT * FROM Product WHERE id = :id LIMIT 1")
    Product findByIdSync(int id);

    @Query("SELECT * FROM Product WHERE id = :id LIMIT 1")
    LiveData<Product> findById(int id);

    //paging
    @Query("SELECT * FROM Product")
    DataSource.Factory<Integer, Product> findAll();
}
