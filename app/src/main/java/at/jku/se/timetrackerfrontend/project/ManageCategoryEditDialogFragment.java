package at.jku.se.timetrackerfrontend.project;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import at.jku.se.timetrackerfrontend.R;
import entities.Category;
import entities.Project;
import services.CategoryService;
import services.ProjectService;

/**
 * Created by domin on 26.03.2017.
 */

public class ManageCategoryEditDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.addCategory);
        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v_iew = inflater.inflate(R.layout.dialog_manage_category_edit, null);
        builder.setView(v_iew);

        ProjectService projectService = new ProjectService();
        CategoryService categoryService = new CategoryService();

        String projectName = getArguments().getString("project");
        Project project = projectService.get().stream().filter(x->x.getName().equals(projectName)).findAny().get();

        EditText catName = (EditText) v_iew.findViewById(R.id.etCategoryName);
        EditText estTime = (EditText) v_iew.findViewById(R.id.etEstimatedTime);

                // Add action buttons
                builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        categoryService.create(new Category(catName.getText().toString(), Double.parseDouble(estTime.getText().toString()), project ));
                        Intent intent = getActivity().getIntent();
                        getActivity().finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ManageCategoryEditDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
