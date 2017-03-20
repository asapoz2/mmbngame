package com.mycompany.myapp.battle.actionobjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.mycompany.myapp.AndroidTutorial;
import com.mycompany.myapp.BitmapGetter;
import com.mycompany.myapp.R;
import com.mycompany.myapp.battle.BattleActionObject;
import com.mycompany.myapp.battle.BattleInfo;
import com.mycompany.myapp.battle.BattleObject;
import com.mycompany.myapp.battle.BattleStatus.Status;


public class ShockWaveAction extends BattleObject implements BattleActionObject {
	int damage;
	//int hp=1;
	private Bitmap defaultbm;
	
	public ShockWaveAction(int x, int y,int damage){
		this.x=x;
		this.y=y;
		this.damage=damage;
		shouldDie=false;
		//loadImages(context);
		//this.defaultbm=mybm;
		this.defaultbm=BitmapGetter.getBitmap(R.drawable.shockwave);
	}
	public void doAction(BattleInfo info,long numTimer){
		//System.out.println("shockwaveaction");
		if(numTimer%5==0){
			doMoveAndAttack(info);
		}
		
	}
	protected void doMoveAndAttack(BattleInfo info){
		if(x>0){
			x--;
			if(info.getOccupier(info.getTileOf(this))==info.getFighter() && (info.getFighter()).canBeHit()){
				doDamage(info.getFighter());
				//should not be commented when status is in effect
				info.getFighter().changeStatus(Status.DAMAGED,1);
			}
		}else{
			shouldDie=true;
		}
		return;
	}
	public void doDamage(BattleObject bo){
		bo.reduceHP(damage);
	}
	public boolean checkDead(){
		return shouldDie;
	}
	public void draw(Canvas c, BattleInfo info){
		double scale=AndroidTutorial.scale;
		//g.drawImage((Image)defaultimage,x*40+8,y*24+30+58,null);
		Rect dest= new Rect((int)(40*scale*x), (int)(58*scale+24*scale*y),(int)( 37*scale+40*scale*x),(int)(35*scale+58*scale+24*scale*y));
		c.drawBitmap(defaultbm, null, dest, null);
	}
	/*public void loadImages(Context context){
		defaultbm=BitmapFactory.decodeResource(context.getResources(), R.drawable.shockwave);
	}*/
	
	
}
