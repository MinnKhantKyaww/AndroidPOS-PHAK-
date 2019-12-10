package com.team.androidpos.model.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.team.androidpos.BR;

import org.joda.time.LocalDateTime;

import java.util.Objects;

@Entity
public class Sale extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "voucher_code")
    private String voucherCode;
    @ColumnInfo(name = "total_price")
    private double totalPrice;
    @ColumnInfo(name = "total_product")
    private int totalProduct;
    @ColumnInfo(name = "sale_date_time")
    private LocalDateTime saleDateTime;
    @ColumnInfo(name = "pay_price")
    private double payPrice;
    private double change;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public LocalDateTime getSaleDateTime() {
        return saleDateTime;
    }

    public void setSaleDateTime(LocalDateTime saleDateTime) {
        this.saleDateTime = saleDateTime;
    }

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {

        this.payPrice = payPrice;
        this.change = payPrice > 0 ? (payPrice - totalPrice) : 0;
        notifyPropertyChanged(com.team.androidpos.BR.change);
    }

    public String getFormattedDateTime() {
        return saleDateTime.toString("MMM dd, yyyy hh:mm a");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sale)) return false;
        Sale sale = (Sale) o;
        return getId() == sale.getId() &&
                Double.compare(sale.getTotalPrice(), getTotalPrice()) == 0 &&
                getTotalProduct() == sale.getTotalProduct() &&
                Double.compare(sale.getPayPrice(), getPayPrice()) == 0 &&
                Double.compare(sale.getChange(), getChange()) == 0 &&
                Objects.equals(getVoucherCode(), sale.getVoucherCode()) &&
                Objects.equals(getSaleDateTime(), sale.getSaleDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVoucherCode(), getTotalPrice(), getTotalProduct(), getSaleDateTime(), getPayPrice(), getChange());
    }

    @Bindable
    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;

    }


}
