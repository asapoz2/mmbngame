package com.example.mmbndemo;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;


public class BitmapGetter {
	private static BitmapGetter instance=null;
	private Context context;
	private SparseArray<Bitmap> bitmaps;
	protected BitmapGetter(Context context){
		this.context=context;
		bitmaps=new SparseArray<Bitmap>();
	}
	public static void initialize(Context context){
		instance=new BitmapGetter(context);
	}
	public static Bitmap getBitmap(int resource){
		if(instance.bitmaps.get((resource))!=null){
			return instance.bitmaps.get(resource);
		}
		Bitmap bm=BitmapFactory.decodeResource(instance.context.getResources(), resource);
		instance.bitmaps.put(resource, bm);
		return bm;
		
	}

}
