package com.mycompany.myapp;




import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.mycompany.myapp.battle.BattleCharacter;
import com.mycompany.myapp.battle.BattleInfo;
import com.mycompany.myapp.battle.enemies.Metaur;


public class BattleInterface {
	private int startx=10;
	private int starty=200;
	
	private int buttx=180;
	private int butty=220;
	
	private int gagex=180;
	private int gagey=180;
	
	private enum Mode{
		BATTLE,PAUSE,GAGE
	};
	private Mode mode=Mode.BATTLE;
	
	private BattleInfo info;

	private long lastupdate;

	private long numTimer;

	private boolean stopped;
	
	public long getLasttime() {
		return numTimer;
	}
	public BattleInterface(Context context){
		info=new BattleInfo(this,context);
		BattleCharacter fighter=new BattleCharacter(info);
		
		fighter.setHP(100);
		info.setFighter(fighter);
		
		Metaur be=new Metaur(4,1,30);
		info.addEnemy(be, info.getTileOf(be));
		Metaur be2=new Metaur(3,1,30);
		info.addEnemy(be2, info.getTileOf(be2));
		info.init();
		
		//add enemies here
		//Bunny be=new Bunny(4,1,40);
		//info.addEnemy(be, info.getTileOf(be));
		//info.init();
		//info.setGaged(true);
		//gaged=true;
		
	}

	public BattleCharacter getHero(){
		return info.getFighter();
	}
	public void manageTouchEvent(MotionEvent event){
		if(mode==Mode.BATTLE){
			battleTouch(event);
		}
		info.updateBattle(numTimer);
	}
	
	public void battleTouch(MotionEvent event){
		double scale=AndroidTutorial.scale;
		float touched_x = event.getX();
		float touched_y = event.getY();

		boolean attacked=false;
		boolean moved=false;
		boolean hitgage=false;
		int destx=-1;
		int desty=-1;
		if(touched_x<(scale*(startx+30))){
			destx=0;
		}else if(touched_x>(scale*(startx+30)) && touched_x<(scale*(startx+60))){
			destx=1;
		}else{
			destx=2;
		}
		if(touched_y<(scale*(starty+30))){
			desty=0;
		}else if(touched_y>(scale*(starty+30)) && touched_y<(scale*(starty+60))){
			desty=1;
		}else{
			desty=2;
		}
		float touched2_x = -1;
		float touched2_y = -1;
		if(event.getPointerCount()>1){
			int mActivePointerId = event.getPointerId(1);
			int pointerIndex = event.findPointerIndex(mActivePointerId);

			touched2_x=event.getX(pointerIndex);
			touched2_y=event.getY(pointerIndex);
		}


		if((touched2_x>scale*buttx&&touched2_y>scale*butty &&touched2_x<scale*(buttx+50) && touched2_y<scale*(butty+50))||(touched_x>scale*buttx&&touched_y>scale*butty &&touched_x<scale*(buttx+50) && touched_y<scale*(butty+50))){
			attacked=true;
		}

		if((event.getAction()==MotionEvent.ACTION_POINTER_2_UP&&(touched2_x>scale*gagex&&touched2_y>scale*gagey &&touched2_x<scale*(gagex+50) && touched2_y<scale*(gagey+40)))||(event.getAction()==MotionEvent.ACTION_UP&&touched_x>scale*gagex&&touched_y>scale*gagey &&touched_x<scale*(gagex+50) && touched_y<scale*(gagey+50))){
			hitgage=true;
		}

		if((touched_x>scale*startx)&&(touched_y>scale*starty) &&(touched_x<scale*(startx+90)) && (touched_y<scale*(starty+90))){
			moved=true;
		}
		if(attacked){
			info.getFighter().doAttack(info, this.getLasttime());
		}
		if(moved){
			info.getFighter().moveTo(destx,desty);
		}
		if(hitgage&info.checkGage()){
			stopped=!stopped;
			info.setGaged(stopped);
			//stopped=true;
		}
	}
	
	public void update(long gameTime){
		if(gameTime>100+lastupdate &!stopped){
			numTimer++;
			lastupdate=gameTime;
			info.update(numTimer);
		}
		//lasttime=numTimer;
		//info.update(numTimer);
		//lasttime=gameTime;
		
	}
	public void draw(Canvas c){
		double scale=AndroidTutorial.scale;
		Paint p=new Paint();
		p.setColor(Color.GRAY);
		//73*xscale+33*xscale=112*xscale
		// 40-130, 110-200, 
		//x 70,100 y 140 170
		
		Rect r=new Rect((int)(startx*scale),(int)(starty*scale),(int)((startx+90)*scale),(int)((starty+90)*scale));
		c.drawRect(r, p);
		
		
		p.setColor(Color.BLACK);
		c.drawLine((int)((startx+30)*scale), (int)(starty*scale), (int)((startx+30)*scale), (int)((starty+90)*scale), p);
		c.drawLine((int)((startx+60)*scale), (int)(starty*scale), (int)((startx+60)*scale), (int)((starty+90)*scale), p);
		c.drawLine((int)(startx*scale), (int)((starty+30)*scale), (int)((startx+90)*scale), (int)((starty+30)*scale), p);
		c.drawLine((int)((startx)*scale), (int)((starty+60)*scale), (int)((startx+90)*scale), (int)((starty+60)*scale), p);
	
		p.setColor(Color.RED);
		Rect attackrect=new Rect((int)(buttx*scale),(int)(butty*scale),(int)((buttx+50)*scale),(int)((butty+50)*scale));
		c.drawRect(attackrect, p);
		
		p.setColor(Color.GREEN);
		Rect gagerect=new Rect((int)(gagex*scale),(int)(gagey*scale),(int)((gagex+50)*scale),(int)((gagey+30)*scale));
		c.drawRect(gagerect, p);
	
		/*if(mode==Mode.BATTLE){
			//drawmenu
		}*/
		
		info.draw(c);
	}
}
