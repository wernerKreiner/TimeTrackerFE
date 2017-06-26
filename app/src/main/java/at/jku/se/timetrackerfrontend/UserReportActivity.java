package at.jku.se.timetrackerfrontend;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import entities.Category;
import entities.Cooperation;
import entities.Person;
import entities.Project;
import entities.TimeEntry;
import services.CategoryService;
import services.CooperationService;
import services.ProjectService;
import services.TimeEntryService;

public class UserReportActivity extends AppCompatActivity {

    private static PieChart userReportChart;
    private CooperationService cooperationService;
    protected Typeface mTfRegular;
    protected Typeface mTfLight;

    static final int REQUEST_EXTERNAL_STORAGE = 1;
    static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_report);

        userReportChart = (PieChart) findViewById(R.id.chart_user_report);

        this.cooperationService = new CooperationService();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnFloting_settings_user_report);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                android.app.DialogFragment dialogFragment = new ChangeUserReportDialogFragment();
                dialogFragment.show(fm, "");
            }
        });

        FloatingActionButton floatingActionButtonExport = (FloatingActionButton) findViewById(R.id.btnFloting_export_user_report);
        floatingActionButtonExport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                exportUserReport();
            }
        });

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        this.configurateChart();

        this.changeChart("All Projects");
    }

    public void changeChart(String projectName) {
        if(projectName.compareTo("All Projects") == 0) {
            userReportChart.setCenterText(generateCenterSpannableText("All Projects"));
            this.setDataAllProjects();
        }
        else {
            userReportChart.setCenterText(generateCenterSpannableText(projectName));
            this.setDataOfProject(projectName);
        }
    }

    private void configurateChart() {
        userReportChart.setUsePercentValues(true);
        userReportChart.getDescription().setEnabled(false);
        userReportChart.setExtraOffsets(5, 10, 5, 5);

        userReportChart.setDragDecelerationFrictionCoef(0.95f);

        userReportChart.setCenterTextTypeface(mTfLight);

        userReportChart.setDrawHoleEnabled(true);
        userReportChart.setHoleColor(Color.WHITE);

        userReportChart.setTransparentCircleColor(Color.WHITE);
        userReportChart.setTransparentCircleAlpha(110);

        userReportChart.setHoleRadius(58f);
        userReportChart.setTransparentCircleRadius(61f);

        userReportChart.setDrawCenterText(true);

        userReportChart.setRotationAngle(0);
        userReportChart.setRotationEnabled(true);
        userReportChart.setHighlightPerTapEnabled(true);

        //mChart.setUnit(" h");
        //mChart.setDrawUnitsInChart(true);

        userReportChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = userReportChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        userReportChart.setEntryLabelColor(Color.BLACK);
        userReportChart.setEntryLabelTypeface(mTfRegular);
        userReportChart.setEntryLabelTextSize(12f);
    }

    private SpannableString generateCenterSpannableText(String projectName) {
        SpannableString s = new SpannableString(projectName);
        s.setSpan(new RelativeSizeSpan(1.7f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);

        return s;
    }

    private void setDataAllProjects() {
        Person actUser = LoginActivity.user;

        // Get all cooperations from person.
        List<Cooperation> cooperations = new ArrayList<>();

        this.cooperationService.get()
                .stream()
                .filter(c -> c.getPerson().getId() == actUser.getId())
                .forEach(cooperations::add);

        Map<String, List<TimeEntry>> nameTimeEntry = new HashMap<>();

        for(Cooperation cooperation : cooperations) {
            List<TimeEntry> entries = new ArrayList<>();

            CategoryService categoryService = new CategoryService();
            TimeEntryService timeEntryService = new TimeEntryService();

            for(Category category : categoryService.getByProject(cooperation.getProject())){
                timeEntryService.getByCategory(category)
                        .stream()
                        .filter(te -> te.getPerson().getId() == actUser.getId())
                        .forEach(entries::add);
            }

            nameTimeEntry.put(cooperation.getProject().getName(), entries);
        }

        Map<String, Double> projectNameTime = new HashMap<>();

        nameTimeEntry.forEach((k,v) -> {
            double hour = v
                    .stream()
                    .mapToDouble(te -> {
                        long diffInMillies = te.getTo().getTime() - te.getFrom().getTime();

                        return diffInMillies / 3600000;
                    })
                    .sum();

            projectNameTime.put(k, hour);
        });


        ArrayList<PieEntry> entries = new ArrayList<>();

        projectNameTime.forEach((k, v) -> {
            if( v.floatValue() > 0) {
                entries.add(new PieEntry(v.floatValue(), k));
            }
        });

        PieDataSet dataSet = new PieDataSet(entries, "Projects");

        this.initialiseNewDataSet(dataSet);
    }

    private void setDataOfProject(String projectName) {
        Person actUser = LoginActivity.user;

        // Get all cooperations from person of Project.
        Optional<Cooperation> cooperationOpt = this.cooperationService.get()
                .stream()
                .filter(c -> c.getPerson().getId() == actUser.getId() && c.getProject().getName().equals(projectName))
                .findFirst();

        Map<String, List<TimeEntry>> nameTimeEntry = new HashMap<>();

        if(cooperationOpt.isPresent()) {
            Cooperation cooperation = cooperationOpt.get();

            CategoryService categoryService = new CategoryService();
            TimeEntryService timeEntryService = new TimeEntryService();
            for(Category category : categoryService.getByProject(cooperation.getProject())) {
                List<TimeEntry> entries = new ArrayList<>();
                timeEntryService.getByCategory(category)
                        .stream()
                        .filter(te -> te.getPerson().getId() == actUser.getId())
                        .forEach(entries::add);

                nameTimeEntry.put(category.getName(), entries);
            }
        }

        Map<String, Double> categoryNameTime = new HashMap<>();

        nameTimeEntry.forEach((k,v) -> {
            double hour = v
                    .stream()
                    .mapToDouble(te -> {
                        long diffInMillies = te.getTo().getTime() - te.getFrom().getTime();

                        return diffInMillies / 3600000;
                    })
                    .sum();

            categoryNameTime.put(k, hour);
        });

        ArrayList<PieEntry> entries = new ArrayList<>();

        categoryNameTime.forEach((k, v) -> {
            if( v.floatValue() > 0) {
                entries.add(new PieEntry(v.floatValue(), k));
            }
        });

        PieDataSet dataSet = new PieDataSet(entries, "Categories");

        this.initialiseNewDataSet(dataSet);
    }

    private void initialiseNewDataSet(PieDataSet dataSet) {
        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // Add a lot of colors.
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(mTfLight);
        userReportChart.setData(data);

        userReportChart.highlightValues(null);

        userReportChart.invalidate();
    }

    private void exportUserReport(){
        verifyStoragePermissions(this);

        TimeEntryService timeEntryService = new TimeEntryService();
        List<TimeEntry> timeEntryList = timeEntryService.getByPerson(LoginActivity.user);

        StringBuilder sb = new StringBuilder();
        sb.append("id;user;project;category;from;to;note\n");
        for(TimeEntry t : timeEntryList){
            sb.append(t.getId() + ";" + t.getPerson().getEmail() + ";" + t.getCategory().getProject().getName() + ";"
                    + t.getCategory().getName() + ";" + t.getFrom().toString() + ";" + t.getTo().toString() + ";" + t.getNote()+ ";\n");
        }
        String string = sb.toString();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        String filename = "userReport_"+LoginActivity.user.getNickname()+"_"+year+month+day+hour+min+".txt";

        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);

        File file = new File(path, filename);

        try{
            path.mkdirs();
            FileOutputStream os = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            osw.append(string);
            osw.close();
            os.close();

            MediaScannerConnection.scanFile(this,
                    new String[] { file.toString() }, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        Toast toast = new Toast(this);
        toast.makeText(this, "UserReport successfully downloaded!", Toast.LENGTH_LONG).show();
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.autoEntry) {
            startActivity(new Intent(this, AutoEntryActivity.class));
        } else if (id == R.id.manEntry) {
            startActivity(new Intent(this, ManualEntryActivity.class));
        } else if (id == R.id.detailEntry) {
            startActivity(new Intent(this,EditEntryActivity.class));
        } else if (id == R.id.newProj) {
            startActivity(new Intent(this,CreateProjectActivity.class));
        } else if (id == R.id.manageProj) {
            startActivity(new Intent(this, ManageProjectActivity.class));
        } else if (id == R.id.projReport) {
            startActivity(new Intent(this, ProjectReportActivity.class));
        } else if (id == R.id.userReport) {
            startActivity(new Intent(this, UserReportActivity.class));
        } else if (id == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.logout) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        return true;
    }
}
