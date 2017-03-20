package com.example.mmbndemo.battle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import com.example.mmbndemo.AndroidTutorial;
import com.example.mmbndemo.BitmapGetter;
import com.example.mmbndemo.R;
import com.example.mmbndemo.battle.ChipAttackFactory.ChipType;
import com.example.mmbndemo.ui.ChipButton;
import com.example.mmbndemo.ui.InterfaceButton;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;



public class BattleMenu {
	/*protected BufferedImage defaultimage;
	protected BufferedImage selectorimage;
	protected BufferedImage okselectorimage;
	protected BufferedImage shockwaveimage;
	protected BufferedImage airshotimage;
	protected BufferedImage longswordimage;
	protected BufferedImage longswordfullimage;
	protected BufferedImage shockwavefullimage;
	protected BufferedImage airshotfullimage;*/
	
	private ChipButton chipbut1;
	private ChipButton chipbut2;
	private ChipButton chipbut3;
	private ChipButton chipbut4;
	private ChipButton chipbut5;
	private InterfaceButton okbutt;
	private InterfaceButton removebutt;
	
	public int selected=0;
	protected BattleInfo info;
	protected ArrayList<BattleFolderChip> folder;
	protected ArrayList<BattleFolderChip> choices;
	protected ArrayList<BattleFolderChip> selections;
	//public static HashMap<ChipAttackFactory.ChipType,BufferedImage> chipiconmap;
	//public static HashMap<ChipAttackFactory.ChipType,BufferedImage> chipfullmap;
	private char chipchar='*';
	
	private Bitmap selectionsbm=null;
	//private BattleFolderChip selectedChip=null;
	
	
	public BattleMenu(BattleInfo binfo) {
		this.info=binfo;
		folder=new ArrayList<BattleFolderChip>();
		folder.add(new BattleFolderChip(ChipAttackFactory.ChipType.LongSword,'l'));
		folder.add(new BattleFolderChip(ChipAttackFactory.ChipType.Airshot,'x'));
		folder.add(new BattleFolderChip(ChipAttackFactory.ChipType.ShockWave,'*'));
		folder.add(new BattleFolderChip(ChipAttackFactory.ChipType.LongSword,'l'));
		folder.add(new BattleFolderChip(ChipAttackFactory.ChipType.Airshot,'x'));
		folder.add(new BattleFolderChip(ChipAttackFactory.ChipType.ShockWave,'*'));
		folder.add(new BattleFolderChip(ChipAttackFactory.ChipType.LongSword,'l'));
		folder.add(new BattleFolderChip(ChipAttackFactory.ChipType.Airshot,'x'));
		folder.add(new BattleFolderChip(ChipAttackFactory.ChipType.ShockWave,'*'));
		
		choices=new ArrayList<BattleFolderChip>();
		selections=new ArrayList<BattleFolderChip>();
		
		
		randomizeFolder();
		chipbut1=new ChipButton(0,180);
		chipbut2=new ChipButton(48,180);
		chipbut3=new ChipButton(96,180);
		chipbut4=new ChipButton(144,180);
		chipbut5=new ChipButton(192,180);
		okbutt=new InterfaceButton(144,260,48,48);
		removebutt=new InterfaceButton(96,260,48,48);
		selectionsbm=BitmapGetter.getBitmap(R.drawable.menuselections);
	}

	public void called(){
		//get the 5 chips to add
		chipchar='*';
		selections.clear();
		int numtoget=5-choices.size();
		if(folder.size()<numtoget){
			for(BattleFolderChip chip:folder){
				choices.add(chip);
			}
		}else{
			for(int i=0;i<numtoget;i++){
				choices.add(folder.get(i));
			}
		}
		folder.removeAll(choices);
		updateChips();
	}
	public void updateChips(){
		if(choices.size()>0){
			if(chipchar=='*'||selections.isEmpty()||choices.get(0).getChipcar()==chipchar||choices.get(0).getChipcar()=='*'){
				chipbut1.setSelectable(true);
			}else{
				chipbut1.setSelectable(false);
			}
			
			chipbut1.setChip(choices.get(0));
		}else{
			
			chipbut1.setChip(null);
		}
		if(choices.size()>1){
			if(chipchar=='*'||selections.isEmpty()||choices.get(1).getChipcar()==chipchar||choices.get(1).getChipcar()=='*'){
				chipbut2.setSelectable(true);
			}else{
				chipbut2.setSelectable(false);
			}
			chipbut2.setChip(choices.get(1));
		}else{
			chipbut2.setChip(null);
		}
		if(choices.size()>2){
			if(chipchar=='*'||selections.isEmpty()||choices.get(2).getChipcar()==chipchar||choices.get(2).getChipcar()=='*'){
				chipbut3.setSelectable(true);
			}else{
				chipbut3.setSelectable(false);
			}
			chipbut3.setChip(choices.get(2));
		}else{
			chipbut3.setChip(null);
		}
		if(choices.size()>3){
			if(chipchar=='*'||selections.isEmpty()||choices.get(3).getChipcar()==chipchar||choices.get(3).getChipcar()=='*'){
				chipbut4.setSelectable(true);
			}else{
				chipbut4.setSelectable(false);
			}
			chipbut4.setChip(choices.get(3));
		}else{
			chipbut4.setChip(null);
		}
		if(choices.size()>4){
			if(chipchar=='*'||selections.isEmpty()||choices.get(4).getChipcar()==chipchar||choices.get(4).getChipcar()=='*'){
				chipbut5.setSelectable(true);
			}else{
				chipbut5.setSelectable(false);
			}
			chipbut5.setChip(choices.get(4));
		}else{
			chipbut5.setChip(null);
		}
		//chipbut5.setSelectable(Selectable.SELECTED);
		//chipbut4.setSelectable(Selectable.DISABLED);
	}
	
	

	
	private void selectChip(int select){
		//info.getFighter().setChipAttack(choices.get(select-1).getChiptype());'
		if(select>choices.size()){
			return;
		}
		BattleFolderChip curr=choices.get(select-1);
		if(chipchar=='*'||selections.isEmpty()||curr.getChipcar()==chipchar||curr.getChipcar()=='*'){
			selections.add(curr);
			choices.remove(curr);
			if(curr.getChipcar()!='*'){
				chipchar=curr.getChipcar();
			}
		}
	}
	
	
	private void randomizeFolder(){
		long seed = System.nanoTime();
		Collections.shuffle(folder, new Random(seed));
	}
	public void draw(Canvas c){
		//full is 240x160
		double scale=AndroidTutorial.scale;
		Paint p=new Paint();
		p.setColor(Color.GRAY);
		c.drawRect(0, (int)(160*scale), (int)(240*scale), (int)((160+200)*scale), p);
		
		/*Rect rect=new Rect();
		//0-200
		for(int i=0;i<5;i++){
			rect.set((int)(i*40*scale), (int)(180*scale), (int)((i+1)*40*scale), (int)(280*scale));
			p.setColor(Color.BLACK);
			c.drawRect(rect, p);
			rect.set((int)(i*40*scale+2*scale), (int)(182*scale), (int)((i+1)*40*scale-2*scale), (int)(278*scale));
			p.setColor(Color.DKGRAY);
			c.drawRect(rect, p);
		}*/
		updateChips();
		chipbut1.draw(c);
		chipbut2.draw(c);
		chipbut3.draw(c);
		chipbut4.draw(c);
		chipbut5.draw(c);
		//Paint p=new Paint();
		p.setColor(Color.GREEN);
		okbutt.draw(c, p);
		p.setColor(Color.RED);
		removebutt.draw(c, p);
		
		//32x111
		Rect rect=new Rect(0,(int)(scale*49),(int)(32*scale),(int)(160*scale));
		c.drawBitmap(selectionsbm, null, rect,null);
		//9x25
		for(int i=0;i<selections.size();i++){
			rect.set((int)(9*scale),(int)(scale*49+25*scale+i*16*scale),(int)(9*scale+14*scale),(int)(scale*49+25*scale+14*scale+i*16*scale));
			c.drawBitmap(ChipImageFactory.getInstance().getIconBitmap(selections.get(i).getChiptype()), null,rect,null);
		}
		
	}
	public boolean handleMotionEvent(MotionEvent event){
		//ChipButton[] buttarray={chipbut1,chipbut2,chipbut3,chipbut4,chipbut5};
		double scale=AndroidTutorial.scale;
		Rect dest=new Rect(0,(int)(scale*49),(int)(32*scale),(int)(160*scale));
		
		boolean hit1=chipbut1.didDragIn(event,dest);
		boolean hit2=chipbut2.didDragIn(event,dest);
		boolean hit3=chipbut3.didDragIn(event,dest);
		boolean hit4=chipbut4.didDragIn(event,dest);
		boolean hit5=chipbut5.didDragIn(event,dest);
		boolean hitok=okbutt.wasClickedOn(event);
		boolean hitrem=removebutt.wasClickedOn(event);
		selected=hit1?1:hit2?2:hit3?3:hit4?4:hit5?5:6;
		
		if(selected<=5){
			selectChip(selected);
			updateChips();
			return false;
		}else if(hitok){
			info.getFighter().setChipStack(selections);
			updateChips();
			return true;
		}else if(hitrem){
			if(!selections.isEmpty()){
				BattleFolderChip curr=selections.remove(selections.size()-1);
				choices.add(curr);
				//selections(curr);
				if(selections.isEmpty()){
					chipchar='*';
				}
			}
			chipchar='*';
			updateChips();
			return false;
		}
		return false;
		
	}
	/*public boolean handleMotionEvent(MotionEvent event){
		boolean hit1=chipbut1.wasClickedOn(event);
		boolean hit2=chipbut2.wasClickedOn(event);
		boolean hit3=chipbut3.wasClickedOn(event);
		boolean hit4=chipbut4.wasClickedOn(event);
		boolean hit5=chipbut5.wasClickedOn(event);
		boolean hitok=okbutt.wasClickedOn(event);
		boolean hitrem=removebutt.wasClickedOn(event);
		selected=hit1?1:hit2?2:hit3?3:hit4?4:hit5?5:6;
		
		if(selected<=5){
			selectChip(selected);
			updateChips();
			return false;
		}else if(hitok){
			info.getFighter().setChipStack(selections);
			updateChips();
			return true;
		}else if(hitrem){
			if(!selections.isEmpty()){
				BattleFolderChip curr=selections.remove(selections.size()-1);
				choices.add(curr);
				//selections(curr);
				if(selections.isEmpty()){
					chipchar='*';
				}
			}
			chipchar='*';
			updateChips();
			return false;
		}else{
			return false;
		}
	}*/
	
}
