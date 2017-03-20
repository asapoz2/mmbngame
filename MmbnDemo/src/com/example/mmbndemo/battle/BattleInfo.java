package com.example.mmbndemo.battle;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.mmbndemo.AndroidTutorial;
import com.example.mmbndemo.BattleInterface;


public class BattleInfo {
	public final int horiz=6;
	public final int vertic=3;
	
	private boolean[] whostiles;
	private BattleObject[] occupiers;
	private TileFactory tf;
	public List<BattleEnemy> enemies;
	private List<BattleActionObject> actions;
	
	private BattleInterface parent;
	private BattleCharacter fighter;
	private Context context;
	private BattleGauge gage;
	boolean gaged=false;
	private Grid grid;
	
	public BattleInfo(BattleInterface battleInterface, Context context) {
		this.context=context;
		parent=battleInterface;
		tf=new TileFactory();
		grid=new Grid();
		occupiers=new BattleObject[18];
		for(int i=0;i<18;i++){
			occupiers[i]=null;
		}
		actions=new ArrayList<BattleActionObject>();
		enemies=new ArrayList<BattleEnemy>();
		whostiles=new boolean[18];
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				whostiles[toTileNumber(i,j)]=true;
			}
		}
		for(int i=3;i<6;i++){
			for(int j=0;j<3;j++){
				whostiles[toTileNumber(i,j)]=false;
			}
		}
		gage=new BattleGauge();
		gage.restartGauge(0);
		
		
	}
	public int getTileOf(BattleObject bo){
		return tf.getTileOf(bo);
	}
	public int toTileNumber(int x,int y){
		return tf.toTileNumber(x, y);
	}
	public int tileToX(int tilenum){
		return tf.tileToX(tilenum);
	}
	
	public int tileToY(int tilenum){
		return tf.tileToY(tilenum);
	}
	public void setOccupier(BattleObject bo, int tilenum){
		occupiers[tilenum]=bo;
	}
	public BattleObject getOccupier(int tilenum){
		return occupiers[tilenum];
	}
	
	public Context getContext(){
		return context;
	}
	
	public BattleCharacter getFighter() {
		return fighter;
	}
	public boolean isFighters(int tilenum){
		return whostiles[tilenum];
	}
	public void doAct(long numTimes){
		this.doActions(numTimes);
		this.updateBattle(numTimes);
		fighter.updateStatus(this,numTimes);
		this.doEnemyAction(numTimes);
		
	}
	public void doActions(long numTimes){
		for(BattleActionObject bao:actions){
			bao.doAction(this, numTimes);
		}
	}
	public void doEnemyAction(long numTimes){
		for(BattleEnemy be:enemies){
			be.doMove(this, numTimes);
		}
	}
	
	public void update(long numTimer){
		doAct(numTimer);
		
	}
	public long getLasttime(){
		return parent.getLasttime();
	}
	
	public void setFighter(BattleCharacter fighter) {
		this.fighter = fighter;
		fighter.moveTo(fighter.getX(), fighter.getY());
		
	}
	
	public boolean updateBattle(long numTimes){
		gage.updateGuage(numTimes);
		if(fighter.checkDead()){
		//	fighter.setHP(100);
		}
		List<Integer> toKill=new ArrayList<Integer>();
		for(int i=0;i<enemies.size();i++){
			if(enemies.get(i).checkDead()){
				toKill.add(i);
			}
		}
		
		for(int i=0;i<toKill.size();i++){
			BattleEnemy be=enemies.get(toKill.get(i));
			int hisTile=getTileOf(be);
			occupiers[hisTile]=null;
			enemies.remove(toKill.get(i).intValue());
			//enemies.
		}
		List<BattleActionObject> toRemove=new ArrayList<BattleActionObject>();
		for(int i=0;i<actions.size();i++){
			if(actions.get(i).checkDead()){
				//toKill.add(i);
				toRemove.add(actions.get(i));
			}
		}
		
		for(int i=0;i<toRemove.size();i++){
			//BattleActionObject bao=actions.get(toKill.get(i));
			//actions.remove(toKill.get(i).intValue());
			actions.remove(toRemove.get(i));
			//enemies.
		}
		for(BattleEnemy be:enemies){
			be.update(this);
		}
		return enemies.isEmpty();
		//check deaths
	}
	
	public List<BattleEnemy> getBattleEnemies(){
		return enemies;
	}
	public void init(){
		//fighter.init(this);
		initEnemies();
	}
	public void initEnemies(){
		for(BattleEnemy be:enemies){
			be.init(this); 
		}
	}
	public void addEnemy(BattleEnemy be,int tilenum){
		be.setX(tileToX(tilenum));
		be.setY(tileToY(tilenum));
		enemies.add(be);
		occupiers[getTileOf(be)]=be;
	}
	public void addActionObject(BattleActionObject bao){
		//BattleActionObject copy=bao;
		actions.add(bao);
	}
	
	public boolean checkGage(){
		return gage.checkPause();
	}
	public void setGaged(boolean gaged){
		this.gaged=gaged;
		if(gaged){
			//bmenu.called();
		}
	}
	public void restartGage(long numTimer){
		gage.restartGauge(numTimer);
	}
	
	
	public void draw(Canvas c) {
		grid.draw(c);
		for(BattleEnemy be:enemies){
			be.draw(c,this);
		}
		for(BattleActionObject bao:actions){
			bao.draw(c,this);
		}
		fighter.draw(c);
		drawHP(c);
		gage.draw(c,this);
		
	}
	private void drawHP(Canvas c){
		double scale=AndroidTutorial.scale;
		int hp=fighter.getHP();
		double startx=5*scale;
		double starty=1*scale;
		
		if(hp>99){
			int dig1=HPFactory.getNthDigit(hp,10,1);
			int dig2=HPFactory.getNthDigit(hp,10,2);
			int dig3=HPFactory.getNthDigit(hp,10,3);
			Rect dest= new Rect((int)(startx), (int)(starty),(int)( startx+8*scale),(int)(starty+13*scale));
			c.drawBitmap(HPFactory.getInstance().getHPBitmap(dig3), null, dest, null);
			Rect dest2= new Rect((int)(startx+8*scale), (int)(starty),(int)( startx+16*scale),(int)(starty+13*scale));
			c.drawBitmap(HPFactory.getInstance().getHPBitmap(dig2), null, dest2, null);
			Rect dest3= new Rect((int)(startx+16*scale), (int)(starty),(int)( startx+24*scale),(int)(starty+13*scale));
			c.drawBitmap(HPFactory.getInstance().getHPBitmap(dig1), null, dest3, null);
			
		}else if(hp>9){
			
			int dig1=HPFactory.getNthDigit(hp,10,1);
			int dig2=HPFactory.getNthDigit(hp,10,2);
			Rect dest= new Rect((int)(startx), (int)(starty),(int)( startx+8*scale),(int)(starty+13*scale));
			c.drawBitmap(HPFactory.getInstance().getHPBitmap(dig2), null, dest, null);
			Rect dest2= new Rect((int)(startx+8*scale), (int)(starty),(int)( startx+16*scale),(int)(starty+13*scale));
			c.drawBitmap(HPFactory.getInstance().getHPBitmap(dig1), null, dest2, null);

			
		}else if(hp>0){
			Rect dest= new Rect((int)(startx), (int)(starty),(int)( startx+8*scale),(int)(starty+13*scale));
			c.drawBitmap(HPFactory.getInstance().getHPBitmap(hp), null, dest, null);
		}
	}
	
}
