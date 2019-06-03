package com.example.food_item;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.myViewHolder> {
    @NonNull
    private Context mContext;
    private ArrayList<ItemtList> mItemtLists;


    RecyclerAdapter(Context context, ArrayList<ItemtList> adapterItemtLists) {
        this.mContext = context;
        this.mItemtLists = adapterItemtLists;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        return new myViewHolder(view , mContext , mItemtLists);
    }


    @Override
    public void onBindViewHolder(@NonNull myViewHolder viewHolder, int i) {

        final ItemtList nItemtList = mItemtLists.get(i);

        TextView id, order_id , time;

        //id = viewHolder.id;
        order_id = viewHolder.id;
        time = viewHolder.time;

        //id.setText(nItemtList.getId());
        order_id.setText(nItemtList.getOrder_id());
        time.setText(nItemtList.getTime());

    }

    @Override
    public int getItemCount() {
        return mItemtLists.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView id, order_id , time;

        ArrayList<ItemtList> mycontactLists = new ArrayList<>();
        Context myContext;


        myViewHolder(@NonNull View itemView , Context context , ArrayList<ItemtList> itemtLists) {
            super(itemView);
            //
            this.mycontactLists = itemtLists;
            this.myContext=context;
            itemView.setOnClickListener(this);
            //
            id = itemView.findViewById(R.id.id);
            order_id = itemView.findViewById(R.id.order_id);
            time = itemView.findViewById(R.id.time);

        }


        @Override
        public void onClick(View v) {

            int pos = getAdapterPosition();
            ItemtList onClickItemtList =this.mycontactLists.get(pos);
            Intent intent = new Intent(this.myContext,test.class);

            intent.putExtra("order_id" , onClickItemtList.getOrder_id());
            intent.putExtra("time" , onClickItemtList.getTime());
            this.myContext.startActivity(intent);


        }
    }

}
