package com.team.androidpos.model.repo;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.team.androidpos.model.dao.CudDao;

public class CudRepo<E> {

    private CudDao<E> dao;

    public CudRepo(CudDao<E> dao) {
        this.dao = dao;
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(E entity) {
        dao.insert(entity);
    }
    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void update(E entity) {
        dao.update(entity);
    }
    public void delete(E entity) {
        dao.delete(entity);
    }

}
