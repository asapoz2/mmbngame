package com.example.mmbndemo.ui;

import com.example.mmbndemo.AndroidTutorial;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class DraggablePad {
	private double startx;
	private double starty;
	private double width;
	private double height;
	private double minstretch;
	private double thresh;
	private boolean dragging=false;
	private float dragx=0;
	private float dragy=0;
	private long lasttime;
	private final long timeout=100;
	public enum Direction{
		LEFT,RIGHT,UP,DOWN,NONE
	};
	public DraggablePad(double startx, double starty,double width,double height,double maxstretch,double minstretch){
		this.startx=startx;
		this.starty=starty;
		this.width=width;
		this.height=height;
		this.minstretch=maxstretch;
		this.thresh=minstretch;
		lasttime=0;
	}
	private boolean inRange(float touched_x, float touched_y){
		return (touched_x>startx&&touched_y>starty &&touched_x<(startx+width) && touched_y<(starty+height));
	}
	private boolean wasClickedOn(MotionEvent event){
		
	    int pointerIndex = event.getActionIndex();

	    int maskedAction = event.getActionMasked();
		float touched_x = event.getX(pointerIndex);
	    float touched_y=event.getY(pointerIndex);
		return  ((maskedAction==MotionEvent.ACTION_POINTER_DOWN||maskedAction==MotionEvent.ACTION_DOWN)&&inRange(touched_x,touched_y));
	}
	public Direction handleMotionEvent(MotionEvent event,long currTime){
		int pointerIndex = event.getActionIndex();
		dragx= event.getX(pointerIndex);
	    dragy=event.getY(pointerIndex);
	    int maskedAction = event.getActionMasked();
	    if(dragging&&(maskedAction==MotionEvent.ACTION_UP||maskedAction==MotionEvent.ACTION_POINTER_UP)){
	    	dragging=false;
	    	return Direction.NONE;
	    }
	    Direction mydir=Direction.NONE;
	    dragging=wasClickedOn(event)||(dragging);
	    if(dragging){
	    	if(((dragx+thresh)<(startx+(width/2)))&&Math.abs(dragx-(startx+(width/2)))>Math.abs(dragy-(starty+(height/2)))){
	    		mydir= Direction.LEFT;
	    	}
	    	if(((dragx-thresh)>(startx+(width/2)))&&Math.abs(dragx-(startx+(width/2)))>Math.abs(dragy-(starty+(height/2)))){
	    		mydir= Direction.RIGHT;
	    	}
	    	if(((dragy+thresh)<(starty+(height/2)))&&Math.abs(dragx-(startx+(width/2)))<Math.abs(dragy-(starty+(height/2)))){
	    		mydir= Direction.UP;
	    	}
	    	if(((dragy-thresh)>(starty+(height/2)))&&Math.abs(dragx-(startx+(width/2)))<Math.abs(dragy-(starty+(height/2)))){	
	    		mydir=Direction.DOWN;
	    	}
	    }
	    if(currTime>(lasttime+timeout)&&mydir!=Direction.NONE){
	    	lasttime=currTime;
	    	return mydir;
	    }
		return Direction.NONE;
	}
	public void draw(Canvas c){
		
		//Rect rect=new Rect((int)(scale*startx),(int)(starty),(int)(startx+width),(int)(starty+height));
		Paint p=new Paint();
		p.setColor(Color.GRAY);
		c.drawCircle((float)((startx+width/2)), (float)((starty+height/2)), (float)(width/2), p);
		p.setColor(Color.MAGENTA);
		double padx,pady;
		if(dragging){
			padx=dragx;
			pady=dragy;
		}else{
			padx=(startx+width/2);
			pady=(starty+height/2);
		}
		
		c.drawCircle((float)((padx)), (float)((pady)), (float)(width/4), p);
	}

}
