package com.example.mmbndemo.battle;


import java.util.ArrayList;


import com.example.mmbndemo.AndroidTutorial;
import com.example.mmbndemo.BitmapGetter;
import com.example.mmbndemo.R;
import com.example.mmbndemo.battle.BattleStatus.Status;
import com.example.mmbndemo.battle.ChipAttackFactory.ChipType;
import com.example.mmbndemo.battle.attacks.Buster;

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
	private ArrayList<BattleFolderChip> chipstack;
	
	public BattleCharacter(BattleInfo info){
		x=1;
		y=1;
		this.info=info;
		
		chipstack=new ArrayList<BattleFolderChip>();
		//chipstack.add(new BattleFolderChip(ChipType.Airshot, '*'));
		//chipstack.add(new BattleFolderChip(ChipType.Airshot, '*'));
		//chipstack.add(new BattleFolderChip(ChipType.LongSword, '*'));
		//chipstack.add(new BattleFolderChip(ChipType.LongSword, '*'));
		chipstack.add(new BattleFolderChip(ChipType.ShockWave, '*'));
		chipstack.add(new BattleFolderChip(ChipType.ShockWave, '*'));
		chipstack.add(new BattleFolderChip(ChipType.ShockWave, '*'));
		
		
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
	
	public boolean moveRight(BattleInfo info){
		if(mystatus==Status.SHOCKED&&statuslength>=0){
			return false;
		}
		int formerTile=info.getTileOf(this);
		if( (x+1>5) || !info.isFighters(info.toTileNumber(x+1, y))){
			return false;
		}
		this.x++;
		info.setOccupier(null, formerTile);
		info.setOccupier(this, info.getTileOf(this));
		return true;
	}
	public boolean moveLeft(BattleInfo info){
		if(mystatus==Status.SHOCKED&&statuslength>=0){
			return false;
		}
		int formerTile=info.getTileOf(this);
		if( (x-1<0) || !info.isFighters(info.toTileNumber(x-1, y))){
			return false;
		}
		this.x--;
		info.setOccupier(null, formerTile);
		info.setOccupier(this, info.getTileOf(this));
		return true;
	}
	public boolean moveUp(BattleInfo info){
		if(mystatus==Status.SHOCKED&&statuslength>=0){
			return false;
		}
		int formerTile=info.getTileOf(this);
		if( (y-1<0) || !info.isFighters(info.toTileNumber(x, y-1))){
			return false;
		}
		this.y--;
		info.setOccupier(null, formerTile);
		info.setOccupier(this, info.getTileOf(this));
		return true;
	}
	public boolean moveDown(BattleInfo info){
		if(mystatus==Status.SHOCKED&&statuslength>=0){
			return false;
		}
		int formerTile=info.getTileOf(this);
		if( (y+1>2) || !info.isFighters(info.toTileNumber(x, y+1))){
			return false;
		}
		
		this.y++;
		info.setOccupier(null, formerTile);
		info.setOccupier(this, info.getTileOf(this));
		return true;
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
	
	public void doNextChip(BattleInfo info,long numTimer){
		if((mystatus==Status.SHOCKED&&statuslength>=0)||currAttack!=null||chipstack.isEmpty()){
			return;
		}

		BattleFolderChip nextChip=chipstack.remove(0);
		currAttack=ChipAttackFactory.getAttack(nextChip.getChiptype(),this.x, this.y, numTimer);
		
		
	}
	
	public void setChipStack(ArrayList<BattleFolderChip> newstack){
		//currChip=chip;
		chipstack.clear();
		chipstack.addAll(newstack);
	}
	
	public void updateStatus(BattleInfo battleInfo, long numTimer) {
		if(statuslength>=0){
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
