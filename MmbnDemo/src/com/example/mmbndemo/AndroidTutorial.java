package com.example.mmbndemo;


import com.example.mmbndemo.battle.BattleCharacter;
import com.example.mmbndemo.battle.Grid;
import com.example.mmbndemo.battle.HPFactory;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;

public class AndroidTutorial extends Activity {
	//AnimatedSprite animation = new AnimatedSprite();
	private BattleInterface binterface;
	public static double scale;
	
	private BattleCharacter megaman;
	private AndroidTutorialPanel panel;
	//private boolean stopped=false;

	/** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	HPFactory.initialize(this);
    	BitmapGetter.initialize(this);
    	Display display = getWindowManager().getDefaultDisplay();
    	Point size = new Point();
    	display.getSize(size);
    	int width = size.x;
    	//int height = size.y;
    	double xscale=(double)width/240;
    	scale=xscale;
    	//double yscale=(double)height/160;
    	 //grid= new Grid();
    	 binterface=new BattleInterface(this);
        super.onCreate(savedInstanceState);
        panel=new AndroidTutorialPanel(this);
        setContentView(panel);
    }
    @Override
    public void onPause(){
        super.onPause();
        panel.setThreadRunning(false);
        finish();
    }
    public BattleInterface getBinterface() {
		return binterface;
	}

	public void setBinterface(BattleInterface binterface) {
		this.binterface = binterface;
	}

	/*public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}*/

	public BattleCharacter getMegaman() {
		return megaman;
	}

	public void setMegaman(BattleCharacter megaman) {
		this.megaman = megaman;
	}

    
    
    
    class AndroidTutorialPanel extends DrawablePanel {
    	AndroidTutorial context;
    	long lastupdate;
    	long numTimer;
    	
		public AndroidTutorialPanel(AndroidTutorial context) {
			super(context);
			this.context=context;
		}

		@Override
		public void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			//AndroidTutorial.this.animation.draw(canvas);
			//grid.draw(canvas);
			binterface.draw(canvas);
		}
		
		@Override
		public void onInitalize() {
			/*
			grid.Initialize(BitmapFactory.decodeResource(getResources(), R.drawable.topbluetile),
					BitmapFactory.decodeResource(getResources(), R.drawable.midbluetile),
					BitmapFactory.decodeResource(getResources(), R.drawable.lowbluetile),
					BitmapFactory.decodeResource(getResources(), R.drawable.topredtile),
					BitmapFactory.decodeResource(getResources(), R.drawable.midredtile),
					BitmapFactory.decodeResource(getResources(), R.drawable.lowredtile));
			//binterface.getHero().Initialize(BitmapFactory.decodeResource(getResources(), R.drawable.mmnorm));
			*/
			}

		@Override
		public void onUpdate(long gameTime) {
			//AndroidTutorial.this.animation.Update(gameTime);
			binterface.update(gameTime);
			
			
			
		}
		@Override
		  public boolean onTouchEvent(MotionEvent event) {
		   context.getBinterface().manageTouchEvent(event);
		   return true; //processed
		  }
    }
    
}
