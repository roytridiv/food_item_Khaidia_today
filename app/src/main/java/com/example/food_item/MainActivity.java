package com.example.food_item;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button date_pick;


    private String my_date;


    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;

    private RecyclerAdapter recyclerAdapter;

    private ArrayList<ItemtList> itemtLists = new ArrayList<>();

    private int clickedDay, clickedMonth, clickedYear, currentday, currentMonth, currentYear;


    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        date_pick = findViewById(R.id.date);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Calendar current_date = Calendar.getInstance();
        currentday = current_date.getInstance().get(Calendar.DAY_OF_MONTH);
        currentMonth = current_date.getInstance().get(Calendar.MONTH) + 1;
        currentYear = current_date.getInstance().get(Calendar.YEAR);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("MY_APP_DEBUG", "current date : " + formatter.format(current_date.getTime()));

        date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });


        Log.d("MY_APP_DEBUG", "flag " + flag);


    }


    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        month = month + 1;
        this.clickedDay = dayOfMonth;
        this.clickedMonth = month;
        this.clickedYear = year;


        if (month < 10 && dayOfMonth > 10) {
            my_date = "" + year + "-0" + month + "-" + dayOfMonth;
        } else if (dayOfMonth < 10 && month > 10) {
            my_date = "" + year + "-" + month + "-0" + dayOfMonth;
        } else if (month < 10 && dayOfMonth < 10) {
            my_date = "" + year + "-0" + month + "-0" + dayOfMonth;
        } else {
            my_date = "" + year + "-" + month + "-" + dayOfMonth;
        }


        Log.d("MY_APP_DEBUG", "date : " + my_date);

        check();

        Log.d("MY_APP_DEBUG", "flag " + flag);
        Log.d("MY_APP_DEBUG", "current year ,  clicked year : " +
                currentYear + "," + clickedYear + " current month , clicked month : " + currentMonth + "," +
                clickedMonth + " current day , clicked day : " + currentday + " , " + clickedDay);

        if (flag == true) {
            recyclerView.setVisibility(View.VISIBLE);
            callRetrofit();
        } else {
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Sorry you cannot give future dates", Toast.LENGTH_SHORT).show();
        }
    }


    public void callRetrofit() {
        Log.d("MY_APP_DEBUG", "button pressed");
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .fetchData(my_date);


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

                        JSONArray jsonArray = obj.getJSONArray("MESSAGE");

                        // JSONArray jsonArray = new JSONArray(serverResponse);
                        if (jsonArray.length() == 0) {
                            recyclerView.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Sorry no entries found on this date ", Toast.LENGTH_SHORT).show();
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("ID");
                            String order_id = jsonObject.getString("ORDER_ID");
                            String time = jsonObject.getString("TIMESTAMP");

                            String time2 = time.substring(time.indexOf(' '));
                            itemtLists.add(new ItemtList(id, order_id, time2));
                        }
                        recyclerAdapter = new RecyclerAdapter(MainActivity.this, itemtLists);
                        recyclerView.setAdapter(recyclerAdapter);


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

    }

    public void check() {
        if (clickedYear < currentYear) {
            flag = true;
        } else if (clickedYear == currentYear) {
            if (clickedMonth < currentMonth) {
                flag = true;
            } else if (clickedMonth == currentMonth) {
                if (clickedDay <= currentday) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        } else {
            flag = false;
        }


    }
}
