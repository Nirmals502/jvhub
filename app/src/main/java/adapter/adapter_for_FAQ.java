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


import com.example.deepsingh.jbhub.Answer_screen;
import com.example.deepsingh.jbhub.Detail_activity_;
import com.example.deepsingh.jbhub.MapsActivity;
import com.example.deepsingh.jbhub.Property_detail_info;
import com.example.deepsingh.jbhub.R;

import java.util.ArrayList;
import java.util.HashMap;


public class adapter_for_FAQ extends RecyclerView.Adapter<adapter_for_FAQ.MyViewHolder> {

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

            Rlv_holder = (RelativeLayout) view.findViewById(R.id.Rlv_holder);

        }
    }
// mAdapter = new Detail_adapter(listdata,specification,description,lat,lng,floor_plan);

    public adapter_for_FAQ(ArrayList<HashMap<String, String>> listdata) {
        this.listdata = listdata;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_layout, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.Tittle.setText(listdata.get(position).get("FAQ"));
        final String Str_question = listdata.get(position).get("FAQ");
        final String Str_Answer = listdata.get(position).get("ans");
        holder.Rlv_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (position == 0) {
                    Intent i1 = new Intent(context, Answer_screen.class);
                    i1.putExtra("Question", Str_question);
                    i1.putExtra("Answer", Str_Answer);
                    context.startActivity(i1);
//

            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
}