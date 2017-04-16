package at.jku.se.timetrackerfrontend;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import entities.Cooperation;
import entities.Person;
import entities.Project;
import entities.ProjectRole;
import services.CooperationService;
import services.PersonService;
import services.ProjectService;

/**
 * Created by domin on 26.03.2017.
 */

public class ManageProjectTeamEditDialogFragment extends DialogFragment {

    String projectRole;
    String projectName;
    Project project;
    View view;
    PersonService personService;
    CooperationService cooperationService;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.addTeamMember);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v_iew = inflater.inflate(R.layout.dialog_manage_project_team_edit, null);
        builder.setView(v_iew);

        cooperationService = new CooperationService();
        personService = new PersonService();
        ProjectService projectService = new ProjectService();

        projectName = getArguments().getString("project");
        project = projectService.get().stream().filter(x->x.getName().equals(projectName)).findAny().get();
        List<String> roles = Arrays.asList(ProjectRole.values()).stream().map(ProjectRole::name).collect(Collectors.toList());
        String [] rolesArray = roles.toArray(new String[roles.size()]);

        Spinner spinner = (Spinner) v_iew.findViewById(R.id.spRole);
        ArrayAdapter<String> stringAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, rolesArray);
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringAdapter);

        class myOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectRole = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }

        spinner.setOnItemSelectedListener(new myOnItemSelectedListener());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

                // Add action buttons
                builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Dialog d = (Dialog) dialog;
                        EditText email = (EditText) d.findViewById(R.id.editEmail);
                        String emailText = email.getText().toString();
                        Optional<Person> person = personService.get().stream().filter(x->x.getEmail().equals(emailText)).findFirst();
                        if(person.isPresent()){
                            cooperationService.create(new Cooperation(ProjectRole.valueOf(projectRole), person.get(), project));
                        }else{
                            Toast toast = Toast.makeText(getContext(),"User does not exist!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        Intent intent = getActivity().getIntent();
                        getActivity().finish();
                        startActivity(intent);

                    }
                })
                .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ManageProjectTeamEditDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
