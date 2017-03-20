package com.example.mmbndemo.battle;

import android.graphics.Canvas;


public interface BattleActionObject{
	void doAction(BattleInfo info,long numTimes);
	boolean checkDead();
	void draw(Canvas c, BattleInfo info);
}
