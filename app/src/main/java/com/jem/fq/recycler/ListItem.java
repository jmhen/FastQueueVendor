package com.jem.fq.recycler;

public class ListItem {

    private String foodname;
    private String customer_id;
    private String orderid;
    private String takeaway;
    private String preorder;

    public ListItem(String foodname, String customer_id,
                    String orderid, String takeaway,
                    String preorder) {

        this.foodname = foodname;
        this.customer_id = customer_id;
        this.orderid = orderid;
        this.takeaway = takeaway;
        this.preorder = preorder;
    }

    public String getFoodname(){
        return foodname;
    }
    public String getCustomer_id(){
        return customer_id;
    }

    public String getOrderid() {
        return orderid;
    }
    public String getTakeaway(){
        return takeaway;
    }

    public String getPreorder() {
        return preorder;
    }
}
