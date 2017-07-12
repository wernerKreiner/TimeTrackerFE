package at.jku.se.timetrackerfrontend.timeEntry;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import at.jku.se.timetrackerfrontend.R;
import entities.TimeEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EntryAdapter extends ArrayAdapter<TimeEntry> {
    Context context;

    public EntryAdapter(Activity context, ArrayList<TimeEntry> timeEntries){
        super(context, 0, timeEntries);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_listview_entries, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        TimeEntry currentItem = getItem(position);

        viewHolder.item_name.setText(currentItem.getNote());

        DateFormat dateFormater = new SimpleDateFormat("dd.MM.yyyy");
        String dateFormatted =  dateFormater.format(currentItem.getFrom());
        viewHolder.item_date.setText(dateFormatted);

        long diff = (currentItem.getTo().getTime()-currentItem.getFrom().getTime())/1000;
        long hours = diff/3600;
        long min = (diff%3600)/60;

        //viewHolder.item_duration.setText(String.format(hours + ":" + min));
        viewHolder.item_duration.setText(String.format("%02d:%02d",hours,min)); //edit werner

        if(currentItem.getCategory() == null) {
            viewHolder.item_nameProj.setText("No Project");
        }else {
            viewHolder.item_nameProj.setText(currentItem.getCategory().getProject().getName());
        }



        DateFormat timeFormat = new SimpleDateFormat("H:mm");
        String timeFormatedFrom = timeFormat.format(currentItem.getFrom());
        String timeFormatedTo = timeFormat.format(currentItem.getTo());

        viewHolder.item_from.setText(timeFormatedFrom + " -");
        viewHolder.item_to.setText(timeFormatedTo);



        return convertView;
    }

    private class ViewHolder {
        public TextView item_name;
        public TextView item_duration;
        public TextView item_from;
        public TextView item_to;
        public TextView item_date;
        public TextView item_nameProj;

        public ViewHolder (View convertView) {
            item_date = (TextView) convertView.findViewById(R.id.dateListEntry);
            item_name = (TextView) convertView.findViewById(R.id.entry_name);
            item_duration = (TextView) convertView.findViewById(R.id.durationTextView);
            item_from = (TextView) convertView.findViewById(R.id.from);
            item_to = (TextView) convertView.findViewById(R.id.to);
            item_nameProj = (TextView) convertView.findViewById(R.id.projNameList);
        }
    }
}
