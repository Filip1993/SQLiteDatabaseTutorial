package com.filipkesteli.sqlitedatabasetutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnInsertNewProduct;
    private Button btnQuery1LookupProduct;
    private Button btnDeleteRemoveProduct;

    private TextView tvProductID;
    private EditText etProductName;
    private EditText etProductQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        setupListeners();
    }

    private void initWidgets() {
        btnInsertNewProduct = (Button) findViewById(R.id.btnInsertNewProduct);
        btnQuery1LookupProduct = (Button) findViewById(R.id.btnQuery1LookupProduct);
        btnDeleteRemoveProduct = (Button) findViewById(R.id.btnDeleteRemoveProduct);
    }

    private void setupListeners() {
        final MyDBHandler myDBHandler = new MyDBHandler(MainActivity.this, null, null, 1); //closure varijabla

        btnInsertNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = etProductName.getText().toString();
                int productQuantity = Integer.parseInt(etProductQuantity.getText().toString());
                Product product = new Product(productName, productQuantity);
                myDBHandler.INSERT_addProduct(product);
                etProductQuantity.setText("");
                etProductName.setText("");
            }
        });
        btnQuery1LookupProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = myDBHandler.QUERY_findProduct(etProductName.getText().toString());
                if (product != null) {
                    tvProductID.setText(String.valueOf(product.get_id()));
                    etProductQuantity.setText(String.valueOf(product.get_quantity()));
                } else {
                    tvProductID.setText("No Match Found");
                }
            }
        });
        btnDeleteRemoveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = myDBHandler.DELETE_deleteProduct(etProductName.getText().toString());
                if (result) {
                    tvProductID.setText("Record Deleted");
                    etProductName.setText("");
                    etProductQuantity.setText("");
                } else {
                    tvProductID.setText("No match found");
                }
            }
        });
    }
}