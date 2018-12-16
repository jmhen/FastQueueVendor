package com.jem.fq.base;

public class MyStore {

    private String store_id;
    private String store_name;
    private String store_description;
    private String store_username;
    MyStore(){
        store_id = "";
        store_name = "";
        store_description = "";
        store_username = "";
    }
    public void setStore_description(String store_description) {
        this.store_description = store_description;
    }

    public String getStore_description() {
        return store_description;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_username(String store_username) {
        this.store_username = store_username;
    }

    public String getStore_username() {
        return store_username;
    }
}
