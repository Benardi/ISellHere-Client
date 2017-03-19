package com.example.grupoes.projetoes.domain.repository;

import android.util.Log;

import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Wesley on 17/03/2017.
 */

public class ProductsRepository {
    private static volatile ProductsRepository instance;

    private Map<String, List<Product>> products;

    public static ProductsRepository getInstance() {
        if (instance == null) {
            instance = new ProductsRepository();
        }

        return instance;
    }

    public ProductsRepository() {
        this.products = new HashMap<>();
    }

    public synchronized Map<String, List<Product>> getProducts() {
        return products;
    }

    public synchronized  void setProducts(Map<String, List<Product>> product) {
        this.products = product;
    }

    public void insertAll(List<Product> p) {
        for (Product product : p) {
            if (!products.containsKey(product.getPointOfSale())) {
                products.put(product.getPointOfSale(), new ArrayList<Product>());
            }

            products.put(product.getPointOfSale(), p);
        }
    }

    public List<Product> findAllProductsByPointOfSale(String point) {
        List<Product> rProducts = new ArrayList<>();

        if (products.get(point) != null) {
            for (Product product : products.get(point)) {
                rProducts.add(product);
            }
        }

        return rProducts;
    }

    public Product findProduct(String product) {
        for (String pos : products.keySet()) {
            for (Product pt : products.get(pos)) {
                if (pt.getProductName().equals(product)) {
                    return pt;
                }
            }
        }

        return null;
    }

    public Product findProductByPointOfSale(String point, String product) {
        if (products.get(point) != null) {
            for (Product iproduct : products.get(point)) {
                if (iproduct.getProductName().equals(product)) {
                    return iproduct;
                }
            }
        }

        return null;
    }

    public void removeProductByPointAndName(String point, String product) {
        products.get(point).remove(product);
    }
}
