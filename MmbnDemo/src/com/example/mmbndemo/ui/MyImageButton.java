package com.example.mmbndemo.ui;

import android.graphics.Bitmap;

public class MyImageButton extends InterfaceButton{
	private Bitmap mybm;
	public MyImageButton(int startx, int starty, int width, int height,Bitmap bm) {
		super(startx, starty, width, height);
		mybm=bm;
	}

}
