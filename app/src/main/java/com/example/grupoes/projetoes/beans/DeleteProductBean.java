package com.example.grupoes.projetoes.beans;

/**
 * Created by Hadrizia on 28/02/2017.
 */

public class DeleteProductBean {
    String requester;
    String productName;

    public DeleteProductBean(){}

    public DeleteProductBean(String requester, String productName) {
        this.requester = requester;
        this.productName = productName;
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
}
