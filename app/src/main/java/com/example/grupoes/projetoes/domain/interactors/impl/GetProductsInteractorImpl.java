package com.example.grupoes.projetoes.domain.interactors.impl;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.GetPointsInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.GetProductsInteractor;
import com.example.grupoes.projetoes.domain.interactors.base.AbstractInteractor;
import com.example.grupoes.projetoes.domain.repository.ProductsRepository;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.request_handlers.ProductHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley on 15/03/2017.
 */

public class GetProductsInteractorImpl extends AbstractInteractor implements GetProductsInteractor {
    private Callback callback;

    private String pointOfSale;

    public GetProductsInteractorImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     Callback callback,
                                     String pointOfSale) {
        super(threadExecutor, mainThread);

        this.callback = callback;
        this.pointOfSale = pointOfSale;
    }

    private void notifyError(final String msg) {
        getMainThread().post(new Runnable() {
            @Override
            public void run() {
                callback.onProductsRetrievalFailed(msg);
            }
        });
    }

    private void postMessage(JSONArray response) {
        final List<Product> products = new ArrayList<>();

        try {

            for (int i = 0; i < response.length(); i++) {
                Product product = convertResponse((JSONObject) response.get(i));
                products.add(product);
            }
            Log.d("COLOCOU", pointOfSale);
            Log.d("COLOCOU", products.size() + "");
            ProductsRepository.getInstance().getProducts().put(pointOfSale, products);

            getMainThread().post(new Runnable() {
                @Override
                public void run() {
                    callback.onProductsRetrieved(products);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            notifyError("It is all wrong.");
        }

    }

    @Override
    public void run() {
        ProductHandler.getInstance().requestGetProducts(pointOfSale, new SessionStorage(ISellHereApplication.getInstance()).getToken(),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                       postMessage(response);
                    }

                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        notifyError("It was not possible to retrieve the products. Try again later.");
                    }

                });
    }

    private Product convertResponse(JSONObject object) throws JSONException {
        String creator = object.getString("creator");
        String pointOfSale = object.getString("pointOfSale");
        String productName = object.getString("name");
        String productComment = object.getString("comment");
        double productPrice = object.getDouble("price");
        String productImage = object.getString("image");

        return new Product(creator, pointOfSale, productName, productComment, productPrice, productImage);
    }
}
