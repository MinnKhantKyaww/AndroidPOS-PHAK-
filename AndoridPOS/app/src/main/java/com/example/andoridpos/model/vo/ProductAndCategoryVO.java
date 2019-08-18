package com.example.andoridpos.model.vo;

import com.example.andoridpos.model.entity.Product;

import java.util.Objects;

public class ProductAndCategoryVO {

    private int id;

    private String name;

    private String image;

    private String category;

    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductAndCategoryVO)) return false;
        ProductAndCategoryVO that = (ProductAndCategoryVO) o;
        return getId() == that.getId() &&
                Double.compare(that.getPrice(), getPrice()) == 0 &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getImage(), that.getImage()) &&
                Objects.equals(getCategory(), that.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getImage(), getCategory(), getPrice());
    }
}
