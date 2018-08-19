package com.moonlay.litewill.model;

public class ProductList {
    private String name;
    private String code;
    private String period;
    private int unitOfPeriod;
    private int price;
    private String description;
    private String category;

    public ProductList(String name, String code, String period, int unitOfPeriod, int price, String description, String category) {
        this.name = name;
        this.code = code;
        this.period = period;
        this.unitOfPeriod = unitOfPeriod;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getUnitOfPeriod() {
        return unitOfPeriod;
    }

    public void setUnitOfPeriod(int unitOfPeriod) {
        this.unitOfPeriod = unitOfPeriod;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
