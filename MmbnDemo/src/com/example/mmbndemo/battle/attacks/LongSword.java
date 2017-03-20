package com.example.mmbndemo.battle.attacks;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.mmbndemo.AndroidTutorial;
import com.example.mmbndemo.BitmapGetter;
import com.example.mmbndemo.R;
import com.example.mmbndemo.battle.BattleAttack;
import com.example.mmbndemo.battle.BattleInfo;


public class LongSword extends BattleAttack{
	private long startTimer;
	private static Bitmap defaultbm=null;
	public LongSword(int x, int y,long numTimer){
		this.x=x;
		this.y=y;
		startTimer=numTimer;
		shouldDie=false;
		if(defaultbm==null){
			defaultbm=BitmapGetter.getBitmap(R.drawable.mmsword);
		}
	}
	public void doAttack(BattleInfo info, long numTimer){

		if(numTimer>startTimer+1){
			shouldDie=true;
		}else{
			if((x+1<info.horiz)&&info.getOccupier(info.toTileNumber(x+1, y))!=null ){
				info.getOccupier(info.toTileNumber(x+1, y)).damage(info,20);
			}
			if((x+2<info.horiz)&&info.getOccupier(info.toTileNumber(x+2, y))!=null ){
				info.getOccupier(info.toTileNumber(x+2, y)).damage(info,20);
		}
			
		}
		
	}
	
	public void draw(Canvas c,BattleInfo info){
		double scale=AndroidTutorial.scale;
		//49x58
		//g.drawImage((Image)defaultimage,x*40+8-4,y*24+30+43-4,null);
		Rect dest= new Rect((int)(40*scale*x-4*scale), (int)(43*scale+24*scale*y),(int)( 40*scale*x-4*scale+49*scale),(int)(43*scale+24*scale*y+56*scale));
		c.drawBitmap(defaultbm, null, dest, null);
	}
	public boolean checkDead(){
		return shouldDie;
	}
}
