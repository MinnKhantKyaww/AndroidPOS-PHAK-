package com.example.andoridpos.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.andoridpos.model.entity.Category;

import java.util.List;

@Dao
public interface CategoryDao extends CudDao<Category> {

    @Query("SELECT * FROM Category")
    LiveData<List<Category>> findAll();

    @Query("SELECT * FROM Category WHERE id = :id LIMIT 1")
    LiveData<Category> findById(int id);

    @Query("SELECT * FROM Category WHERE id = :id LIMIT 1")
    Category findBySync(int id);
}
