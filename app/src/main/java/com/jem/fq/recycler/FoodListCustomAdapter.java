/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.jem.fq.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jem.fq.R;

import java.util.ArrayList;



/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class FoodListCustomAdapter extends RecyclerView.Adapter<FoodListCustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private ArrayList<FoodItem> mDataSet;
    private Double sum;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView foodName;
        private CheckBox foodCheck;
        private TextView foodPrice;



        public ViewHolder(View v) {
            super(v);

            // Define click listener for the ViewHolder's View.
            foodName = (TextView) v.findViewById(R.id.food_name);
            foodCheck = v.findViewById(R.id.foodCheckBox);
            foodPrice = v.findViewById(R.id.food_price);
            foodCheck.setChecked(true);
            foodName.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                 }
            });
//            foodCheck = (CheckBox) v.findViewById(R.id.foodCheckBox);


        }

        public TextView getTextView() {
            return foodName;
        }
        public TextView getPriceView() {return foodPrice;}
        public CheckBox getFoodCheck() {return foodCheck;}
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public FoodListCustomAdapter(ArrayList<FoodItem> dataSet) {
        mDataSet = dataSet;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.food_item_cell, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");
        sum = 0.00;

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        holder.getTextView().setText(mDataSet.get(position).getFoodName());
        final TextView foodPrice = holder.getPriceView();
        holder.getPriceView().setText(mDataSet.get(position).getFoodPrice());
//        CheckBox priceCheck =  holder.getFoodCheck();
//        priceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                View rootView = buttonView.getRootView();
//                if(buttonView.isChecked()){
//                sum += Double.valueOf(foodPrice.getText().toString());
//                TextView priceSum = rootView.findViewById(R.id.sum);
//                priceSum.setText(String.format("%.2f",sum));
//                }else{
//                    sum -= Double.valueOf(foodPrice.getText().toString());
//                    TextView priceSum = rootView.findViewById(R.id.sum);
//                    priceSum.setText(String.format("%.2f",sum));
//                }
//
//            }
//
//        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }



}
