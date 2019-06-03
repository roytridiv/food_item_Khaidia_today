package com.example.food_item;

public class ItemtList {
    private String id , order_id , time;

    public ItemtList(String id, String order_id, String time) {
        this.id = id;
        this.order_id = order_id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
