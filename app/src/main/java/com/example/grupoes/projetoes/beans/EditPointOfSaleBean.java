package com.example.grupoes.projetoes.beans;

/**
 * Created by Wesley on 28/02/2017.
 */

public class EditPointOfSaleBean {
    private String requester;
    private String selectedPoint;
    private String pointName;
    private String pointComment;
    private String pointImage;

    public EditPointOfSaleBean(String requester, String selectedPoint, String pointName, String pointComment, String pointImage) {
        this.requester = requester;
        this.selectedPoint = selectedPoint;
        this.pointName = pointName;
        this.pointComment = pointComment;
        this.pointImage = pointImage;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getSelectedPoint() {
        return selectedPoint;
    }

    public void setSelectedPoint(String selectedPoint) {
        this.selectedPoint = selectedPoint;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getPointComment() {
        return pointComment;
    }

    public void setPointComment(String pointComment) {
        this.pointComment = pointComment;
    }

    public String getPointImage() {
        return pointImage;
    }

    public void setPointImage(String pointImage) {
        this.pointImage = pointImage;
    }
}
