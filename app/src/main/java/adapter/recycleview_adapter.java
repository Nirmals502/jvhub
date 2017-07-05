package adapter;

/**
 * Created by Mr singh on 5/18/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.deepsingh.jbhub.MainActivity;
import com.example.deepsingh.jbhub.Property_detail_info;
import com.example.deepsingh.jbhub.Property_listing_screen;
import com.example.deepsingh.jbhub.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class recycleview_adapter extends RecyclerView.Adapter<recycleview_adapter.MyViewHolder> {
    Context context;
    ArrayList<HashMap<String, String>> listdata = new ArrayList<HashMap<String, String>>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Price, detail, address, Description;
        public ImageView Img_;
        RelativeLayout Rlv_Container;

        public MyViewHolder(View view) {
            super(view);
            Price = (TextView) view.findViewById(R.id.Txt_price);
            detail = (TextView) view.findViewById(R.id.textView20);
            address = (TextView) view.findViewById(R.id.textView27);
            Description = (TextView) view.findViewById(R.id.textView21);
            Img_ = (ImageView) view.findViewById(R.id.imageView9);
            Rlv_Container = (RelativeLayout) view.findViewById(R.id.rlv_container);

        }
    }


    public recycleview_adapter(ArrayList<HashMap<String, String>> listdata) {
        this.listdata = listdata;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recycleview_listing, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
     //   DecimalFormat formatter = new DecimalFormat("#,###,###");
        String number = listdata.get(position).get("Price");
        double amount = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formatted = formatter.format(amount);

       // holder.Price.setText("£" + listdata.get(position).get("Price"));
        holder.Price.setText("£" + formatted);
        holder.detail.setText(listdata.get(position).get("Floor_plan"));
        holder.address.setText(listdata.get(position).get("Address"));
        holder.Description.setText(listdata.get(position).get("Description"));
        //Description
        String image = listdata.get(position).get("image");
        Picasso.with(context)
                .load(image)
                .placeholder(R.drawable.backendbuilding)   // optional
                // optional
                // optional
                .into(holder.Img_);

        holder.Rlv_Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(context, Property_detail_info.class);
                i1.putExtra("id", listdata.get(position).get("id"));
                context.startActivity(i1);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
}