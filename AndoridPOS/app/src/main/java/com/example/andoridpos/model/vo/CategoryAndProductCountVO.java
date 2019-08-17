package com.example.andoridpos.model.vo;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import com.example.andoridpos.model.entity.Category;

import java.util.Objects;

public class CategoryAndProductCountVO {

    @Embedded
    private Category category;

    @ColumnInfo(name = "product_count")
    private long productCount;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getProductCount() {
        return productCount;
    }

    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryAndProductCountVO)) return false;
        CategoryAndProductCountVO that = (CategoryAndProductCountVO) o;
        return getProductCount() == that.getProductCount() &&
                Objects.equals(getCategory(), that.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategory(), getProductCount());
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
