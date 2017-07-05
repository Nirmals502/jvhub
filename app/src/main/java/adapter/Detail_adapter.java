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


import com.example.deepsingh.jbhub.Detail_activity_;
import com.example.deepsingh.jbhub.MapsActivity;
import com.example.deepsingh.jbhub.Property_detail_info;
import com.example.deepsingh.jbhub.R;

import java.util.ArrayList;
import java.util.HashMap;


public class Detail_adapter extends RecyclerView.Adapter<Detail_adapter.MyViewHolder> {

    ArrayList<HashMap<String, String>> listdata = new ArrayList<HashMap<String, String>>();
    Context context;
    String Str_Specification, Str_description, Str_lat, Str_lng, Str_floor_plan;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Tittle, detail, address;
        ImageView Img_Vw_Icon;
        RelativeLayout Rlv_holder;

        public MyViewHolder(View view) {
            super(view);
            Tittle = (TextView) view.findViewById(R.id.textView17);
            Img_Vw_Icon = (ImageView) view.findViewById(R.id.imageView11);
            Rlv_holder = (RelativeLayout) view.findViewById(R.id.Rlv_holder);

        }
    }
// mAdapter = new Detail_adapter(listdata,specification,description,lat,lng,floor_plan);

    public Detail_adapter(ArrayList<HashMap<String, String>> listdata, String specification, String description, String Str_lat, String Str_lng, String Str_Floor_plan) {
        this.listdata = listdata;
        this.Str_Specification = specification;
        this.Str_description = description;
        this.Str_lat = Str_lat;
        this.Str_lng = Str_lng;
        this.Str_floor_plan = Str_Floor_plan;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {


            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyle_listview_detail_adapter, parent, false);
            context = parent.getContext();

            return new MyViewHolder(itemView);
        } catch (android.view.InflateException e) {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.Tittle.setText(listdata.get(position).get("Name"));
        holder.Rlv_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Intent i1 = new Intent(context, Detail_activity_.class);
                    i1.putExtra("value", Str_Specification);
                    i1.putExtra("value2", "Key Features");
                    context.startActivity(i1);
                } else if (position == 1) {
                    Intent i1 = new Intent(context, Detail_activity_.class);
                    i1.putExtra("value", Str_description);
                    i1.putExtra("value2", "Description");
                    context.startActivity(i1);
                } else if (position == 2) {
                    Intent i1 = new Intent(context, MapsActivity.class);
                    i1.putExtra("value_lat", Str_lat);
                    i1.putExtra("value_lng", Str_lng);
                    context.startActivity(i1);
                } else if (position == 3) {
                    Intent i1 = new Intent(context, Detail_activity_.class);
                    i1.putExtra("value", Str_floor_plan);
                    i1.putExtra("value2", "Floor Plan");
                    context.startActivity(i1);
                }

            }
        });
        String Str_value = listdata.get(position).get("image");
        try {


            int resID = context.getResources().getIdentifier(Str_value, "drawable", context.getPackageName());
            int image_id = Integer.valueOf(resID);

            holder.Img_Vw_Icon.setImageResource(image_id);
        } catch (java.lang.OutOfMemoryError e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
}