package com.mycompany.myapp.battle;


import com.mycompany.myapp.AndroidTutorial;
import com.mycompany.myapp.BitmapGetter;
import com.mycompany.myapp.R;
import com.mycompany.myapp.battle.BattleStatus.Status;
import com.mycompany.myapp.battle.attacks.Buster;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class BattleCharacter extends BattleObject{
	private Bitmap defaultbm;
	private Bitmap hitpic;
	private BattleInfo info;
	private int x;
	private int y;

	private int statuslength=-1;
	private BattleStatus.Status mystatus=Status.NORMAL;

	private BattleAttack currAttack;
	public BattleCharacter(BattleInfo info){
		x=1;
		y=1;
		this.info=info;
		defaultbm=BitmapGetter.getBitmap(R.drawable.mmnorm);
		hitpic=BitmapGetter.getBitmap(R.drawable.mmhit);
		//this.context=context;
		//currAttack=new Buster(x,y,info.getLasttime());
		
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public BattleInfo getInfo() {
		return info;
	}

	
	public void setInfo(BattleInfo info) {
		this.info = info;
	}
	
	public boolean canBeHit(){
		return mystatus!=Status.DAMAGED;
		//return true;
	}
	
	public void moveTo(int x, int y){
		if(mystatus==Status.SHOCKED&&statuslength>=0){
			return;
		}
		int oldTile=info.getTileOf(this);
		this.x=x;
		this.y=y;
		info.setOccupier(null, oldTile);
		info.setOccupier(this, info.getTileOf(this));
		
	}
	public void changeStatus(Status status,int cantmovelength){
		this.statuslength=cantmovelength;
		this.mystatus=status;
	}
	
	public void draw(Canvas canvas) {
		double scale=AndroidTutorial.scale;
		if(mystatus==Status.DAMAGED || mystatus==Status.SHOCKED){
			//g.drawImage((Image)hitpic,x*40+8,y*24+30+43,null);
			Rect dest= new Rect((int)(40*scale*x), (int)(43*scale+24*scale*y),(int)( 25*scale+40*scale*x),(int)(47*scale+43*scale+24*scale*y));
			canvas.drawBitmap(hitpic, null, dest, null);
			return;
		}
		
		Rect dest= new Rect((int)(40*scale*x), (int)(43*scale+24*scale*y),(int)( 36*scale+40*scale*x),(int)(44*scale+43*scale+24*scale*y));
		if(currAttack==null){
			canvas.drawBitmap(defaultbm, null, dest, null);
		}else{
			//canvas.drawBitmap(busterbm, null, dest, null);
			currAttack.draw(canvas, info);
		}
	}

	public void doAttack(BattleInfo info,long l){
		if(mystatus==Status.SHOCKED&&statuslength>=0){
			return;
		}
		if(currAttack==null){
			currAttack=new Buster(this.x,this.y,l);
		}
	}
	
	public void updateStatus(BattleInfo battleInfo, long numTimer) {
		if(numTimer%10==0 && statuslength>=0){
			statuslength-=1;
			if(statuslength<=0){
				statuslength=-1;
				mystatus=Status.NORMAL;
			}
		}
		if(currAttack!=null){
			currAttack.doAttack(info, numTimer);
		}
		if(currAttack!=null&&currAttack.checkDead()){
			currAttack=null;
		}
		
	}
}
