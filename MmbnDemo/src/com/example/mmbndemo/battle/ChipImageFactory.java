package com.example.mmbndemo.battle;

import java.util.HashMap;

import com.example.mmbndemo.BitmapGetter;
import com.example.mmbndemo.R;
import com.example.mmbndemo.battle.ChipAttackFactory.ChipType;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class ChipImageFactory {
	private static ChipImageFactory instance;
	private HashMap<ChipType,Bitmap> fullMap;
	private HashMap<ChipType,Bitmap> grayMap;
	private HashMap<ChipType,Integer> resourceMap;
	private HashMap<ChipType,Integer> iconResourceMap;
	private HashMap<ChipType,Bitmap> iconMap;
	protected ChipImageFactory(){
		fullMap=new HashMap<ChipType,Bitmap>();
		resourceMap=new HashMap<ChipType,Integer>();
		resourceMap.put(ChipType.Airshot, R.drawable.airshotfull);
		resourceMap.put(ChipType.LongSword, R.drawable.longswordfull);
		resourceMap.put(ChipType.ShockWave, R.drawable.shockwavefull);
		
		grayMap=new HashMap<ChipType,Bitmap>();
		
		iconMap=new HashMap<ChipType,Bitmap>();
		iconResourceMap=new HashMap<ChipType,Integer>();
		iconResourceMap.put(ChipType.Airshot, R.drawable.airshoticon);
		iconResourceMap.put(ChipType.LongSword, R.drawable.longswordicon);
		iconResourceMap.put(ChipType.ShockWave, R.drawable.shockwaveicon);
		
		
	}
	public static ChipImageFactory getInstance(){
		if(instance==null){
			instance=new ChipImageFactory();
		}
		return instance;
	}
	public Bitmap getFullBitmap(ChipType chiptype){
		if(fullMap.containsKey(chiptype)){
			return fullMap.get(chiptype);
		}else{
			Bitmap bm=BitmapGetter.getBitmap(resourceMap.get(chiptype));
			fullMap.put(chiptype, bm);
			return bm;
		}
	}
	public Bitmap getGrayBitmap(ChipType chiptype){
		if(grayMap.containsKey(chiptype)){
			return grayMap.get(chiptype);
		}else{
			Bitmap bm=this.getFullBitmap(chiptype);
			Bitmap graybm=toGrayscale(bm);
			grayMap.put(chiptype, graybm);
			return graybm;
		}
	}
	public Bitmap getIconBitmap(ChipType chiptype){
		if(iconMap.containsKey(chiptype)){
			return iconMap.get(chiptype);
		}else{
			Bitmap bm=BitmapGetter.getBitmap(iconResourceMap.get(chiptype));
			iconMap.put(chiptype, bm);
			return bm;
		}
	}
	private static Bitmap toGrayscale(Bitmap bmpOriginal){        
	    int width, height;
	    height = bmpOriginal.getHeight();
	    width = bmpOriginal.getWidth();    

	    Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    Canvas c = new Canvas(bmpGrayscale);
	    Paint paint = new Paint();
	    ColorMatrix cm = new ColorMatrix();
	    cm.setSaturation(0);
	    ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
	    paint.setColorFilter(f);
	    c.drawBitmap(bmpOriginal, 0, 0, paint);
	    return bmpGrayscale;
	    }

}
