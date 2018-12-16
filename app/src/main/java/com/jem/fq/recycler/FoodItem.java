package com.jem.fq.recycler;



public class FoodItem {
    private String foodName;
    private String foodImageByte;
    private String foodPrice;

    public FoodItem(String name, String foodPrice, String pic){
        this.foodPrice = foodPrice;
        this.foodName = name;
        this.foodImageByte = getFoodImageByte();
    }

    public String getFoodImageByte(){
        return foodImageByte;
    }

    public void setFoodImageByte(String foodImageByte) {
        this.foodImageByte = foodImageByte;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
