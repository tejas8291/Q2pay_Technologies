package com.example.q2paytechnologies.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q2paytechnologies.ApiService.APIServices;
import com.example.q2paytechnologies.ModelClass.ProductModel;
import com.example.q2paytechnologies.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView back_iv;

    private ImageView product_img;

    private int id=0;
    private TextView productTitle_tv, productPrice_tv, productDesc_tv,discount_tv,rating_tv, stock_tv,brand_tv;
    private APIServices apiServices;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_product_details );

        back_iv=findViewById( R.id.back_iv );
        productTitle_tv=findViewById( R.id.productTitle_tv );
        productPrice_tv=findViewById( R.id.productPrice_tv );
        productDesc_tv=findViewById( R.id.productDesc_tv );
        discount_tv=findViewById( R.id.discount_tv );
        rating_tv=findViewById( R.id.rating_tv );
        stock_tv=findViewById( R.id.stock_tv );
        brand_tv=findViewById( R.id.brand_tv );
        product_img = findViewById( R.id.product_img );
        Intent intent=getIntent();
        id=intent.getIntExtra( "id", 0 );
        back_iv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                finish();
            }
        } );


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl( "https://dummyjson.com/" )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        apiServices=retrofit.create( APIServices.class );
        getProduct( id );


    }

    private void getProduct ( int id ) {
        Call < ProductModel > call=apiServices.getProduct( id );
        call.enqueue( new Callback < ProductModel >() {
            @Override
            public void onResponse ( Call < ProductModel > call, Response < ProductModel > response ) {
                if (response.isSuccessful()) {
                    ProductModel product=response.body();
                    if (product != null) {
                        productTitle_tv.setText( product.getTitle() );
                        productPrice_tv.setText( String.valueOf( product.getPrice() ) );
                        productDesc_tv.setText( product.getDescription() );
                        stock_tv.setText( String.valueOf(product.getStock()) );
                        rating_tv.setText(String.valueOf( product.getRating() ));
                        discount_tv.setText( String.valueOf( product.getDiscountPercentage() ) );
                        brand_tv.setText( product.getBrand() );
                        String image = product.getThumbnail();
                        if(!image.isEmpty()){
                            Picasso.get().load(image).into( product_img );
                        }


                    }
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure ( Call < ProductModel > call, Throwable t ) {
                // Handle failure
            }
        } );
    }

}