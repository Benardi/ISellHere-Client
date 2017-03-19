package com.example.grupoes.projetoes.models;

/**
 * Created by Hadrizia on 28/02/2017.
 */

public class Product {
    private String creator;
    private String pointOfSale;
    private String productName;
    private String productComment;
    private double productPrice;
    private String productImage;

    public Product(){

    }

    public Product(String creator, String pointOfSale, String productName, String productComment, double productPrice, String productImage) {
        this.creator = creator;
        this.pointOfSale = pointOfSale;
        this.productName = productName;
        this.productComment = productComment;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductComment() {
        return productComment;
    }

    public void setProductComment(String productComment) {
        this.productComment = productComment;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    @Override
    public int hashCode() {
        int result = pointOfSale != null ? pointOfSale.hashCode() : 0;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product)) {
            return false;
        }

        Product other = (Product) obj;

        if (other.getProductName().equals(this.getProductName()) && other.getPointOfSale().equals(this.getPointOfSale())) {
            return true;
        }

        return false;
    }
}
