package com.mycompany.myapp.battle;

import android.content.Context;
import android.graphics.*;

public class BattleEnemy extends BattleObject {
	protected Bitmap defaultimage;
	protected boolean canBeHit=true;
	private long lasttime;
	public BattleEnemy(){
		this.setX(4);
		this.setY(1);

	}
	public BattleEnemy(int x, int y, int hp,Context context){
		this.setX(x);
		this.setY(y);
		loadImages(context);

	}
	public void init(BattleInfo info){
		//should be called at start of battle
	}
	public void update(BattleInfo info){
		//should be called to update status
	}
	public void doMove(BattleInfo info, long numTimes){

		// if a second passed
		lasttime=numTimes;
		if(numTimes>lasttime+1){
			//moveSelf(info);
			moveVerticalToPlayer(info);
		}
		
	}
	/*private void moveSelf(BattleInfo info){
		List<Integer> spaces= new ArrayList<Integer>();
		for(int i=0;i<18;i++){
			if(!info.isFighters(i)){
				spaces.add(i);
			}
		}
		// integer between [3,7]
        //int r1 = (int) (Math.random()*5)+3;
		int whichpanel = (int) (Math.random()*spaces.size());
		int panelnum=spaces.get(whichpanel);
		setX(info.tileToX(panelnum));
		setY(info.tileToY(panelnum));
		//
	}*/
	
	public boolean canBeHit(){
		return canBeHit;
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
			return;
		}

		setX(info.tileToX(panelnum));
		setY(info.tileToY(panelnum));
		info.setOccupier(null, formerTile);
		info.setOccupier(this, panelnum);
		//
	}
	public void draw(Canvas c,BattleInfo info){
		
	}
	public void loadImages(Context context) {

        
    } 
	public void damage(BattleInfo info,int amt){
		if(this.canBeHit){
			this.reduceHP(amt);
		}
	}
	
}
