package com.example.mmbndemo.battle.attacks;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.mmbndemo.AndroidTutorial;
import com.example.mmbndemo.BitmapGetter;
import com.example.mmbndemo.R;
import com.example.mmbndemo.battle.BattleAttack;
import com.example.mmbndemo.battle.BattleInfo;
import com.example.mmbndemo.battle.BattleObject;

public class Airshot extends BattleAttack {
	long startTimer;
	private static Bitmap gunpic=null;
	private static Bitmap megapic=null;
	public Airshot(int x, int y,long numTimer){
		this.x=x;
		this.y=y;
		startTimer=numTimer;
		//currPhase=2;
		shouldDie=false;
		if(gunpic==null){
			gunpic=BitmapGetter.getBitmap(R.drawable.airshot1);
			megapic=BitmapGetter.getBitmap(R.drawable.mmbuster);
		}
	}
	public void doAttack(BattleInfo info, long numTimer){
		//currPhase--;
		/*if(currPhase<=1){
			shouldDie=true;
		}*/
		if(numTimer>startTimer+1){
			shouldDie=true;
		}else{
			for(int i=x+1;i<6;i++){
				if((i<info.horiz)&&info.getOccupier(info.toTileNumber(i, y))!=null ){
					//System.out.println("found bad guy at "+i+","+y);
					BattleObject occupier=info.getOccupier(info.toTileNumber(i, y));
					
					int currTile=info.getTileOf(occupier);
					occupier.damage(info,20);
					if(!occupier.checkDead()){
						if((i+1<=5)&&info.getOccupier(info.toTileNumber(i+1, y))==null){
							info.setOccupier(occupier, info.toTileNumber(i+1, y));
							
							info.setOccupier(null, currTile);
							occupier.setX(i+1);
						}
					}
					//info.damage(info.getOccupier(info.toTileNumber(i, y)),10);
					return;
				}
			}
		}
	}
	
	public void draw(Canvas c, BattleInfo info){
		double scale=AndroidTutorial.scale;
		double megax=x*40*scale;
		double megay=(y*24+43)*scale;
		/*g.drawImage((Image)defaultimage,megax,megay,megax+48,megay+46,0,0,48,46,null);
		g.drawImage((Image)gunpic,megax+30,megay+10,megax+30+18,megay+10+13,0,0,18,13,null);*/
		Rect dest= new Rect((int)(40*scale*x), (int)(43*scale+24*scale*y),(int)( 48*scale+40*scale*x),(int)(46*scale+43*scale+24*scale*y));
		c.drawBitmap(megapic, null, dest, null);
		Rect dest2= new Rect((int)(megax+30*scale), (int)(megay+10*scale),(int)( megax+48*scale),(int)(megay+23*scale));
		c.drawBitmap(gunpic, null, dest2, null);
	}
	public boolean checkDead(){
		return shouldDie;
	}
}
