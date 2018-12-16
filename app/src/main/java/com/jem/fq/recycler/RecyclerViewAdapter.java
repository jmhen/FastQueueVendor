package com.jem.fq.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jem.fq.R;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "Custom Adapter";

    private ArrayList<ListItem> listItems;
    private Context context;

    public RecyclerViewAdapter(ArrayList<ListItem> listItems, Context context){
        this.listItems = listItems;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Customer_Id;
        public TextView FoodName;
        public TextView OrderId;
        public TextView Takeaway;
        public TextView Preorder;
        public RelativeLayout parentLayout;

        public MyViewHolder(View v) {
            super(v);
            Customer_Id = v.findViewById(R.id.customer_id);
            OrderId = v.findViewById(R.id.order_id);
            FoodName = v.findViewById(R.id.food_name);
            Preorder = v.findViewById(R.id.preorder_bool);
            Takeaway = v.findViewById(R.id.takeaway_bool);
            parentLayout = v.findViewById(R.id.parent_layout);
        }

        public TextView getCustomer_Id() {
            return Customer_Id;
        }

        public TextView getOrderId() {
            return OrderId;
        }

        public TextView getFoodName() {
            return FoodName;
        }

        public TextView getPreorderBool() {
            return Preorder;
        }

        public TextView getTakeawayBool() {
            return Takeaway;
        }
    }

    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG, "Element" + position + "set.");

        holder.getCustomer_Id().setText(listItems.get(position).getCustomer_id());
        holder.getOrderId().setText(listItems.get(position).getOrderid());
        holder.getFoodName().setText(listItems.get(position).getFoodname());
        holder.getPreorderBool().setText(listItems.get(position).getPreorder());
        holder.getTakeawayBool().setText(listItems.get(position).getTakeaway());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

}





