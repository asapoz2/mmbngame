package com.example.mmbndemo.ui;

import com.example.mmbndemo.AndroidTutorial;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class InterfaceButton {
	protected int startx;
	protected int starty;
	protected int height;
	protected int width;
	public InterfaceButton(int startx, int starty, int width, int height){
		this.startx=startx;
		this.starty=starty;
		this.height=height;
		this.width=width;
	}
	public void draw(Canvas c,Paint p){
		double scale=AndroidTutorial.scale;
		/*Paint p= new Paint();
		p.setColor(Color.GREEN);*/
		Rect rect=new Rect((int)(startx*scale),(int)(starty*scale),(int)((startx+width)*scale),(int)((starty+30)*scale));
		c.drawRect(rect, p);
		
	}
	public boolean wasClickedOn(MotionEvent event){
		
	    int pointerIndex = event.getActionIndex();

	    int maskedAction = event.getActionMasked();
		float touched_x = event.getX(pointerIndex);
	    float touched_y=event.getY(pointerIndex);
		return  ((maskedAction==MotionEvent.ACTION_POINTER_DOWN||maskedAction==MotionEvent.ACTION_DOWN)&&inRange(touched_x,touched_y));
	}
			

	
	private boolean inRange(float touched_x, float touched_y){
		double scale=AndroidTutorial.scale;
		return (touched_x>scale*startx&&touched_y>scale*starty &&touched_x<scale*(startx+width) && touched_y<scale*(starty+height));
	}

}
