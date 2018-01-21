package com.example.mypc.frider.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mypc.frider.R;
import com.example.mypc.frider.adapters.CheckRecyclerViewAdapter;
import com.example.mypc.frider.model.ProductObject;
import com.example.mypc.frider.util.GlobalPreference;
import com.example.mypc.frider.util.SimpleDividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG = CheckoutActivity.class.getSimpleName();

    private RecyclerView checkRecyclerView;

    private TextView subTotal;

    private double mSubTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("Over Cart");


        checkRecyclerView = (RecyclerView)findViewById(R.id.checkout_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CheckoutActivity.this);
        checkRecyclerView.setLayoutManager(linearLayoutManager);
        checkRecyclerView.setHasFixedSize(true);
        checkRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(CheckoutActivity.this));

        // get content of cart
        GlobalPreference mShared = new GlobalPreference(CheckoutActivity.this);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        ProductObject[] addCartProducts = gson.fromJson(mShared.RetriveProductFromCart(), ProductObject[].class);
        List<ProductObject> productList = convertObjectArrayToListObject(addCartProducts);

        CheckRecyclerViewAdapter mAdapter = new CheckRecyclerViewAdapter(CheckoutActivity.this, productList);
        checkRecyclerView.setAdapter(mAdapter);

        mSubTotal = getTotalPrice(productList);



        Button shoppingButton = (Button)findViewById(R.id.shopping);
        assert shoppingButton != null;
        shoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent shoppingIntent = new Intent(CheckoutActivity.this, ShopActivi.class);
//                startActivity(shoppingIntent);
//                Fragment fragment = null;
//                fragment =new ShopingFragment();
//
//                if (fragment != null) {
//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.content_frame, fragment);
//                    ft.commit();
//                }
//
//

            }
        });

        Button checkButton = (Button)findViewById(R.id.checkout);
        assert checkButton != null;
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),CashOnDelivery.class));
            }
        });
    }

    private List<ProductObject> convertObjectArrayToListObject(ProductObject[] allProducts){
        List<ProductObject> mProduct = new ArrayList<ProductObject>();
        Collections.addAll(mProduct, allProducts);
        return mProduct;
    }

    private int returnQuantityByProductName(String productName, List<ProductObject> mProducts){
        int quantityCount = 0;
        for(int i = 0; i < mProducts.size(); i++){
            ProductObject pObject = mProducts.get(i);
            if(pObject.getProductName().trim().equals(productName.trim())){
                quantityCount++;
            }
        }
        return quantityCount;
    }

    private double getTotalPrice(List<ProductObject> mProducts){
        double totalCost = 0;
        for(int i = 0; i < mProducts.size(); i++){
            ProductObject pObject = mProducts.get(i);


//            totalCost = totalCost + Double.parseDouble(pObject.getPrize());
        }
        return totalCost;
    }
}
