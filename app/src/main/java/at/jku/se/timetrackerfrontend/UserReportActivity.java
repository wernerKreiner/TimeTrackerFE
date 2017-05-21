package at.jku.se.timetrackerfrontend;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import entities.Category;
import entities.Cooperation;
import entities.Person;
import entities.TimeEntry;
import services.CooperationService;

public class UserReportActivity extends AppCompatActivity {

    private static PieChart userReportChart;
    private CooperationService cooperationService;
    protected Typeface mTfRegular;
    protected Typeface mTfLight;

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

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        this.configurateChart();
        this.changeChart("All Projects");
    }

    public void changeChart(String projectName) {
        if(projectName.compareTo("All Projects") == 0) {
            this.setDataAllProjects();
        }
        else {
            this.setDataOfProject(projectName);
        }
    }

    private void configurateChart() {
        userReportChart.setUsePercentValues(true);
        userReportChart.getDescription().setEnabled(false);
        userReportChart.setExtraOffsets(5, 10, 5, 5);

        userReportChart.setDragDecelerationFrictionCoef(0.95f);

        userReportChart.setCenterTextTypeface(mTfLight);
        userReportChart.setCenterText(generateCenterSpannableText());

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

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("TimeTracker\ndeveloped by SE GROUP 1");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 12, s.length() - 11, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 11, s.length() - 11, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 11, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 11, s.length(), 0);
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
            for(Category category : cooperation.getProject().getCategories()) {
                category.getTimeEntries()
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
            entries.add(new PieEntry(v.floatValue(), k));
        });

        PieDataSet dataSet = new PieDataSet(entries, "Projects");

        this.initialiseNewDataSet(dataSet);
    }

    private void setDataOfProject(String projectName) {
        Person actUser = LoginActivity.user;

        // Get all cooperations from person of Project.
        Optional<Cooperation> cooperationOpt = this.cooperationService.get()
                .stream()
                .filter(c -> c.getPerson().getId() == actUser.getId() && c.getProject().getName() == projectName)
                .findFirst();

        Map<String, List<TimeEntry>> nameTimeEntry = new HashMap<>();

        if(cooperationOpt.isPresent()) {
            Cooperation cooperation = cooperationOpt.get();

            for(Category category : cooperation.getProject().getCategories()) {
                List<TimeEntry> entries = new ArrayList<>();
                category.getTimeEntries()
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
            entries.add(new PieEntry(v.floatValue(), k));
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
