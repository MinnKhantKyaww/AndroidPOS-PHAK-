package com.example.andoridpos.model.repo;

import com.example.andoridpos.model.dao.CudDao;

public class CudRepo<E> {

    private CudDao<E> dao;

    public CudRepo(CudDao<E> dao) {
        this.dao = dao;
    }

    public void insert(E entity) {

    }

    public void update(E entity) {

    }

    public void delete(E entity) {

    }


}
