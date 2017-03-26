package at.jku.se.timetrackerfrontend;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Object> {

    public ItemAdapter(Activity context, ArrayList<Object> object){
        super(context, 0, object);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_manage_project_team, parent, false);
        }



//        TextView recipeTitle = (TextView) listItemView.findViewById(R.id.recipe_title);
//        recipeTitle.setText(currentRecipe.getTitle());
//
//        TextView recipeSubtitle = (TextView) listItemView.findViewById(R.id.recipe_subtitle);
//        String stars = String.format("%.1f", currentRecipe.getRating());
//        recipeSubtitle.setText(stars + " of 5 stars");

        return listItemView;
    }
}
