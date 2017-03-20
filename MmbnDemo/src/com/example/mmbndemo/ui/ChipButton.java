package com.example.mmbndemo.ui;

import com.example.mmbndemo.AndroidTutorial;
import com.example.mmbndemo.BitmapGetter;
import com.example.mmbndemo.R;
import com.example.mmbndemo.battle.BattleFolderChip;
import com.example.mmbndemo.battle.ChipImageFactory;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class ChipButton extends InterfaceButton {
	private BattleFolderChip bfc=null;
	private Bitmap mybm=null;
	private Bitmap mygraybm=null;
	private static Bitmap background=null;
	private static Bitmap invalidbackground=null;
	private char mychar='.';
	private boolean dragging=false;
	private float dragx=0;
	private float dragy=0;

	private boolean selectable;


	public ChipButton(int startx, int starty) {
		super(startx, starty,48,65);
		if(background==null){
			background=BitmapGetter.getBitmap(R.drawable.chipvalidbg);
			invalidbackground=BitmapGetter.getBitmap(R.drawable.chipinvalidbg);
		}
		
		
	}
	public void setChip(BattleFolderChip bfc){
		this.bfc=bfc;
		if(bfc!=null){
			mybm=ChipImageFactory.getInstance().getFullBitmap(bfc.getChiptype());
			mygraybm=ChipImageFactory.getInstance().getGrayBitmap(bfc.getChiptype());
			mychar=bfc.getChipcar();
		}else{
			mybm=null;
			mychar='.';
			selectable=false;
		}
	}
	public boolean getSelectable() {
		return selectable;
	}
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
	public BattleFolderChip getChip(){
		return bfc;
	}
	public void draw(Canvas c){
		double scale=AndroidTutorial.scale;
		double startx=(!dragging)?this.startx:dragx/scale-width/2;
		double starty=(!dragging)?this.starty:dragy/scale-height/2;
		
		//94x127
		//48x64.85
		
		Rect rect=new Rect((int)(scale*startx), (int)(scale*starty), (int)(scale*(startx+width)),(int)(scale*(starty+64.85)));
		Paint p=new Paint();
		
		//p.setColor((selectable==Selectable.UNSELECTED)?Color.DKGRAY:(selectable==Selectable.DISABLED||mybm==null)?Color.LTGRAY:Color.CYAN);
		Bitmap bg=(selectable)?background:invalidbackground;
		//c.drawRect(rect, p);
		c.drawBitmap(bg, null, rect,null);
		//44
		//64x56 chips
		//double chipscale=0.71875;
		//new dimen 44x38.5
		//start at 12/5 use original dimen
		
		//0.51063829787234042553191489361702=new scale
		double chipscale=0.5106;
		//start at 12x6
		rect.set((int)(scale*startx+scale*chipscale*12), (int)(scale*(starty+6*chipscale)), (int)(scale*(startx+12*chipscale+chipscale*64)),(int)(scale*(starty+6*chipscale+chipscale*56)));
		if(mybm!=null){
			c.drawBitmap((this.selectable)?mybm:mygraybm, null, rect, null);
		}
		if(mychar!='.'){
			p.setColor(Color.BLACK);
			p.setTextSize((float)(15*scale));
			c.drawText(""+mychar, (int)(scale*(startx+width/2-10)), (int)(scale*(starty+6+chipscale*56+15)), p);
		}
	}
	
	public boolean startDrag(MotionEvent event){
		dragging=super.wasClickedOn(event);
		dragx=event.getX();
		dragy=event.getY();
		return dragging;
	}
	public boolean continueDrag(MotionEvent event){
		int pointerIndex = event.getActionIndex();

	    int maskedAction = event.getActionMasked();
		dragx= event.getX(pointerIndex);
	    dragy=event.getY(pointerIndex);
	    dragging=(maskedAction==MotionEvent.ACTION_MOVE);
		return dragging;
	}
	public boolean endDragIn(MotionEvent event,Rect dest){
		int pointerIndex = event.getActionIndex();

	    int maskedAction = event.getActionMasked();
		dragx= event.getX(pointerIndex);
	    dragy=event.getY(pointerIndex);
	    if(maskedAction==MotionEvent.ACTION_UP){
	    	if(dragx>dest.left && dragx<dest.right && dragy>dest.top && dragy<dest.bottom){
	    		dragging=false;
	    		return true;
	    	}
	    }
		return false;
	}
	
	public boolean didDragIn(MotionEvent event,Rect dest){
		int pointerIndex = event.getActionIndex();
		dragx= event.getX(pointerIndex);
	    dragy=event.getY(pointerIndex);
	    int maskedAction = event.getActionMasked();
	    if(dragging&&maskedAction==MotionEvent.ACTION_UP){
	    	if(dragx>dest.left && dragx<dest.right && dragy>dest.top && dragy<dest.bottom){
	    		dragging=false;
	    		return true;
	    	}
	    }
	    dragging=super.wasClickedOn(event)||(dragging&&maskedAction==MotionEvent.ACTION_MOVE);
		return false;
	}

}
