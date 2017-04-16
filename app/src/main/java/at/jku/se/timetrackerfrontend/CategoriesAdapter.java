package at.jku.se.timetrackerfrontend;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import entities.Category;
import entities.Cooperation;
import entities.ProjectRole;
import services.CategoryService;
import services.CooperationService;

/**
 * Created by Anna on 15.04.2017.
 */

public class CategoriesAdapter extends ArrayAdapter<Category> {

    public CategoriesAdapter(Activity context, ArrayList<Category> categories) {
        super(context, 0,categories);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.content_listview_categories, parent, false);
        }

        Category currentItem = getItem(position);

        Button delete = (Button) listItemView.findViewById(R.id.btnDelete);
        TextView name = (TextView) listItemView.findViewById(R.id.categorieName);
        TextView estimatedTime = (TextView) listItemView.findViewById(R.id.tvEstimatedTime);

        name.setText(currentItem.getName());
        estimatedTime.setText(currentItem.getEstimatedTime() + " Hours");



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryService categoryService = new CategoryService();
                categoryService.remove(currentItem.getId());//remove from Data
                remove(currentItem);//remove from list
            }
        });

        return listItemView;
    }
}
