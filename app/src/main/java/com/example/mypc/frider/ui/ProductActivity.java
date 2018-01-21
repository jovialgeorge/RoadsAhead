package com.example.mypc.frider.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mypc.frider.R;
import com.example.mypc.frider.adapters.ServerImageParseAdapter;
import com.example.mypc.frider.model.ProductObject;
import com.example.mypc.frider.util.GlobalPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private static final String TAG = ProductActivity.class.getSimpleName();

    private TextView productSize, productColor, productPrice, productDescription;
    private NetworkImageView productImage;
    GlobalPreference globalPreference;
    Gson gson;

    private int cartProductNumber = 0;
    ImageLoader imageLoader1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        productImage=(NetworkImageView)findViewById(R.id.full);
        productSize=(TextView)findViewById(R.id.product_size);
        productColor=(TextView)findViewById(R.id.product_color);
        productPrice=(TextView)findViewById(R.id.product_price);
        productDescription=(TextView)findViewById(R.id.product_description);
         globalPreference=new GlobalPreference(getApplicationContext());
        GsonBuilder Builder= new GsonBuilder();
        gson=Builder.create();
        String ProductInStringFormat=getIntent().getExtras().getString("PRODUCT");
        final ProductObject singleProduct =gson.fromJson(ProductInStringFormat,ProductObject.class);



        if(singleProduct!=null){
            setTitle(singleProduct.getProductName());

            imageLoader1 = ServerImageParseAdapter.getInstance(getApplicationContext()).getImageLoader();
            imageLoader1.get(singleProduct.getProuductServerUrl(),
                    ImageLoader.getImageListener(
                            productImage,//Server Image
                            R.mipmap.ic_launcher,//Before loading server image the default showing image.
                            android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                    )
            );

            productImage.setImageUrl(singleProduct.getProuductServerUrl(),imageLoader1);
            productSize.setText("Size: " +singleProduct.getSize());
            productColor.setText("Color: " + singleProduct.getColor());
            productPrice.setText("Price: " + singleProduct.getPrize());
            productDescription.setText(Html.fromHtml("<strong>Product Description</strong><br/>" + singleProduct.getDescription()));


        }

        Button addToCartButton = (Button) findViewById(R.id.add_to_cart);

        assert addToCartButton != null;

        assert addToCartButton != null;

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productFromCart = globalPreference.RetriveProductFromCart();
                if (productFromCart.equals("")) {
                    List<ProductObject> cartProductList = new ArrayList<ProductObject>();
                    cartProductList.add(singleProduct);
                    String cartValue = gson.toJson(cartProductList);
                    globalPreference.addProductToCart(cartValue);
                    cartProductNumber = cartProductList.size();

                } else {

                    String productsInCart = globalPreference.RetriveProductFromCart();
                    ProductObject[] storedProducts = gson.fromJson(productsInCart, ProductObject[].class);

                    List<ProductObject> allNewProduct = convertObjectArrayToListObject(storedProducts);
                    allNewProduct.add(singleProduct);
                    String addAndStoreNewProduct = gson.toJson(allNewProduct);
                    globalPreference.addProductToCart(addAndStoreNewProduct);
                    cartProductNumber = allNewProduct.size();
                }
                globalPreference.AddProductCount(cartProductNumber);
                invalidateCart();
            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_shop);
        int mCount = globalPreference.retrieveProductCount();
        menuItem.setIcon(buildCounterDrawable(mCount, R.drawable.cart));
        return true;
    }

    private List<ProductObject> convertObjectArrayToListObject(ProductObject[] allProducts) {
        List<ProductObject> mProduct = new ArrayList<ProductObject>();
        Collections.addAll(mProduct, allProducts);
        return mProduct;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_shop) {
         Intent checkoutIntent = new Intent(ProductActivity.this, CheckoutActivity.class);
            startActivity(checkoutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.shopping_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    private void invalidateCart() {
        invalidateOptionsMenu();
    }

}
