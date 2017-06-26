package at.jku.se.timetrackerfrontend;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import entities.*;
import services.CooperationService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Anna on 28.03.2017.
 */

public class UserAdapter extends ArrayAdapter<Cooperation> {

    public UserAdapter(Activity context, ArrayList<Cooperation> coop) {
        super(context, 0,coop);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        CooperationService coop = new CooperationService();

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.content_listview_members, parent, false);
        }

        Cooperation currentItem = getItem(position);

        Button delete = (Button) listItemView.findViewById(R.id.buttonDel);
        TextView name = (TextView) listItemView.findViewById(R.id.userName);
        name.setText(currentItem.getPerson().getNickname());

        Spinner spinner = (Spinner) listItemView.findViewById(R.id.spinnerRole);
        ArrayAdapter<ProjectRole> stringAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, ProjectRole.values());
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringAdapter);

        if(currentItem.getProjectRole()!=null){
            int spinnerPos = stringAdapter.getPosition(currentItem.getProjectRole());
            spinner.setSelection(spinnerPos);
        }

        class myOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String obj = parent.getSelectedItem().toString();
                ProjectRole role = ProjectRole.valueOf(obj);
                currentItem.setProjectRole(role);
                coop.edit(currentItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }

        spinner.setOnItemSelectedListener(new myOnItemSelectedListener());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coop.remove(currentItem.getId());
                remove(currentItem);
            }
        });

        return listItemView;
    }
}
