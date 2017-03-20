package com.example.mmbndemo.battle.actionobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.mmbndemo.AndroidTutorial;
import com.example.mmbndemo.BitmapGetter;
import com.example.mmbndemo.R;
import com.example.mmbndemo.battle.BattleActionObject;
import com.example.mmbndemo.battle.BattleInfo;
import com.example.mmbndemo.battle.BattleObject;



public class ShockWaveMMObject extends BattleObject implements BattleActionObject {
	int damage;
	private static Bitmap defaultbm=null;
	
	public ShockWaveMMObject(int x, int y,int damage){
		this.x=x;
		this.y=y;
		this.damage=damage;
		shouldDie=false;
		if(defaultbm==null){
			defaultbm=BitmapGetter.getBitmap(R.drawable.shockwaveobjmm);
		}
	}

	protected void doMoveAndAttack(BattleInfo info){
		if(x<=4){
			x++;
			BattleObject occupier=info.getOccupier(info.getTileOf(this));
			if(occupier!=null&& occupier!=info.getFighter()){//&& occupier.canBeHit()
				doDamage(info,occupier);
				//info.getFighter().changeStatus(Status.DAMAGED,1);
				//info.getFighter().changeStatus(1, 2);
			}
		}else{
			shouldDie=true;
		}
		return;
	}
	public void doDamage(BattleInfo info,BattleObject bo){
		bo.damage(info,damage);
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
	
	@Override
	public void doAction(BattleInfo info, long numTimes) {
		if(numTimes%5==0){
			doMoveAndAttack(info);
		}
		
	} 
}
