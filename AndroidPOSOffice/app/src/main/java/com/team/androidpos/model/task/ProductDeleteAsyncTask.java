package com.team.androidpos.model.task;

import android.os.AsyncTask;

import com.team.androidpos.model.dao.ProductDao;

public class ProductDeleteAsyncTask extends AsyncTask<Integer, Void, Void> {

    private ProductDao productDao;

    public ProductDeleteAsyncTask(ProductDao dao) {
        productDao = dao;
    }

    @Override
    protected Void doInBackground(Integer... data) {
        try {
            if(data.length > 0) {
                productDao.delete(productDao.findByIdSync(data[0]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
