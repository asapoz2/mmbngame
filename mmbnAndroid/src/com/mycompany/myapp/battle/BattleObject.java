package com.mycompany.myapp.battle;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.*;


public class BattleObject {
	protected int x;
	protected int y;
	protected int hp;
	protected boolean shouldDie;
	
	
	public BattleObject(){
		shouldDie=false;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setY(int y){
		this.y=y;
	}
	public void setX(int x){
		this.x=x;
	}
	public int getHP(){
		return hp;
	}
	public void setHP(int hp){
		this.hp=hp;
	}
	public void reduceHP(int amt){
		this.hp-=amt;
	}
	public boolean checkDead(){
		if(hp<=0){
			shouldDie=true;
		}
		return shouldDie;
	}
	public void draw(Canvas c, BattleInfo info){
		
	}
	public void loadImages(Context context){
		
	}
	public void damage(BattleInfo info,int amt) {
		// TODO Auto-generated method stub
		reduceHP(amt);
		
	} 
}
