package com.example.grupoes.projetoes.presentation.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupoes.projetoes.ISellHereApplication;
import com.example.grupoes.projetoes.R;
import com.example.grupoes.projetoes.beans.EditProductBean;
import com.example.grupoes.projetoes.controllers.ProductController;
import com.example.grupoes.projetoes.domain.repository.ProductsRepository;
import com.example.grupoes.projetoes.localstorage.SessionStorage;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.presentation.presenters.api.EditProductPresenter;
import com.example.grupoes.projetoes.presentation.presenters.factory.PresenterFactory;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InvalidInput;
import com.example.grupoes.projetoes.util.UtilOperations;

import java.util.List;

public class EditProductActivity extends AppCompatActivity implements EditProductPresenter.View {
    private static final int RESULT_LOAD_IMAGE = 1;

    ImageView productImage;
    EditText productName;
    EditText productDescription;
    EditText productPrice;
    TextView buttonGetImageProduct;
    Button close;
    Button save;
    ImageView imageView;

    private Product product;

    private EditProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        final Intent intent = getIntent();
        final String nameProduct = intent.getExtras().getString("PRODUCT_NAME");
        final String namePoint = intent.getExtras().getString("POINT_NAME");

        product = ProductsRepository.getInstance().findProductByPointOfSale(namePoint, nameProduct);
        presenter = PresenterFactory.getInstance().createEditProductPresenter(this);
        configureComponents();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }

        buttonGetImageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionStorage storage =  new SessionStorage(ISellHereApplication.getInstance());
                String requester = storage.getLoggedUser().getUsername();
                String token = storage.getToken();

                String productImageStr = UtilOperations.bitMapToString(((BitmapDrawable) productImage.getDrawable()).getBitmap());

                EditProductBean bodyData = new EditProductBean(requester, productName.getText().toString(), productDescription.getText().toString(), String.valueOf(productPrice.getText().toString()), productImageStr, product.getProductName().toString());

                presenter.requestEditProduct(bodyData);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
    }

    private void configureComponents() {
        if(product != null){
            productName = (EditText) findViewById(R.id.editNameProductText);
            productImage = (ImageView) findViewById(R.id.editImageProductView);
            productDescription = (EditText) findViewById(R.id.editDescriptionProductText);
            productPrice = (EditText) findViewById(R.id.editPriceProductText);
            buttonGetImageProduct = (TextView) findViewById(R.id.editProductImagemButton);
            save = (Button) findViewById(R.id.saveButtonEditProduct);
            close = (Button) findViewById(R.id.close_edit_product);
            imageView = (ImageView) findViewById(R.id.product_image);

            productName.setText(product.getProductName());
            productPrice.setText(String.valueOf(product.getProductPrice()));

            if(product.getProductImage() != null){
                productImage.setImageBitmap(UtilOperations.StringToBitMap(product.getProductImage()));
            }
            productDescription.setText(product.getProductComment());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageView = (ImageView) findViewById(R.id.editImageProductView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(EditProductActivity.this, ProductActivity.class);
                intent.putExtra("PRODUCT_NAME", product.getProductName());
                intent.putExtra("POINT_NAME", product.getPointOfSale());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(EditProductActivity.this, ProductActivity.class);
        i.putExtra("PRODUCT_NAME", product.getProductName());
        startActivity(i);
    }

    @Override
    public void onSuccessfulEdit() {
        Intent intent1 = new Intent(EditProductActivity.this, PointOfSaleActivity.class);
        intent1.putExtra("POINT_NAME", product.getPointOfSale());
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
        finish();
        Toast.makeText(getApplicationContext(), "Your product has been successfully updated!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInvalidInput(List<InvalidInput> invalidInputs) {
        for (InvalidInput invalidInput : invalidInputs) {
            InputType type = invalidInput.getType();
            String error = invalidInput.getError();

            if (type == InputType.ADDPRODUCT_NAME) {
                productName.setError(error);
            } else if (type == InputType.ADDPRODUCT_DESCRIPTION) {
                productDescription.setError(error);
            } else if (type == InputType.ADDPRODUCT_PRICE) {
                productPrice.setError(error);
            }
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
