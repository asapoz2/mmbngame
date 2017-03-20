package com.example.mmbndemo;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.mmbndemo.battle.BattleBackground;
import com.example.mmbndemo.battle.BattleCharacter;
import com.example.mmbndemo.battle.BattleInfo;
import com.example.mmbndemo.battle.BattleMenu;
import com.example.mmbndemo.battle.enemies.Metaur;
import com.example.mmbndemo.ui.DraggablePad;
import com.example.mmbndemo.ui.DraggablePad.Direction;
import com.example.mmbndemo.ui.InterfaceButton;



public class BattleInterface {

	
	private InterfaceButton gageButt;
	private InterfaceButton chipButt;
	private InterfaceButton attackButt;
	private InterfaceButton pauseButt;
	
	private DraggablePad pad;
	
	
	private enum Mode{
		BATTLE,PAUSE,GAGE
	};
	private Mode mode=Mode.GAGE;
	
	private BattleInfo info;
	private BattleMenu bmenu;

	private long lastupdate;

	private long numTimer;
	private long realTime;

	private boolean stopped;
	
	
	public long getLasttime() {
		return numTimer;
	}
	public BattleInterface(Context context){
		gageButt=new InterfaceButton(180,180,50,40);
		chipButt=new InterfaceButton(130,220,50,50);
		attackButt=new InterfaceButton(180,220,50,50);
		pauseButt=new InterfaceButton(180,290,50,50);
		double scale=AndroidTutorial.scale;
		pad=new DraggablePad(5*scale,220*scale,70*scale,70*scale,50,5*scale);
		
		info=new BattleInfo(this,context);
		BattleCharacter fighter=new BattleCharacter(info);
		bmenu=new BattleMenu(info);
		
		fighter.setHP(100);
		info.setFighter(fighter);
		
		Metaur be=new Metaur(4,1,30);
		info.addEnemy(be, info.getTileOf(be));
		Metaur be2=new Metaur(3,1,30);
		info.addEnemy(be2, info.getTileOf(be2));
		info.init();
		
		
		stopped=true;
		info.setGaged(true);
		mode=Mode.GAGE;
		bmenu.called();
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
		}else if(mode==Mode.GAGE){
			/*if(event.getAction()==MotionEvent.ACTION_DOWN){
				mode=Mode.BATTLE;
			}*/
			if(bmenu.handleMotionEvent(event)){
				mode=Mode.BATTLE;
				stopped=!stopped;
				info.setGaged(stopped);
				info.restartGage(numTimer);
			}
		}else if(mode==Mode.PAUSE){
			if(pauseButt.wasClickedOn(event)){
				mode=Mode.BATTLE;
				stopped=!stopped;
			}
		}
		info.updateBattle(numTimer);
	}
	
	public void battleTouch(MotionEvent event){
		/*double scale=AndroidTutorial.scale;
		float touched_x = event.getX();
		float touched_y = event.getY();*/

		boolean attacked=false;
		//boolean moved=false;
		boolean hitgage=false;
		boolean chipped=false;
		boolean hitpause=false;
		/*int destx=-1;
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
		}*/
		Direction paddir=pad.handleMotionEvent(event,realTime);
		if(paddir==Direction.LEFT){
			info.getFighter().moveLeft(info);
		}else if(paddir==Direction.RIGHT){
			info.getFighter().moveRight(info);
		}else if(paddir==Direction.UP){
			info.getFighter().moveUp(info);
		}else if(paddir==Direction.DOWN){
			info.getFighter().moveDown(info);
		}
		

		attacked=attackButt.wasClickedOn(event);

		chipped=chipButt.wasClickedOn(event);

		hitgage=gageButt.wasClickedOn(event);
		
		hitpause=pauseButt.wasClickedOn(event);
		
		/*if((touched_x>scale*startx)&&(touched_y>scale*starty) &&(touched_x<scale*(startx+90)) && (touched_y<scale*(starty+90))){
			moved=true;
		}*/
		if(attacked){
			info.getFighter().doAttack(info, this.getLasttime());
		}
		/*if(moved){
			info.getFighter().moveTo(destx,desty);
		}*/
		
		if(chipped){
			info.getFighter().doNextChip(info, this.getLasttime());
		}
		
		if(hitpause){
			stopped=!stopped;
			mode=Mode.PAUSE;
		}
		
		if(hitgage&info.checkGage()){
			stopped=!stopped;
			info.setGaged(stopped);
			mode=Mode.GAGE;
			bmenu.called();
			//stopped=true;
		}
	}
	
	public void update(long gameTime){
		realTime=gameTime;
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
		BattleBackground.getInstance().draw(c,numTimer);
		info.draw(c);
		if(mode==Mode.BATTLE){
			drawBattleInterface(c);
			
		}else if(mode==Mode.GAGE){
			bmenu.draw(c);
		}else if(mode==Mode.PAUSE){
			double scale=AndroidTutorial.scale;
			c.drawBitmap(BitmapGetter.getBitmap(R.drawable.pause),null,new Rect((int)(100*scale),(int)(50*scale),(int)((100+53)*scale),(int)(scale*(50+13))),null);
			
		}
		
		
		
	}
	public void drawBattleInterface(Canvas c){
		//double scale=AndroidTutorial.scale;
		
		/*p.setColor(Color.GRAY);
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
		*/
		
		Paint p=new Paint();
		p.setColor(Color.RED);
		attackButt.draw(c, p);
		p.setColor(Color.GREEN);
		gageButt.draw(c,p);
		p.setColor(Color.YELLOW);
		chipButt.draw(c,p);
		p.setColor(Color.WHITE);
		pauseButt.draw(c,p);
		pad.draw(c);
	}
}

