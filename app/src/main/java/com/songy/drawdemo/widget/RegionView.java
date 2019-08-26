package com.songy.drawdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description:
 *
 * @author by song on 2019-07-29.
 * email：bjay20080613@qq.com
 */
public class RegionView extends View {
    private Paint  mPaint;

    public RegionView(Context context) {
        super(context);
        init();
    }

    public RegionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint=new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);


    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Path ovalPath=new Path();

        RectF  rectF=new RectF(50,50,200,500);
        ovalPath.addOval(rectF,Path.Direction.CCW);

        Region rgn=new Region();
        Region region=new Region(50,50,200,200);
        rgn.setPath(ovalPath,region);
        drawRegion(canvas,rgn);


        Rect rectF1=new Rect(210,50,410,500);
        Rect rectF2=new Rect(210,275,600,400);
        canvas.drawRect(rectF1,mPaint);

        Region region1=new Region(rectF1);
        Region region2=new Region(rectF2);

        region1.op(region2, Region.Op.UNION);
        drawRegion(canvas,region1);
    }

    private void drawRegion(Canvas canvas, Region rgn){
        RegionIterator  regionIterator=new RegionIterator(rgn);
        Rect r=new Rect();
        while (regionIterator.next(r)){
            canvas.drawRect(r,mPaint);
        }
    }}
