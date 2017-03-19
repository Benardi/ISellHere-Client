package com.example.grupoes.projetoes.beans;

/**
 * Created by Wesley on 26/02/2017.
 */

public class GetProductsInPointBean {
    private String pointName;

    public GetProductsInPointBean() {

    }

    public GetProductsInPointBean(String pointName) {
        this.pointName = pointName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }
}
