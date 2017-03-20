package com.example.mmbndemo.battle.enemies;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.mmbndemo.AndroidTutorial;
import com.example.mmbndemo.BitmapGetter;
import com.example.mmbndemo.R;
import com.example.mmbndemo.battle.BattleEnemy;
import com.example.mmbndemo.battle.BattleInfo;
import com.example.mmbndemo.battle.HPFactory;
import com.example.mmbndemo.battle.actionobjects.ShockWaveAction;


public class Metaur extends BattleEnemy{
	private enum Phase{
		CHARGING, ATTACKING,HIDING, SHOULDWAKEUP, MOVING
	};
	private Phase phase=Phase.HIDING;
	//int attackphase;
	//protected boolean hiding;
	//protected boolean shouldWakeUp;
	protected Bitmap defaultbm;
	protected Bitmap attimage;
	//protected Bitmap shockwavebm;
	/*protected BufferedImage attimage3;
	protected BufferedImage attimage4;
	protected BufferedImage attimage5;*/
	
	public Metaur(int x, int y, int hp){
		
		this.x=x;
		this.y=y;
		this.hp=hp;
		//attackphase=-1;
		//hiding=true;
		
		this.canBeHit=true;
		//this.shouldWakeUp=false;
		loadImages();
		//hp=30;
		//loadImages
	}
	public void init(BattleInfo info){
		List<BattleEnemy> enemies=info.getBattleEnemies();
		boolean anynothiding=false;
		for(int i=0; i<enemies.size();i++){
			if(enemies.get(i).getClass()==Metaur.class  && enemies.get(i)!=this){
				Metaur metaur=(Metaur)enemies.get(i);
				if(!metaur.checkHiding()){
					anynothiding=true;
				}
			}
		}
		if(!anynothiding){ //if they're all hiding i should not hide
			this.unHide();
		}
	}
	public void update(BattleInfo info){
		//should be called to update status
		
		
	}
	public void doMove(BattleInfo info, long numTimer){
		// if a second passed
		if(phase==Phase.HIDING ){
			return;
		}
		if(numTimer%5==0){
			if(phase==Phase.CHARGING){
				phase=Phase.ATTACKING;
			}else if(phase==Phase.ATTACKING){
				doAttack(info);
			}
		}
		
		if(numTimer%10==0){
			if(phase==Phase.MOVING){
				moveVerticalToPlayer(info);
			}else if(phase==Phase.SHOULDWAKEUP){
				//doAttack(info);
				this.phase=Phase.MOVING;
			}
		}
		
	}
	private void moveVerticalToPlayer(BattleInfo info){
		int formerTile=info.getTileOf(this);
		int panelnum=info.toTileNumber(x, y);
		int fighterY=info.getFighter().getY();
		if(fighterY>y&&!info.isFighters(info.toTileNumber(x, y+1)) && info.getOccupier(info.toTileNumber(x, y+1))==null){
			panelnum=info.toTileNumber(x, y+1);
		}else if(fighterY<y&&!info.isFighters(info.toTileNumber(x, y-1)) && info.getOccupier(info.toTileNumber(x, y-1))==null){
			panelnum=info.toTileNumber(x, y-1);
		}else{
			this.phase=Phase.CHARGING;
			return;
		}

		setX(info.tileToX(panelnum));
		setY(info.tileToY(panelnum));
		info.setOccupier(null, formerTile);
		info.setOccupier(this, panelnum);
		if(fighterY==y){
			this.phase=Phase.CHARGING;
		}
		//
	}
	private boolean wakeUpNext(BattleInfo info){
		List<BattleEnemy> enemies=info.getBattleEnemies();
		boolean anyMetaurs=false;
		List<Metaur> metaurs=new ArrayList<Metaur>();
		for(int i=0; i<enemies.size();i++){
			if(enemies.get(i).getClass()==Metaur.class && enemies.get(i)!=this &&!enemies.get(i).checkDead()){
				anyMetaurs=true;
				metaurs.add((Metaur)enemies.get(i));
			}
		}
		if(!anyMetaurs){ //if they're all dead
			return false;
		}
		metaurs.get((int)(Math.random()*metaurs.size())).unHide();
		return true;
	}
	private void doAttack(BattleInfo info){
		//System.out.println("in attackphase "+attackphase);
		//if(this.phase==Phase.CHARGING){
			ShockWaveAction bao=new ShockWaveAction(x-1,y,10,info);
			info.addActionObject(bao);
			boolean didWake=wakeUpNext(info);
			if(didWake){
				hide();
				return;
			}
		//}
		this.phase=Phase.SHOULDWAKEUP;
		
	}
	public void loadImages() { //Context context
		/*defaultbm=BitmapFactory.decodeResource(context.getResources(), R.drawable.metaur);
		attimage=BitmapFactory.decodeResource(context.getResources(), R.drawable.metauranim4);
		shockwavebm=BitmapFactory.decodeResource(context.getResources(), R.drawable.shockwave);*/
		defaultbm=BitmapGetter.getBitmap(R.drawable.metaur);
		attimage=BitmapGetter.getBitmap(R.drawable.metauranim4);
		//attimage=BitmapGetter.getBitmap(R.drawable.metauranim4);
    } 
	public void draw(Canvas c,BattleInfo info){
		double scale=AndroidTutorial.scale;
		
		if(phase!=Phase.ATTACKING){
			//g.drawImage((Image)defaultimage,x*40+8+5,y*24+30+70,null);
			Rect dest= new Rect((int)(40*scale*x+5*scale), (int)(70*scale+24*scale*y),(int)( 22*scale+5*scale+40*scale*x),(int)(22*scale+70*scale+24*scale*y));
			c.drawBitmap(defaultbm, null, dest, null);
			
		}else{
			//36 39
			Rect dest= new Rect((int)(40*scale*x+5*scale), (int)(48*scale+24*scale*y),(int)( 36*scale+5*scale+40*scale*x),(int)(39*scale+48*scale+24*scale*y));
			c.drawBitmap(attimage, null, dest, null);
			//g.drawImage((Image)attimage4,x*40+8+5,y*24+30+48,null);
		}
		//g.setColor(Color.black);
		//g.drawString(""+hp, x*40+8+7, y*24+30+103);
		drawHP(c);
	}
	
	
	public void hide(){
		phase=Phase.HIDING;
		//this.canBeHit=false;
	}
	public void unHide(){
		//this.hiding=false;
		//this.canBeHit=true;
		phase=Phase.SHOULDWAKEUP;
	}
	public boolean checkHiding(){
		return (phase==Phase.HIDING);
	}
	public void damage(BattleInfo info,int amt){
		if(this.canBeHit){
			this.reduceHP(amt);
		}
		if(checkDead()){
			this.wakeUpNext(info);
		}
	}
	private void drawHP(Canvas c){
		double scale=AndroidTutorial.scale;
		if(hp>9){
			
			int dig1=HPFactory.getNthDigit(hp,10,1);
			int dig2=HPFactory.getNthDigit(hp,10,2);
			Rect dest= new Rect((int)(40*scale*x+5*scale), (int)(87*scale+24*scale*y),(int)( 8*scale+5*scale+40*scale*x),(int)(13*scale+87*scale+24*scale*y));
			c.drawBitmap(HPFactory.getInstance().getHPBitmap(dig2), null, dest, null);
			Rect dest2= new Rect((int)(8*scale+40*scale*x+5*scale), (int)(87*scale+24*scale*y),(int)( 16*scale+5*scale+40*scale*x),(int)(13*scale+87*scale+24*scale*y));
			c.drawBitmap(HPFactory.getInstance().getHPBitmap(dig1), null, dest2, null);
			
		}else if(hp>0){
			Rect dest= new Rect((int)(40*scale*x+5*scale), (int)(87*scale+24*scale*y),(int)( 8*scale+5*scale+40*scale*x),(int)(13*scale+87*scale+24*scale*y));
			c.drawBitmap(HPFactory.getInstance().getHPBitmap(hp), null, dest, null);
		}
	}
	
}