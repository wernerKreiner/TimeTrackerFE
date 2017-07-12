package at.jku.se.timetrackerfrontend.report;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import at.jku.se.timetrackerfrontend.user.LoginActivity;
import at.jku.se.timetrackerfrontend.R;
import services.CooperationService;

/**
 * Created by domin on 26.03.2017.
 */

public class ChangeUserReportDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        CooperationService cooperationService = new CooperationService();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change Project");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v_iew = inflater.inflate(R.layout.dialog_change_user_report, null);
        builder.setView(v_iew);

        List<String> projects = new ArrayList<>();

        // Add entry of all projects.
        projects.add("All Projects");

        cooperationService.get()
                .stream()
                .filter(c -> c.getPerson().getId() == LoginActivity.user.getId())
                .map(c -> c.getProject().getName())
                .forEach(projects::add);

        Spinner spinner = (Spinner) v_iew.findViewById(R.id.spinner_project_dialog_changeUserReport);
        ArrayAdapter<String> stringAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, projects.toArray());
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringAdapter);

                // Add action buttons
        builder.setPositiveButton("Choose", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String projectName = spinner.getSelectedItem().toString();
                UserReportActivity callingActivity = (UserReportActivity) getActivity();
                callingActivity.changeChart(projectName);
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
