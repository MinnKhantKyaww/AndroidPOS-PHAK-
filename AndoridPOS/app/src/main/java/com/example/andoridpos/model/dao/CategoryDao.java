package com.example.andoridpos.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.andoridpos.model.entity.Category;
import com.example.andoridpos.model.vo.CategoryAndProductCountVO;

import java.util.List;

@Dao
public interface CategoryDao extends CudDao<Category> {

    @Query("SELECT * FROM Category")
    LiveData<List<Category>> findAll();

    @Query("SELECT * FROM Category WHERE id = :id LIMIT 1")
    LiveData<Category> findById(int id);

    @Query("SELECT * FROM Category WHERE id = :id LIMIT 1")
    Category findBySync(int id);

    @Query("SELECT c.id, c.name, COUNT(p.id) AS product_count FROM Category c LEFT JOIN Product p ON " +
            "c.id = p.category_id " +
            "GROUP BY c.id, c.name ")
    LiveData<List<CategoryAndProductCountVO>> findCategoryAndProductCount();
}
