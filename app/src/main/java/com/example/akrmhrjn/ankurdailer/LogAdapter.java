package com.example.akrmhrjn.ankurdailer;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LogAdapter extends BaseAdapter implements Filterable {

    ArrayList<LogClass> logList,list ;
    private LayoutInflater layoutInflater;
    Context mCon;


    private InfoFilter mFilter = new InfoFilter();

    public LogAdapter(Context context, ArrayList<LogClass> logsList) {
        this.logList = logsList;
        this.list = logsList;
        layoutInflater = LayoutInflater.from(context);
        this.mCon = context;
    }

    public LogAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return logList.size();
    }

    @Override
    public Object getItem(int position) {
        return logList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.row_item_log, null);
            holder = new ViewHolder();
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.duration = (TextView) convertView.findViewById(R.id.duration);
            holder.imgType = (ImageView) convertView.findViewById(R.id.imgType);
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.linearMerge);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            LogClass log = logList.get(position);
            try {
                char aChar = log.name.charAt(0);
                System.out.println("first" + aChar);
                holder.title.setTag(position);
                holder.title.setText(String.valueOf(aChar));
                holder.name.setTag(position);
                holder.name.setText(log.name);
            }catch (NullPointerException err){
                err.printStackTrace();
                holder.title.setTag(position);
                holder.title.setText("U");
                holder.name.setTag(position);
                holder.name.setText("Unknown");
            }
            holder.number.setTag(position);
            holder.number.setText(log.number);

            long now = System.currentTimeMillis();
            CharSequence relativeTimeStr = DateUtils.getRelativeTimeSpanString(Long.valueOf(log.date),
                    now, DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);
            holder.date.setText(relativeTimeStr.toString());


            holder.duration.setText(timeConversion(Integer.parseInt(log.duration)));

            if(log.type == 1){
                holder.imgType.setImageResource(R.drawable.in);
            }else if(log.type == 2) {
                holder.imgType.setImageResource(R.drawable.out);
            }else {
                holder.imgType.setImageResource(R.drawable.misssss);
            }

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

        TextView number,name, title,date,duration;
        RelativeLayout relativeLayout;
        ImageView imgType;

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
                ArrayList<LogClass> nContactList = new ArrayList<LogClass>();
                for (LogClass contact:list) {
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
        protected void publishResults(CharSequence constraint, FilterResults results) {
            logList = (ArrayList<LogClass>) results.values;
            notifyDataSetChanged();
        }

    }

    private static final int MINUTES_IN_AN_HOUR = 60;
    private static final int SECONDS_IN_A_MINUTE = 60;
    private static String timeConversion(int totalSeconds) {
        int hours = totalSeconds / MINUTES_IN_AN_HOUR / SECONDS_IN_A_MINUTE;
        int minutes = (totalSeconds - (hoursToSeconds(hours)))
                / SECONDS_IN_A_MINUTE;
        int seconds = totalSeconds
                - ((hoursToSeconds(hours)) + (minutesToSeconds(minutes)));

        if(hours==0 && minutes == 0 && seconds == 0)
            return seconds + " sec";
        else if(hours==0 && minutes == 0){
            return seconds + " secs" ;
        }
        else if(hours == 0){
            return minutes + ":" + seconds;
        }else{
            return hours + ":" + minutes + ":" + seconds;
        }
    }

    private static int hoursToSeconds(int hours) {
        return hours * MINUTES_IN_AN_HOUR * SECONDS_IN_A_MINUTE;
    }

    private static int minutesToSeconds(int minutes) {
        return minutes * SECONDS_IN_A_MINUTE;
    }
}
