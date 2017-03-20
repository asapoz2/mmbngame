package com.example.mmbndemo.battle;

import java.util.ArrayList;

import com.example.mmbndemo.AndroidTutorial;
import com.example.mmbndemo.BitmapGetter;
import com.example.mmbndemo.R;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class BattleBackground {
	private static ArrayList<Bitmap> mybms;
	private static BattleBackground instance;
	private BattleBackground(){
		if(mybms==null){
			mybms=new ArrayList<Bitmap>();
			mybms.add(BitmapGetter.getBitmap(R.drawable.bg1));
			mybms.add(BitmapGetter.getBitmap(R.drawable.bg5));
			mybms.add(BitmapGetter.getBitmap(R.drawable.bg6));
			mybms.add(BitmapGetter.getBitmap(R.drawable.bg1));
			mybms.add(BitmapGetter.getBitmap(R.drawable.bg5));
			mybms.add(BitmapGetter.getBitmap(R.drawable.bg6));
			mybms.add(BitmapGetter.getBitmap(R.drawable.bg7));
			mybms.add(BitmapGetter.getBitmap(R.drawable.bg8));
		}
	}
	public static BattleBackground getInstance(){
		if(instance==null){
			instance=new BattleBackground();
		}
		return instance;
	}
	public void draw(Canvas c,long numTimer){
		double scale=AndroidTutorial.scale;
		Rect rect=new Rect();
		for(int i=0;i<272;i+=32){
			for(int j=0;j<172;j+=32){
				rect.set((int)(i*scale),(int)(j*scale),(int)((i+32)*scale),(int)((j+32)*scale));
				c.drawBitmap(mybms.get((int)(numTimer%7)), null,rect,null);
			}
			
		}
	}
	
}
