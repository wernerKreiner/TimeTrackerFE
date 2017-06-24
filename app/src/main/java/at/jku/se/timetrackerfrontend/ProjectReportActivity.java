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
import android.widget.ListView;
import android.view.ViewGroup.LayoutParams;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
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
import entities.Project;
import entities.TimeEntry;
import services.CategoryService;
import services.CooperationService;
import services.PersonService;
import services.ProjectService;
import services.TimeEntryService;

public class ProjectReportActivity extends AppCompatActivity {
    private PersonService personService;
    private CooperationService cooperationService;
    private ProjectService projectService;

    private ListView listview;
    private static PieChart projectReportChart;
    private EntryAdapter entryAdapter;
    private TextRoundCornerProgressBar progressbarProject;

    protected Typeface mTfRegular;
    protected Typeface mTfLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_report);

        projectReportChart = (PieChart) findViewById(R.id.chart_project_report);
        this.listview = (ListView) findViewById(R.id.lv_project_report);

        this.progressbarProject = (TextRoundCornerProgressBar) findViewById(R.id.pbProject);

        this.personService = new PersonService();
        this.cooperationService = new CooperationService();
        this.projectService = new ProjectService();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnFloting_settings_project_report);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                android.app.DialogFragment dialogFragment = new ChangeProjectReportDialogFragment();
                dialogFragment.show(fm, "");
            }
        });

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        this.configurateChart();

        // Get projectname of first project of user to show.
        Person actUser = LoginActivity.user;

        Optional<String> projectNameOpt = this.personService.get()
                .stream()
                .filter(p -> p.getId() == actUser.getId())
                .map(person -> {
                    if(cooperationService.getByPerson(person).stream().findFirst().isPresent()) {
                        return cooperationService.getByPerson(person).stream().findFirst().get().getProject().getName();
                    }

                    return "";
                })
                .findFirst();

        if(projectNameOpt.isPresent()) {
            this.changeChart(projectNameOpt.get());
        }
    }

    public void changeChart(String projectName) {
        if(projectName.compareTo("All Projects") == 0) {
            projectReportChart.setCenterText(generateCenterSpannableText(projectName));
        }
        else {
            projectReportChart.setCenterText(generateCenterSpannableText(projectName));
            this.setDataOfProject(projectName);
            this.setDataOfListView(projectName);
        }
    }

    private void setDataOfListView(String projectName) {

        // Load data.
        Optional<Project> projectOpt = this.projectService.get()
                .stream()
                .filter(p -> p.getName().equals(projectName)) //edit werner
                .findFirst();

        if(projectOpt.isPresent()) {
            Project project = projectOpt.get();

            CategoryService categoryService = new CategoryService();
            TimeEntryService timeEntryService = new TimeEntryService();
            ArrayList<TimeEntry> timeEntries = new ArrayList<>();
            categoryService.getByProject(project)
                    .stream()
                    .forEach(c -> {
                        timeEntryService.getByCategory(c)
                                .stream()
                                .forEach(timeEntries::add);
                    });

            // Fill EntryAdapter.
            this.entryAdapter = new EntryAdapter(this, timeEntries);

            // Set height for Listview.
            this.listview.setAdapter(entryAdapter);

            LayoutParams listviewParams = (LayoutParams) this.listview.getLayoutParams();
            int newHeight = this.calculateListViewHeight(this.listview);
            listviewParams.height = newHeight;
            this.listview.setLayoutParams(listviewParams);
        }
    }

    private int calculateListViewHeight(ListView list) {
        int height = 0;

        for (int i = 0; i < list.getCount(); i++) {
            View childView = list.getAdapter().getView(i, null, list);
            childView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            height+= childView.getMeasuredHeight();
        }

        //dividers height
        height += list.getDividerHeight() * list.getCount();

        return height;
    }

    private void setDataOfProject(String projectName) {
        Person actUser = LoginActivity.user;

        // Get all cooperations from person of Project.
        Optional<Cooperation> cooperationOpt = this.cooperationService.get()
                .stream()
                .filter(c -> c.getPerson().getId() == actUser.getId() && c.getProject().getName().equals(projectName))
                .findFirst();

        Map<String, List<TimeEntry>> nameTimeEntry = new HashMap<>();

        Cooperation cooperation = cooperationOpt.get();
        CategoryService categoryService = new CategoryService();
        List<Category> categories = categoryService.getByProject(cooperation.getProject());

        if(cooperationOpt.isPresent()) {
            TimeEntryService timeEntryService = new TimeEntryService();

            for(Category category : categories) {
                List<TimeEntry> entries = new ArrayList<>();
                timeEntryService.getByCategory(category)
                        .stream()
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
        this.initialisePbProject(entries, categories);
    }

    private void initialisePbProject(ArrayList<PieEntry> entries, List<Category> categories) {
        // sum all entries
        double sumOfTimeEntries = entries.stream().mapToDouble(e -> e.getValue()).sum();
        double sumOfAllCategories = categories.stream().mapToDouble(e -> e.getEstimatedTime()).sum();

        this.progressbarProject.setProgress((float) sumOfTimeEntries);
        this.progressbarProject.setMax((float) sumOfAllCategories);
        this.progressbarProject.setProgressText(sumOfTimeEntries + " of " + sumOfAllCategories);
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
        projectReportChart.setData(data);

        projectReportChart.highlightValues(null);

        projectReportChart.invalidate();
    }

    private void configurateChart() {
        projectReportChart.setUsePercentValues(true);
        projectReportChart.getDescription().setEnabled(false);
        projectReportChart.setExtraOffsets(5, 10, 5, 5);

        projectReportChart.setDragDecelerationFrictionCoef(0.95f);

        projectReportChart.setCenterTextTypeface(mTfLight);

        projectReportChart.setDrawHoleEnabled(true);
        projectReportChart.setHoleColor(Color.WHITE);

        projectReportChart.setTransparentCircleColor(Color.WHITE);
        projectReportChart.setTransparentCircleAlpha(110);

        projectReportChart.setHoleRadius(58f);
        projectReportChart.setTransparentCircleRadius(61f);

        projectReportChart.setDrawCenterText(true);

        projectReportChart.setRotationAngle(0);
        projectReportChart.setRotationEnabled(true);
        projectReportChart.setHighlightPerTapEnabled(true);

        //mChart.setUnit(" h");
        //mChart.setDrawUnitsInChart(true);

        projectReportChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = projectReportChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        projectReportChart.setEntryLabelColor(Color.BLACK);
        projectReportChart.setEntryLabelTypeface(mTfRegular);
        projectReportChart.setEntryLabelTextSize(12f);
    }

    private SpannableString generateCenterSpannableText(String projectName) {
        SpannableString s = new SpannableString(projectName);
        s.setSpan(new RelativeSizeSpan(1.7f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);

        return s;
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