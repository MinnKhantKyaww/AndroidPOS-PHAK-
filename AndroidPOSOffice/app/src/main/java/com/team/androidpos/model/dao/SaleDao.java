package com.team.androidpos.model.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.team.androidpos.model.entity.Sale;
import com.team.androidpos.model.entity.SaleProduct;
import com.team.androidpos.model.vo.SaleReportVO;

import org.joda.time.LocalDateTime;

import java.util.Date;
import java.util.List;

@Dao
public abstract class SaleDao implements CudDao<Sale> {

    @Insert
    public abstract long insertAndGet(Sale sale);

    @Query("SELECT COUNT (*) FROM Sale")
    public abstract long count();

    @Insert
    public abstract void insert(List<SaleProduct> list);

    @Query("SELECT * FROM SALE ORDER BY sale_date_time DESC")
    public abstract DataSource.Factory<Integer, Sale> findAll();

    @Transaction
    public long save(Sale sale, List<SaleProduct> list) {
        long count = count();
        sale.setSaleDateTime(LocalDateTime.now());
        sale.setVoucherCode("#" + (count > 0 ? count + 1 : 1));
        long id = insertAndGet(sale);

        for(SaleProduct sp : list) {
            sp.setSaleId(id);
            sp.getId().setSaleTime(new Date().getTime());
        }
        insert(list);
        return id;
    }

    @Query("SELECT * FROM Sale WHERE id = :id LIMIT 1")
    public abstract LiveData<Sale> findById(long id);

    @Query("SELECT * FROM sale_product WHERE sale_id = :saleId ")
    public abstract LiveData<List<SaleProduct>> findSaleProducts(long saleId);

    @Query("SELECT c.name AS category, SUM(sp.price * sp.quantity) AS price FROM sale_product sp " +
            "LEFT JOIN product p ON p.id = sp.product_id " +
            "LEFT JOIN category c ON c.id = p.category_id " +
            "GROUP BY c.id, c.name")
    public abstract LiveData<List<SaleReportVO>> findSaleReport();
}
