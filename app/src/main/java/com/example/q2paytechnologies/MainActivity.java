package com.example.q2paytechnologies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.q2paytechnologies.Activity.ProductDetailsActivity;
import com.example.q2paytechnologies.ApiService.APIServices;
import com.example.q2paytechnologies.ApiService.ApiCallbackCode;
import com.example.q2paytechnologies.ApiService.ApiUrl;
import com.example.q2paytechnologies.ApiService.ApiUtility;
import com.example.q2paytechnologies.ApiService.Utility;
import com.example.q2paytechnologies.ModelClass.ProductAdapter;
import com.example.q2paytechnologies.ModelClass.ProductModel;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements ApiCallbackCode, ProductAdapter.ProductClickListener{
    private RecyclerView productList_rv;
    private String title = "", description = "", price = "",brand = "",id="";

    private JSONArray product_data;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        productList_rv=findViewById( R.id.productList_rv );
        product_data=new JSONArray();

        ApiUtility api=new ApiUtility( MainActivity.this, ApiUrl.BASE_URL, "", "Please Wait!", true );
        Retrofit retrofit=api.getRetrofitInstance();
        APIServices apiRequest=retrofit.create( APIServices.class );
        Call < JsonObject > responseCall=apiRequest.get_product_api();
        api.postRequest( responseCall, this, 1 );

    }
    @Override
    public void onClick ( int pos ) {
        try {
            JSONObject jsonObject = product_data.getJSONObject( pos );
            int id = jsonObject.getInt( "id" );
            String image = jsonObject.getString( "images" );
            try {
                // Parse the JSON array
                JSONArray imagesArray = new JSONArray(image);
                String firstImageUrl = imagesArray.getString(0);
                Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                intent.putExtra( "id", id);
                intent.putExtra( "firstImageUrl", firstImageUrl);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            throw new RuntimeException( e );
        }


    }

    @Override
    public void onResponse (JSONObject var1, int var2, int status_code ) {

        if (var1 != null) {
            if (var2 == 1) {
                try {
                    String products=var1.getString( "products" );
                    product_data = var1.getJSONArray( "products" );
                    ProductAdapter productAdapter = new ProductAdapter(MainActivity.this,product_data ,this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
                    productList_rv.setLayoutManager(linearLayoutManager);
                    productList_rv.setAdapter(productAdapter);
                } catch (Exception e) {

                }
            }
        }

    }

    @Override
    public void onFailure ( Object var1, String error_msg ) {

        if(!error_msg.equalsIgnoreCase("")){
            Toast.makeText(MainActivity.this, error_msg, Toast.LENGTH_LONG).show();
        }else{
            try {
                JSONObject jsonObject = new JSONObject(var1.toString());
                String message = jsonObject.getString("message");
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        }

    }
}