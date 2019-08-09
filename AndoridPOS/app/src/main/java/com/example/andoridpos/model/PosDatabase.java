package com.example.andoridpos.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.andoridpos.model.dao.CategoryDao;
import com.example.andoridpos.model.dao.ProductDao;
import com.example.andoridpos.model.entity.Category;
import com.example.andoridpos.model.entity.Product;

@Database(entities = {
        Category.class,
        Product.class
}, version = 1, exportSchema = false)
public abstract class PosDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();
}
