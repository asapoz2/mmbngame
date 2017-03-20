package com.example.mmbndemo.battle.attacks;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.mmbndemo.AndroidTutorial;
import com.example.mmbndemo.BitmapGetter;
import com.example.mmbndemo.R;
import com.example.mmbndemo.battle.BattleAttack;
import com.example.mmbndemo.battle.BattleInfo;
import com.example.mmbndemo.battle.actionobjects.ShockWaveMMObject;



public class ShockWaveMM extends BattleAttack{
	long startTimer;
	private static Bitmap defaultbm=null;
	public ShockWaveMM(int x, int y,long numTimer){
		this.x=x;
		this.y=y;
		startTimer=numTimer;
		//currPhase=2;
		shouldDie=false;
		if(defaultbm==null){
			defaultbm=BitmapGetter.getBitmap(R.drawable.shockwavemm);
		}
	}
	public void doAttack(BattleInfo info, long numTimer){
	
		if(numTimer>startTimer+1){
			shouldDie=true;
		}else{
			info.addActionObject(new ShockWaveMMObject(x+1,y,10));
		}
	}
	
	public void draw(Canvas c, BattleInfo info){
		double scale=AndroidTutorial.scale;
		int megax=x*40;
		int megay=y*24+43;
		//g.drawImage((Image)defaultimage,megax,megay+9,megax+78,megay+46,0,0,78,37,null);
		Rect dest= new Rect((int)(megax*scale), (int)(scale*(megay+9)),(int)( scale*(megax+78)),(int)(scale*(megay+46)));
		c.drawBitmap(defaultbm, null, dest, null);
	}
	public boolean checkDead(){
		return shouldDie;
	}
}
