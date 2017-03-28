package at.jku.se.timetrackerfrontend;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import enteties.TimeEntry;

public class EntryAdapter extends ArrayAdapter<TimeEntry> {

    public EntryAdapter(Activity context, ArrayList<TimeEntry> timeEntries){
        super(context, 0, timeEntries);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.content_listview_entries, parent, false);
        }

        TimeEntry currentItem = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.entry_name);
        name.setText(currentItem.getName());

        TextView date = (TextView) listItemView.findViewById(R.id.dateListEntry);
        date.setText(currentItem.getUntil().toString());

        return listItemView;
    }
}
