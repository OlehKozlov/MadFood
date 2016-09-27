package ua.kozlov.madfood.utils;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import ua.kozlov.madfood.models.FoodsR;
import ua.kozlov.madfood.utils.DebugLogger;

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
    private FoodsR foodsR;

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
        if(foodsR != null) {
            float total = foodsR.getCalories() + foodsR.getFat() + foodsR.getCarbonates() +
                    foodsR.getProteins() + foodsR.getGi();
            angle  = (turnover / total) * foodsR.getCalories();
            DebugLogger.log("start: " + start + "\n" + "angle: " + angle);
            drawSector(start, angle, radius , Color.RED, canvas);
            start += angle;
            angle  = (turnover / total) * foodsR.getFat();
            DebugLogger.log("start: " + start + "\n" + "angle: " + angle);
            drawSector(start, angle, radius, Color.GREEN, canvas);
            start += angle;
            angle  = (turnover / total) * foodsR.getCarbonates();
            DebugLogger.log("start: " + start + "\n" + "angle: " + angle);
            drawSector(start, angle, radius, Color.BLUE, canvas);
            start += angle;
            angle  = (turnover / total) * foodsR.getProteins();
            DebugLogger.log("start: " + start + "\n" + "angle: " + angle);
            drawSector(start, angle, radius, Color.MAGENTA, canvas);
            start += angle;
            angle  = (turnover / total) * foodsR.getGi();
            DebugLogger.log("start: " + start + "\n" + "angle: " + angle);
            drawSector(start, angle, radius, Color.YELLOW, canvas);
        }
        drawWhiteCircle(radius * 0.8f,canvas);
    }

    public void setValues(FoodsR foodsR) {
        init();
        this.foodsR = foodsR;
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

    private void drawTextByAngle(String text,float angle, Canvas canvas) {
        text = text.toUpperCase();
        Rect bounds = new Rect();
        paint.setColor(Color.WHITE);
        paint.setShadowLayer(2, 0, 0, Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        canvas.save();
        paint.getTextBounds(text, 0, text.length()-1, bounds);
        if(rightAngle <= angle && angle < threeQuarter) {
            canvas.rotate(angle, centerX, centerY);
            canvas.rotate(reverse, (centerX + radius), centerY);
            if(bounds.width() > radius)
                canvas.drawText(text, centerX + bounds.width()/2, centerY + bounds.height()/2, paint);
            else
                canvas.drawText(text, centerX + radius + radius * 0.1f, centerY + bounds.height()/2, paint);
        } else {
            canvas.rotate(angle, centerX, centerY);
            canvas.drawText(text, centerX + small + radius * 0.1f, centerY +  bounds.height()/2, paint);
        }
        canvas.restore();
    }

}
