package com.example.food_item;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class test extends AppCompatActivity {
    TextView customerOrderId , customerOrderTime , customerName , customerAddress , customerContact , customerFoodBill,
            customerDelCrg , customertotalBill ;

    String order_id;
    String order_time;

    String item_details;
    String delivery_fee;
    String mres_fee;
    String total_bill;
    String type;
    String payment;
    String receiver;
    String toAddr;
    String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        customerOrderId = findViewById(R.id.orderId);
        customerOrderTime = findViewById(R.id.orderTime);
        customerName = findViewById(R.id.name);
        customerAddress = findViewById(R.id.address);
        customerContact = findViewById(R.id.contact);
        customerFoodBill = findViewById(R.id.food_bill);
        customerDelCrg = findViewById(R.id.delivery_crg);
        customertotalBill = findViewById(R.id.total);

        //tx.setText("Your Order id : \n"+getIntent().getStringExtra("order_id") + "\n\nOrder time : " + getIntent().getStringExtra("time"));

        order_id = getIntent().getStringExtra("order_id");
        order_time = getIntent().getStringExtra("time");
        Log.d("MY_APP_DEBUG", "order_id :  " + order_id);

        Call<ResponseBody> call = RetrofitClient2
                .getInstance()
                .getApi()
                .fetchData(order_id);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String serverResponse = null;
                    if (response.body() != null) {
                        serverResponse = response.body().string();
                    }
                    Log.d("MY_APP_DEBUG", "msg " + serverResponse);
                    try {
                        JSONObject obj = new JSONObject(serverResponse);
                        //JSONStringer code = new JSONStringer();

                        JSONArray jsonArray = obj.getJSONArray("result");

                        // JSONArray jsonArray = new JSONArray(serverResponse);
                        if (jsonArray.length() == 0) {
                            // recyclerView.setVisibility(View.GONE);
                            Toast.makeText(test.this, "Sorry no entries found on this date ", Toast.LENGTH_SHORT).show();
                        } else {
                            // recyclerView.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            item_details = jsonObject.getString("item_details");
                            delivery_fee = jsonObject.getString("delivery_fee");
                            mres_fee = jsonObject.getString("mres_fee");
                            total_bill = jsonObject.getString("total_bill");
                            type = jsonObject.getString("type");
                            payment = jsonObject.getString("payment");
                            receiver = jsonObject.getString("receiver");
                            toAddr = jsonObject.getString("toAddr");
                            contact = jsonObject.getString("contact");

                            //String time2 = time.substring(time.indexOf(' '));
                            // itemtLists.add(new ItemtList(id, order_id, time2));
                        }
                        // recyclerAdapter = new RecyclerAdapter(MainActivity.this, itemtLists);
                        //  recyclerView.setAdapter(recyclerAdapter);
                        Log.d("MY_APP_DEBUG" , ""+ item_details + " , " +delivery_fee+" , "+mres_fee+" , "+toAddr+" , " +contact );
                        customerOrderId.setText("Order ID : "+order_id);
                        customerOrderTime.setText("Order Time : "+order_time);
                        customerName.setText("Name : " + receiver);
                        customerAddress.setText("Address : "+toAddr);
                        customerContact.setText("Contact : "+contact);

                        customerFoodBill.setText("Food Bill : "+mres_fee);
                        customerDelCrg.setText("Delivery Bill: "+delivery_fee);
                        customertotalBill.setText("Total Bill: "+total_bill);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

//        customerOrderId.setText(toAddr);
//        customerOrderTime.setText(order_time);
//        customerName.setText("name");
//        customerAddress.setText(toAddr);
//        customerContact.setText(contact);
//
//        customerFoodBill.setText(mres_fee);
//        customerDelCrg.setText(delivery_fee);
//        customertotalBill.setText(total_bill);






    }
}
