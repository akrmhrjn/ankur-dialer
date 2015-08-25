package com.example.akrmhrjn.ankurdailer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapter extends BaseAdapter implements Filterable {

    ArrayList<ContactClass> contacts,list ;
    private LayoutInflater layoutInflater;
    Context mCon;


    private InfoFilter mFilter = new InfoFilter();

    public Adapter(Context context, ArrayList<ContactClass> contacts) {
        this.contacts = contacts;
        this.list = contacts;
        layoutInflater = LayoutInflater.from(context);
        this.mCon = context;
    }

    public Adapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        convertView = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_item_white, null);
            holder = new ViewHolder();
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linearMerge);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            ContactClass contact = contacts.get(position);
            char aChar = contact.name.charAt(0);
            System.out.println("first" + aChar);
            holder.title.setTag(position);
            holder.title.setText(String.valueOf(aChar));
            holder.name.setTag(position);
            holder.name.setText(contact.name);
            holder.number.setTag(position);
            holder.number.setText(contact.number);
            final String num = contact.number;

            /*holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse("tel:" + Uri.encode(num)));
                    mCon.startActivity(call);
                }
            });*/

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


        }catch (IndexOutOfBoundsException err){}



        return convertView;
    }

    static class ViewHolder {

        TextView number,name, title;
        LinearLayout linearLayout;

    }

    public Filter getFilter() {
        return mFilter;
    }

    private class InfoFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = list;
                results.count = list.size();
            } else {
                ArrayList<ContactClass> nContactList = new ArrayList<ContactClass>();
                for (ContactClass contact:list) {
                        if (contact.number.toUpperCase().startsWith(constraint.toString().toUpperCase()))
                            nContactList.add(contact);
                }

                results.values = nContactList;
                results.count = nContactList.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            contacts = (ArrayList<ContactClass>) results.values;
            notifyDataSetChanged();
        }

    }
}
