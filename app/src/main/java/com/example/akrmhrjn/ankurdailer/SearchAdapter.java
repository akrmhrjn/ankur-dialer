package com.example.akrmhrjn.ankurdailer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by akrmhrjn on 7/14/15.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ContactClass> contactList;
    private LayoutInflater inflater;

    public SearchAdapter(Context context, ArrayList<ContactClass> list){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.contactList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_item_white, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ContactClass contact = contactList.get(position);
        holder.title.setText(String.valueOf(contact.name.charAt(0)));
        holder.name.setText(contact.name);
        holder.number.setText(contact.number);

        Random random = new Random();
        int number = random.nextInt(5 - 0 + 1) + 0;

        if(number==0){
            holder.title.setBackgroundColor(Color.parseColor("#ff5722"));
        }
        else if(number==1){
            holder.title.setBackgroundColor(Color.parseColor("#e91e63"));
        }else if(number==2){
            holder.title.setBackgroundColor(Color.parseColor("#2196F3"));
        }else if(number==3){
            holder.title.setBackgroundColor(Color.parseColor("#4CAF50"));
        }else if(number==4){
            holder.title.setBackgroundColor(Color.parseColor("#795548"));
        }else if(number==5){
            holder.title.setBackgroundColor(Color.parseColor("#33691E"));
        }else if(number==6){
            holder.title.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }else if(number==7){
            holder.title.setBackgroundColor(Color.parseColor("#D84315"));
        }else if(number==8){
            holder.title.setBackgroundColor(Color.parseColor("#607D8B"));
        }else{
            holder.title.setBackgroundColor(Color.parseColor("#9c27b0"));
        }

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title,name,number;
        Context mCon;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mCon = itemView.getContext();
            title = (TextView) itemView.findViewById(R.id.title);
            name = (TextView) itemView.findViewById(R.id.name);
            number = (TextView) itemView.findViewById(R.id.number);
        }

        @Override
        public void onClick(View v) {
            final ContactClass contactClass = (ContactClass) contactList.get(getPosition());


            final Dialog d = new Dialog(mCon, R.style.FullHeightDialog);
            d.setContentView(R.layout.custom_dialog);
            //d.setTitle("Choose your option:");
            //d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.show();


            ImageButton callBtn =(ImageButton)d.findViewById(R.id.btnCall);

            ImageButton msg =(ImageButton)d.findViewById(R.id.btnMsg);

            TextView name = (TextView)d.findViewById(R.id.name);

            name.setText(contactClass.name);

            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse("tel:" + Uri.encode(contactClass.number)));
                    mCon.startActivity(call);
                }
            });

            msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setType("vnd.android-dir/mms-sms");
                    intent.putExtra("address", contactClass.number);
                    context.startActivity(intent);
                }
            });


        }
    }


}
