package com.example.grupoes.projetoes.domain.repository;

import com.example.grupoes.projetoes.controllers.PointsController;
import com.example.grupoes.projetoes.models.PointOfSale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley on 17/03/2017.
 */

public class PointsRepository {
    private static volatile PointsRepository instance;

    private List<PointOfSale> pointsOfSale;

    public static PointsRepository getInstance() {
        if (instance == null) {
            instance = new PointsRepository();
        }

        return instance;
    }

    public PointsRepository() {
        this.pointsOfSale = new ArrayList<>();
    }

    public synchronized List<PointOfSale> getPoints() {
        return pointsOfSale;
    }

    public synchronized  void setPoints(List<PointOfSale> pointsOfSale) {
        this.pointsOfSale = pointsOfSale;
    }

    public PointOfSale findPointOfSaleByName(String name) {
        if (pointsOfSale != null) {
            for (PointOfSale pointOfSale : pointsOfSale) {
                if (pointOfSale.getName().equals(name)) {
                    return pointOfSale;
                }

            }
        }

        return null;
    }
}
