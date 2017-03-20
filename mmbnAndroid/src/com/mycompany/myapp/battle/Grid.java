package com.mycompany.myapp.battle;

import com.mycompany.myapp.AndroidTutorial;
import com.mycompany.myapp.BitmapGetter;
import com.mycompany.myapp.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.*;

public class Grid {
	//private Bitmap lowbluetile;
	//private Bitmap midbluetile;
	private Bitmap topbluetile;
	private Bitmap midbluetile;
	private Bitmap lowbluetile;
	private Bitmap topredtile;
	private Bitmap midredtile;
	private Bitmap lowredtile;
	//private long frameTimer;
	public Grid(){
	
		this.topbluetile=BitmapGetter.getBitmap(R.drawable.topbluetile);
		this.midbluetile=BitmapGetter.getBitmap(R.drawable.midbluetile);
		this.lowbluetile=BitmapGetter.getBitmap(R.drawable.lowbluetile);
		this.topredtile=BitmapGetter.getBitmap(R.drawable.topredtile);
		this.midredtile=BitmapGetter.getBitmap(R.drawable.midredtile);
		this.lowredtile=BitmapGetter.getBitmap(R.drawable.lowredtile);
	}
	public void draw(Canvas canvas) {
		Bitmap curr;
		Rect dest;
		double xscale=AndroidTutorial.scale;
		double yscale=AndroidTutorial.scale;
		for(int i=0;i<6;i++){
			for(int j=0;j<3;j++){
				if(i<3){
					if(j==0){
						dest= new Rect((int)(40*xscale*i), (int)(73*yscale+24*yscale*j),(int)( 40*xscale+40*xscale*i),(int)(24*xscale+73*xscale+24*xscale*j));
						curr=topbluetile;
					}else if(j==1){
						dest= new Rect((int)(40*xscale*i), (int)(73*yscale+24*yscale*j),(int)( 40*xscale+40*xscale*i),(int)(24*xscale+73*xscale+24*xscale*j));
						curr=midbluetile;
					}else{
						dest= new Rect((int)(40*xscale*i), (int)(73*yscale+24*yscale*j),(int)( 40*xscale+40*xscale*i),(int)(24*xscale+73*xscale+33*xscale*j));
						curr=lowbluetile;
					}
				}else{
					if(j==0){
						dest= new Rect((int)(40*xscale*i), (int)(73*yscale+24*yscale*j),(int)( 40*xscale+40*xscale*i),(int)(24*xscale+73*xscale+24*xscale*j));
						curr=topredtile;
					}else if(j==1){
						dest= new Rect((int)(40*xscale*i), (int)(73*yscale+24*yscale*j),(int)( 40*xscale+40*xscale*i),(int)(24*xscale+73*xscale+24*xscale*j));
						curr=midredtile;
					}else{
						dest= new Rect((int)(40*xscale*i), (int)(73*yscale+24*yscale*j),(int)( 40*xscale+40*xscale*i),(int)(24*xscale+73*xscale+33*xscale*j));
						curr=lowredtile;
					}
				}
				//Rect dest = new Rect(0, 73, 40*scale,24*scale+73*5);
				
				canvas.drawBitmap(curr, null, dest, null);
			}
		}
		
		
	}
}
