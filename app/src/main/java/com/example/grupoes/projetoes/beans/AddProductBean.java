package com.example.grupoes.projetoes.beans;

/**
 * Created by Hadrizia on 28/02/2017.
 */

public class AddProductBean {
    private String creator;
    private String pointOfSale;
    private String productName;
    private String productComment;
    private double productPrice;
    private String productImage;

    public AddProductBean(){}

    public AddProductBean(String creator, String pointOfSale, String productName, String productComment, double productPrice, String productImage) {
        this.creator = creator;
        this.pointOfSale = pointOfSale;
        this.productName = productName;
        this.productComment = productComment;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductComment() {
        return productComment;
    }

    public void setProductComment(String productComment) {
        this.productComment = productComment;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "AddProductBean{" +
                "creator='" + creator + '\'' +
                ", pointOfSale='" + pointOfSale + '\'' +
                ", productName='" + productName + '\'' +
                ", productComment='" + productComment + '\'' +
                ", productPrice=" + productPrice +
                ", productImage='" + productImage + '\'' +
                '}';
    }
}
