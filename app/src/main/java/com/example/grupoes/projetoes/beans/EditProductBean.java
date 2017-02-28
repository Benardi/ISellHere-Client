package com.example.grupoes.projetoes.beans;

/**
 * Created by Hadrizia on 28/02/2017.
 */

public class EditProductBean {
    String requester;
    String productName;
    String productComment;
    double productPrice;
    String productImage;
    String selectedProduct;

    public EditProductBean(){}

    public EditProductBean(String requester, String productName, String productComment, double productPrice, String productImage, String selectedProduct) {
        this.requester = requester;
        this.productName = productName;
        this.productComment = productComment;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.selectedProduct = selectedProduct;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
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

    public String getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(String selectedProduct) {
        this.selectedProduct = selectedProduct;
    }
}
