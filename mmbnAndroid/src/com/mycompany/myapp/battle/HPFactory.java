package com.mycompany.myapp.battle;

import java.util.ArrayList;

import com.mycompany.myapp.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HPFactory {
	private static HPFactory instance=null;
	private ArrayList<Bitmap> enemyhpbm; 
	private ArrayList<Bitmap> mmhpbm; 
	protected HPFactory(Context context){
		enemyhpbm=new ArrayList<Bitmap>();
		mmhpbm=new ArrayList<Bitmap>();
		this.loadImages(context, enemyhpbm,mmhpbm);
	}
	protected void loadImages(Context context, ArrayList<Bitmap> enemyhpbm,ArrayList<Bitmap> mmhpbm) {
		//defaultbm=BitmapFactory.decodeResource(context.getResources(), R.drawable.metaur);
		//attimage=BitmapFactory.decodeResource(context.getResources(), R.drawable.metauranim4);
		enemyhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ehp_0));
		enemyhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ehp_1));
		enemyhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ehp_2));
		enemyhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ehp_3));
		enemyhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ehp_4));
		enemyhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ehp_5));
		enemyhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ehp_6));
		enemyhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ehp_7));
		enemyhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ehp_8));
		enemyhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ehp_9));
		mmhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.mhp_0));
		mmhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.mhp_1));
		mmhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.mhp_2));
		mmhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.mhp_3));
		mmhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.mhp_4));
		mmhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.mhp_5));
		mmhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.mhp_6));
		mmhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.mhp_7));
		mmhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.mhp_8));
		mmhpbm.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.mhp_9));
		
    } 
	public Bitmap getHPBitmap(int digit){
		return enemyhpbm.get(digit);
	}
	public static HPFactory getInstance(){
		return instance;
	}
	public static void initialize(Context context){
		if(instance==null){
			instance=new HPFactory(context);
		}
	}
	public static int getNthDigit(int number, int base, int n) {    
		  return (int) ((number / Math.pow(base, n - 1)) % base);
		  /*System.out.println(getNthDigit(123, 10, 1));  // 3
		  System.out.println(getNthDigit(123, 10, 2));  // 2
		  System.out.println(getNthDigit(123, 10, 3));  // 1*/
	}

}
