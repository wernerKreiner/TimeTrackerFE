package at.jku.se.timetrackerfrontend;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import entities.User;

/**
 * Created by Anna on 28.03.2017.
 */

public class UserAdapter extends ArrayAdapter<User>{

    public UserAdapter(Activity context, ArrayList<User> user){
        super(context, 0, user);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.content_listview_members, parent, false);
        }

        User currentItem = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.userName);
        name.setText(currentItem.getNickname());

        Spinner spinner = (Spinner) listItemView.findViewById(R.id.spinnerRole);
        ArrayAdapter<CharSequence> stringAdapter = ArrayAdapter.createFromResource(getContext(), R.array.rolesArray, android.R.layout.simple_spinner_item);
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringAdapter);

        return listItemView;
    }
}
