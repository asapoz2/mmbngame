package com.mycompany.myapp.battle.attacks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.mycompany.myapp.AndroidTutorial;
import com.mycompany.myapp.BitmapGetter;
import com.mycompany.myapp.R;
import com.mycompany.myapp.battle.BattleAttack;
import com.mycompany.myapp.battle.BattleEnemy;
import com.mycompany.myapp.battle.BattleInfo;


public class Buster extends BattleAttack{
	long startTimer;
	Bitmap defaultbm;
	public Buster(int x, int y,long numTimer){//Bitmap defaultbm,
		this.x=x;
		this.y=y;
		startTimer=numTimer;
		//currPhase=2;
		shouldDie=false;
		defaultbm=BitmapGetter.getBitmap(R.drawable.mmbuster);
		//this.defaultbm=defaultbm;
	}
	public void doAttack(BattleInfo info, long numTimer){
		/*if(numTimer==startTimer){
			
		}*/
		if(numTimer>startTimer+1){
			shouldDie=true;
		}else{
			for(int i=x+1;i<6;i++){
				if((i<info.horiz)&&info.getOccupier(info.toTileNumber(i, y))!=null ){
					info.getOccupier(info.toTileNumber(i, y)).damage(info,5);
					//info.damage(info.getOccupier(info.toTileNumber(i, y)),10);
					return;
				}
			}
			/*for(BattleEnemy be:info.getBattleEnemies()){
				be.damage(info, 5);
			}*/
		}
	}

	public boolean checkDead(){
		return shouldDie;
	}
	public void draw(Canvas c, BattleInfo info){
		double scale=AndroidTutorial.scale;
		Rect dest= new Rect((int)(40*scale*x), (int)(43*scale+24*scale*y),(int)( 48*scale+40*scale*x),(int)(46*scale+43*scale+24*scale*y));
		c.drawBitmap(defaultbm, null, dest, null);
	}
	//initialize and draw
	
	
}
