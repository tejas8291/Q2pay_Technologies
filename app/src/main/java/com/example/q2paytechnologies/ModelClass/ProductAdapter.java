package com.example.q2paytechnologies.ModelClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.q2paytechnologies.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private Context context;
    private JSONArray product_data;

    private ProductClickListener productClickListener;

    // constructor
    public ProductAdapter ( Context context,JSONArray product_data,ProductClickListener productClickListener ) {
        this.context=context;
        this.product_data = product_data;
        this.productClickListener = productClickListener;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent, int viewType ) {

        View view=LayoutInflater.from( parent.getContext() ).inflate( R.layout.product_list_single_item, parent, false );
        return new ProductAdapter.ViewHolder( view );

    }
    @Override
    public void onBindViewHolder ( @NonNull ProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position ) {

        try {
            JSONObject jsonObject = product_data.getJSONObject(position);
            String  title = jsonObject.getString("title");
            String  description = jsonObject.getString("description");
            String  price = jsonObject.getString("price");
            String  brand = jsonObject.getString("brand");
            String image = jsonObject.getString( "images" );
            try {
                // Parse the JSON array
                JSONArray imagesArray = new JSONArray(image);
                String firstImageUrl = imagesArray.getString(0);
                Picasso.get().load(firstImageUrl).into(holder.product_civ);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            holder.productName_tv.setText(title);
            holder.productDesc_tv.setText( description );
            holder.productPrice_tv.setText( price );
            holder.productDetails_tv.setText( brand );
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick ( View view ) {
                    productClickListener.onClick( position );
                }
            } );

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount () {

       return product_data.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName_tv,productPrice_tv,productDesc_tv,productDetails_tv;
        private CircleImageView product_civ;


        public ViewHolder ( @NonNull View itemView ) {
            super( itemView );
            productName_tv=itemView.findViewById( R.id.productName_tv );
            productPrice_tv=itemView.findViewById( R.id.productPrice_tv );
            productDesc_tv=itemView.findViewById( R.id.productDesc_tv );
            productDetails_tv=itemView.findViewById( R.id.productDetails_tv );
            product_civ=itemView.findViewById( R.id.product_civ );

        }
    }

    public interface ProductClickListener {
        void onClick( int pos);

    }
}
