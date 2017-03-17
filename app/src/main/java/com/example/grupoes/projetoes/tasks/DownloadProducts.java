package com.example.grupoes.projetoes.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.grupoes.projetoes.controllers.ProductController;

/**
 * Created by Hadrizia on 02/03/2017.
 */

public class DownloadProducts extends AsyncTask<String, String, String> {
    final ProgressDialog progressDialog;
    Context context;

    public DownloadProducts(Context context){
        this.context = context;
        this.progressDialog = new ProgressDialog(context);

    }


    @Override
    protected String doInBackground(String... pointofsale) {
        String result = null;
        try {
            while (progressDialog.getProgress() <= progressDialog.getMax()) {
                progressDialog.incrementProgressBy(5);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(ProductController.getInstance().getProductsList() != null){
                result = "ok";
            }
        }
        return result;
    }


    @Override
    protected void onPostExecute(String params) {
        if(params.equals("ok")){
            Log.d("TASK", "OK");
        }
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

}