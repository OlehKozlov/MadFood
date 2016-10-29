package ua.kozlov.madfood.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import ua.kozlov.madfood.R;
import ua.kozlov.madfood.database.DatabaseManager;
import ua.kozlov.madfood.models.PlanDataR;
import ua.kozlov.madfood.models.UserR;
import ua.kozlov.madfood.utils.DebugLogger;

public class StatisticFragment extends Fragment implements OnChartGestureListener {
    private LineChart mLineChart;
    private Button mButtonCalories, mButtonFats, mButtonCarbs, mButtonProts,
            mButtonGi, mBuuutonWeight, mButtonDay, mButtonWeek, mButtonMonth;
    private Realm mRealm;
    private static ArrayList<Float> mCaloriesList;
    private static ArrayList<Float> mFatsList;
    private static ArrayList<Float> mCarbonatesList;
    private static ArrayList<Float> mProteinsList;
    private static ArrayList<Float> mGiList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistic_fragment, container, false);
        mRealm = Realm.getDefaultInstance();
        mLineChart = (LineChart) view.findViewById(R.id.statisticLineChart);
        mButtonCalories = (Button) view.findViewById(R.id.statisticButtonCalories);
        mButtonCalories.setOnClickListener(v -> {
            DebugLogger.log(getString(R.string.log_cal_click));
            setCaloriesData();
        });
        mButtonFats = (Button) view.findViewById(R.id.statisticButtonFats);
        mButtonFats.setOnClickListener(v -> {
            DebugLogger.log(getString(R.string.log_fat_click));
            setFatsData();
        });
        mButtonCarbs = (Button) view.findViewById(R.id.statisticButtonCarbs);
        mButtonCarbs.setOnClickListener(v -> {
            DebugLogger.log(getString(R.string.log_carb_click));
            setCarbonatesData();
        });
        mButtonProts = (Button) view.findViewById(R.id.statisticButtonProts);
        mButtonProts.setOnClickListener(v -> {
            DebugLogger.log(getString(R.string.log_prot_click));
            setProteinsData();
        });
        mButtonGi = (Button) view.findViewById(R.id.statisticButtonGis);
        mButtonGi.setOnClickListener(v -> {
            DebugLogger.log(getString(R.string.log_gi_click));
            setGIData();
        });
        mBuuutonWeight = (Button) view.findViewById(R.id.statisticButtonWeight);
        mBuuutonWeight.setOnClickListener(v -> {
            DebugLogger.log(getString(R.string.log_weight_click));
            setWeightData();
        });
        mButtonDay = (Button) view.findViewById(R.id.statisticButtonDay);
        mButtonDay.setOnClickListener(v -> {
            DebugLogger.log(getString(R.string.log_day_click));
            setDaysData();
        });
        mButtonWeek = (Button) view.findViewById(R.id.statisticButtonWeek);
        mButtonWeek.setOnClickListener(v -> {
            DebugLogger.log(getString(R.string.log_week_click));
            setWeeksData();
        });
        mButtonMonth = (Button) view.findViewById(R.id.statisticButtonMonth);
        mButtonMonth.setOnClickListener(v -> {
            DebugLogger.log(getString(R.string.log_month_click));
            setMonthsData();
        });
        mLineChart.setOnChartGestureListener(this);
        mLineChart.setTouchEnabled(true);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setScaleYEnabled(false);
        mLineChart.setDrawGridBackground(true);
        mLineChart.setBackgroundResource(R.color.icons);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.enableGridDashedLine(2f, 10f, 0f);
        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.enableGridDashedLine(2f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(true);
        mLineChart.getAxisRight().setEnabled(false);
        setDaysData();
        setCaloriesData();
        return view;
    }

    private void setDaysData() {
        mCaloriesList = new ArrayList<>();
        mFatsList = new ArrayList<>();
        mCarbonatesList = new ArrayList<>();
        mProteinsList = new ArrayList<>();
        mGiList = new ArrayList<>();
        RealmResults<PlanDataR> dayResults = DatabaseManager.getAllPlanData(mRealm);
        if (dayResults.size() > 0) {
            float cal = 0;
            float fat = 0;
            float carb = 0;
            float prot = 0;
            float gi = 0;
            Calendar calendar = Calendar.getInstance();
            if (dayResults.size() == 0) {
                calendar.setTimeInMillis(new Date().getTime());
            } else {
                calendar.setTimeInMillis(dayResults.get(0).getDate());
            }
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            for (int i = 0; i < dayResults.size(); i++) {
                PlanDataR planDataR = dayResults.get(i);
                calendar.setTimeInMillis(planDataR.getDate());
                int localDay = calendar.get(Calendar.DAY_OF_MONTH);
                if (day == localDay) {
                    cal += Float.parseFloat(planDataR.getCalories());
                    fat += Float.parseFloat(planDataR.getFat());
                    carb += Float.parseFloat(planDataR.getCarbonates());
                    prot += Float.parseFloat(planDataR.getProteins());
                    gi += Float.parseFloat(planDataR.getGi());
                } else {
                    mCaloriesList.add(cal);
                    mFatsList.add(fat);
                    mCarbonatesList.add(carb);
                    mProteinsList.add(prot);
                    mGiList.add(gi);
                    day = localDay;
                    cal = Float.parseFloat(planDataR.getCalories());
                    fat = Float.parseFloat(planDataR.getFat());
                    carb = Float.parseFloat(planDataR.getCarbonates());
                    prot = Float.parseFloat(planDataR.getProteins());
                    gi = Float.parseFloat(planDataR.getGi());
                }
                if (i == (dayResults.size() - 1)) {
                    mCaloriesList.add(cal);
                    mFatsList.add(fat);
                    mCarbonatesList.add(carb);
                    mProteinsList.add(prot);
                    mGiList.add(gi);
                }
            }
            setCaloriesData();
        }
    }

    private void setWeeksData() {
        mCaloriesList = new ArrayList<>();
        mFatsList = new ArrayList<>();
        mCarbonatesList = new ArrayList<>();
        mProteinsList = new ArrayList<>();
        mGiList = new ArrayList<>();
        RealmResults<PlanDataR> weekResults = DatabaseManager.getAllPlanData(mRealm);
        if (weekResults.size() > 0) {
            float cal = 0;
            float fat = 0;
            float carb = 0;
            float prot = 0;
            float gi = 0;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(weekResults.get(0).getDate());
            int week = calendar.get(Calendar.WEEK_OF_YEAR);
            for (int i = 0; i < weekResults.size(); i++) {
                PlanDataR planDataR = weekResults.get(i);
                calendar.setTimeInMillis(planDataR.getDate());
                int localWeek = calendar.get(Calendar.WEEK_OF_YEAR);
                if (week == localWeek) {
                    cal += Float.parseFloat(planDataR.getCalories());
                    fat += Float.parseFloat(planDataR.getFat());
                    carb += Float.parseFloat(planDataR.getCarbonates());
                    prot += Float.parseFloat(planDataR.getProteins());
                    gi += Float.parseFloat(planDataR.getGi());
                } else {
                    mCaloriesList.add(cal);
                    mFatsList.add(fat);
                    mCarbonatesList.add(carb);
                    mProteinsList.add(prot);
                    mGiList.add(gi);
                    week = localWeek;
                    cal = Float.parseFloat(planDataR.getCalories());
                    fat = Float.parseFloat(planDataR.getFat());
                    carb = Float.parseFloat(planDataR.getCarbonates());
                    prot = Float.parseFloat(planDataR.getProteins());
                    gi = Float.parseFloat(planDataR.getGi());
                }
                if (i == (weekResults.size() - 1)) {
                    mCaloriesList.add(cal);
                    mFatsList.add(fat);
                    mCarbonatesList.add(carb);
                    mProteinsList.add(prot);
                    mGiList.add(gi);
                }
            }
            setCaloriesData();
        }
    }

    private void setMonthsData() {
        mCaloriesList = new ArrayList<>();
        mFatsList = new ArrayList<>();
        mCarbonatesList = new ArrayList<>();
        mProteinsList = new ArrayList<>();
        mGiList = new ArrayList<>();
        RealmResults<PlanDataR> monthResults = DatabaseManager.getAllPlanData(mRealm);
        if (monthResults.size() > 0) {
            float cal = 0;
            float fat = 0;
            float carb = 0;
            float prot = 0;
            float gi = 0;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(monthResults.get(0).getDate());
            int month = calendar.get(Calendar.MONTH);
            for (int i = 0; i < monthResults.size(); i++) {
                PlanDataR planDataR = monthResults.get(i);
                calendar.setTimeInMillis(planDataR.getDate());
                int localMonth = calendar.get(Calendar.MONTH);
                if (month == localMonth) {
                    cal += Float.parseFloat(planDataR.getCalories());
                    fat += Float.parseFloat(planDataR.getFat());
                    carb += Float.parseFloat(planDataR.getCarbonates());
                    prot += Float.parseFloat(planDataR.getProteins());
                    gi += Float.parseFloat(planDataR.getGi());
                } else {
                    mCaloriesList.add(cal);
                    mFatsList.add(fat);
                    mCarbonatesList.add(carb);
                    mProteinsList.add(prot);
                    mGiList.add(gi);
                    month = localMonth;
                    cal = Float.parseFloat(planDataR.getCalories());
                    fat = Float.parseFloat(planDataR.getFat());
                    carb = Float.parseFloat(planDataR.getCarbonates());
                    prot = Float.parseFloat(planDataR.getProteins());
                    gi = Float.parseFloat(planDataR.getGi());
                }
                if (i == (monthResults.size() - 1)) {
                    mCaloriesList.add(cal);
                    mFatsList.add(fat);
                    mCarbonatesList.add(carb);
                    mProteinsList.add(prot);
                    mGiList.add(gi);
                }
            }
            setCaloriesData();
        }
    }

    private void setChartParams(LineDataSet d, final ArrayList<ILineDataSet> dataSets) {
        d.setLineWidth(2.5f);
        d.setCircleRadius(1f);
        dataSets.add(d);
        LineData data = new LineData(dataSets);
        mLineChart.setData(data);
        mLineChart.invalidate();
        mLineChart.animateX(1000);
    }

    private void setCaloriesData() {
        mLineChart.resetTracking();
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> calories = new ArrayList<>();
        for (int i = 0; i < mCaloriesList.size(); i++) {
            calories.add(new Entry(i, mCaloriesList.get(i)));
        }
        LineDataSet d = new LineDataSet(calories, getString(R.string.calories));
        d.setColor(Color.RED);
        setChartParams(d, dataSets);
    }

    private void setFatsData() {
        mLineChart.resetTracking();
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> fats = new ArrayList<>();
        for (int i = 0; i < mFatsList.size(); i++) {
            fats.add(new Entry(i, mFatsList.get(i)));
        }
        LineDataSet d = new LineDataSet(fats, getString(R.string.fats));
        d.setColor(Color.GREEN);
        setChartParams(d, dataSets);
    }

    private void setCarbonatesData() {
        mLineChart.resetTracking();
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> carbonates = new ArrayList<>();
        for (int i = 0; i < mCarbonatesList.size(); i++) {
            carbonates.add(new Entry(i, mCarbonatesList.get(i)));
        }
        LineDataSet d = new LineDataSet(carbonates, getString(R.string.carbonates));
        d.setColor(Color.BLUE);
        setChartParams(d, dataSets);
    }

    private void setProteinsData() {
        mLineChart.resetTracking();
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> proteins = new ArrayList<>();
        for (int i = 0; i < mProteinsList.size(); i++) {
            proteins.add(new Entry(i, mProteinsList.get(i)));
        }
        LineDataSet d = new LineDataSet(proteins, getString(R.string.proteins));
        d.setColor(Color.MAGENTA);
        setChartParams(d, dataSets);
    }

    private void setGIData() {
        mLineChart.resetTracking();
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> gis = new ArrayList<>();
        for (int i = 0; i < mGiList.size(); i++) {
            gis.add(new Entry(i, mGiList.get(i)));

        }
        LineDataSet d = new LineDataSet(gis, getString(R.string.gi));
        d.setColor(Color.YELLOW);
        setChartParams(d, dataSets);
    }

    private void setWeightData() {
        mLineChart.resetTracking();
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> weights = new ArrayList<>();
        RealmResults<UserR> weightResults = DatabaseManager.getUserData(mRealm);
        for (int i = 0; i < weightResults.size(); i++) {
            UserR userR = weightResults.get(i);
            final float weight = userR.getWeight();
            weights.add(new Entry(i, weight));
        }
        LineDataSet d = new LineDataSet(weights, getString(R.string.weight));
        d.setColor(Color.CYAN);
        setChartParams(d, dataSets);
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        DebugLogger.log("Gesture START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        DebugLogger.log("Gesture END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mLineChart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        mLineChart.zoomOut();
        DebugLogger.log("LongPress Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        DebugLogger.log("DoubleTap Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        DebugLogger.log("SingleTap Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        DebugLogger.log("Fling Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        DebugLogger.log("Scale / Zoom ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        DebugLogger.log("Translate / Move dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onPause() {
        super.onPause();
        mRealm.close();
        DebugLogger.log(getString(R.string.log_realm_closed));
    }

}