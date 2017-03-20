package com.example.mmbndemo.battle;


import com.example.mmbndemo.battle.attacks.Airshot;
import com.example.mmbndemo.battle.attacks.LongSword;
import com.example.mmbndemo.battle.attacks.ShockWaveMM;


public class ChipAttackFactory {
	public static enum ChipType {
	    None,LongSword,Airshot,ShockWave
	}
	public static BattleAttack getAttack(ChipType chiptype,int x, int y, long numTimer){
		if(chiptype==ChipType.None){
			return null; //shouldn't be called
		}else if(chiptype==ChipType.LongSword){
			return new LongSword(x,y, numTimer);
		}else if(chiptype==ChipType.Airshot){
			return new Airshot(x,y, numTimer);
		}else if(chiptype==ChipType.ShockWave){
			return new ShockWaveMM(x,y, numTimer);
		}
		return null;
	}
	
}
