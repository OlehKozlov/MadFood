package ua.kozlov.madfood.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import ua.kozlov.madfood.models.FoodsR;
import ua.kozlov.madfood.models.OneDayPlanR;
import ua.kozlov.madfood.models.PlanDataR;

public final class CircleView extends View{
    private float vHeight;
    private float vWidth;
    private float centerX;
    private float centerY;
    private final float turnover = 360.0f;
    private final float reverse = 180f;
    private final float rightAngle = 90f;
    private final float threeQuarter = 270f;
    //private final String fontHelveticaNeue = "fonts/HelveticaNeue.ttf";
    //Typeface typefacefontHelveticaNeue;
    private float start;
    private float angle;
    private float diametr;
    private float minSide;
    private float radius;
    private float small;
    private float one;
    private Paint paint;
    //private FoodsR foodsR;
    private float calories;
    private float fats;
    private float carbonates;
    private float proteins;
    private float gi;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
       // typefacefontHelveticaNeue = Typeface.createFromAsset(getResources().getAssets(),fontHelveticaNeue);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        vHeight = getHeight();
        vWidth = getWidth();
        centerX = vWidth/2;
        centerY = vHeight/2;
        minSide = Math.min(vHeight, vWidth);
        diametr  = (minSide - minSide/10);
        radius = diametr/2;
        small = diametr*0.1f;
        one = (radius-small);
        drawWhiteCircle(radius, canvas);
        //if(foodsR != null) {
            float total = calories + fats + carbonates +
                    proteins + gi;
            angle  = (turnover / total) * calories;
            //DebugLogger.log("start: " + start + "\n" + "angle: " + angle);
            drawSector(start, angle, radius , Color.RED, canvas);
            start += angle;
            angle  = (turnover / total) * fats;
           // DebugLogger.log("start: " + start + "\n" + "angle: " + angle);
            drawSector(start, angle, radius, Color.GREEN, canvas);
            start += angle;
            angle  = (turnover / total) * carbonates;
           // DebugLogger.log("start: " + start + "\n" + "angle: " + angle);
            drawSector(start, angle, radius, Color.BLUE, canvas);
            start += angle;
            angle  = (turnover / total) * proteins;
            //DebugLogger.log("start: " + start + "\n" + "angle: " + angle);
            drawSector(start, angle, radius, Color.MAGENTA, canvas);
            start += angle;
            angle  = (turnover / total) * gi;
            //DebugLogger.log("start: " + start + "\n" + "angle: " + angle);
            drawSector(start, angle, radius, Color.YELLOW, canvas);
        //}
        drawWhiteCircle(radius * 0.8f,canvas);
    }

    public void setValues(FoodsR foods) {
        init();
        calories = foods.getCalories();
        fats = foods.getFat();
        carbonates = foods.getCarbonates();
        proteins = foods.getProteins();
        gi = foods.getGi();
    }

    public void setValues(OneDayPlanR foods) {
        init();
        calories = Float.parseFloat(foods.getCalories());
        fats = Float.parseFloat(foods.getFat());
        carbonates = Float.parseFloat(foods.getCarbonates());
        proteins = Float.parseFloat(foods.getProteins());
        gi = Float.parseFloat(foods.getGi());
    }

    public void setValues(PlanDataR foods) {
        init();
        calories = Float.parseFloat(foods.getCalories());
        fats = Float.parseFloat(foods.getFat());
        carbonates = Float.parseFloat(foods.getCarbonates());
        proteins = Float.parseFloat(foods.getProteins());
        gi = Float.parseFloat(foods.getGi());
    }

    private void drawWhiteCircle(float radius, Canvas canvas) {
        RectF circle = new RectF();
        paint.setColor(Color.WHITE);
        circle.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(circle, 0, turnover, true, paint);
    }

    private void drawSector(float start, float angle, float radius, int color, Canvas canvas) {
        RectF arc = new RectF();
        paint.setColor(color);
        arc.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(arc, start, angle, true, paint);
    }

}
