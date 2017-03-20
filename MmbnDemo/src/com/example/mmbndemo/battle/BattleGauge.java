package com.example.mmbndemo.battle;

import com.example.mmbndemo.AndroidTutorial;
import com.example.mmbndemo.BitmapGetter;


import com.example.mmbndemo.R;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;



public class BattleGauge {
	protected Bitmap defaultimage;
	protected Bitmap full1;
	protected Bitmap full2;
	protected Bitmap full3;
	private long startGauge=0;
	private long lengthGauge=60;
	private boolean canUse=false;
	public BattleGauge(){
		defaultimage=BitmapGetter.getBitmap(R.drawable.custom);
		full1=BitmapGetter.getBitmap(R.drawable.fullcustom1);
		full2=BitmapGetter.getBitmap(R.drawable.fullcustom2);
		full3=BitmapGetter.getBitmap(R.drawable.fullcustom3);
		
	}
	public void updateGuage(long numTimes){
		if(numTimes>startGauge+lengthGauge){
			canUse=true;
		}else{
			canUse=false;
		}
		
	}
	public void restartGauge(long numTimer){
		startGauge = numTimer;
		canUse=false;
	}
	public boolean checkPause(){
		return canUse;
	}
	public double percent(long numTimer){
		double percent= (double)(numTimer-startGauge)/(double)lengthGauge;
		return (percent>=1)?(1):percent;
	}
	
	
	public void draw(Canvas c,BattleInfo info){
		double scale=AndroidTutorial.scale;
		double startx=49*scale;
		double starty=1*scale;
		//142 16
		Rect dest= new Rect((int)(startx), (int)(starty),(int)( startx+142*scale),(int)(16*scale+starty));
		
		long numTimer=info.getLasttime();
		//49 1
		//56 9 start of bar
		if(!canUse){
			c.drawBitmap(defaultimage, null, dest, null);
			Paint p=new Paint();
			p.setColor(Color.rgb(204,255,255));
			c.drawRect((float)(56*scale), (float)(9*scale), (float)(56*scale+percent(numTimer)*128*scale),(float)(9*scale+6*scale), p);
			/*g.drawImage((Image)defaultimage,8+49,1+30,null);
			g.setColor(new Color(204,255,255));
			g.fillRect( 56+8, 30+9,  (int)(percent(numTimer)*128),6);*/
			
		}else{
			if(numTimer%3==0){
				//g.drawImage((Image)full1,8+49,1+30,null);
				c.drawBitmap(full1, null, dest, null);
			}else if(numTimer%3==1){
				//g.drawImage((Image)full2,8+49,1+30,null);
				c.drawBitmap(full2, null, dest, null);
			}else if(numTimer%3==2){
				//g.drawImage((Image)full3,8+49,1+30,null);
				c.drawBitmap(full3, null, dest, null);
			}
		}
		
	}
}
