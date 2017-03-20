package com.mycompany.myapp.battle.enemies;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.mycompany.myapp.AndroidTutorial;
import com.mycompany.myapp.BitmapGetter;
import com.mycompany.myapp.R;
import com.mycompany.myapp.battle.BattleEnemy;
import com.mycompany.myapp.battle.BattleInfo;
import com.mycompany.myapp.battle.HPFactory;
import com.mycompany.myapp.battle.actionobjects.ShockWaveAction;


public class Metaur extends BattleEnemy{
	int attackphase;
	protected boolean hiding;
	protected boolean shouldWakeUp;
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
		attackphase=-1;
		hiding=true;
		this.canBeHit=true;
		this.shouldWakeUp=false;
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
		if(hiding){
			return;
		}
		if(numTimer%10==0){
			if(attackphase<0){
				moveVerticalToPlayer(info);
			}else{
				doAttack(info);
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
			attackphase=0;
			return;
		}

		setX(info.tileToX(panelnum));
		setY(info.tileToY(panelnum));
		info.setOccupier(null, formerTile);
		info.setOccupier(this, panelnum);
		if(fighterY==y){
			attackphase=0;
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
		if(attackphase==0){
			ShockWaveAction bao=new ShockWaveAction(x-1,y,10);
			info.addActionObject(bao);
			boolean didWake=wakeUpNext(info);
			if(didWake){
				hide();
			}
		}
		attackphase--;
		
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
		
		if(attackphase==-1){
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
		this.hiding=true;
		//this.canBeHit=false;
	}
	public void unHide(){
		this.hiding=false;
		//this.canBeHit=true;
	}
	public boolean checkHiding(){
		return hiding;
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
			
		}else{
			Rect dest= new Rect((int)(40*scale*x+5*scale), (int)(87*scale+24*scale*y),(int)( 8*scale+5*scale+40*scale*x),(int)(13*scale+87*scale+24*scale*y));
			c.drawBitmap(HPFactory.getInstance().getHPBitmap(hp), null, dest, null);
		}
	}
	
}
