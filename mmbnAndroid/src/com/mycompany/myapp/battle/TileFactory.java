package com.mycompany.myapp.battle;

public class TileFactory {
	
	private int[][] toVertex;
	private int[][] toTileNum;
	
	
	public TileFactory(){
		toVertex=new int[18][2]; //{x,y}
		toTileNum=new int[6][3]; //x,y
		
		toVertex[0][0]= 0;
		toVertex[0][1]= 0;
		toVertex[1][0]= 1;
		toVertex[1][1]= 0;
		toVertex[2][0]= 2;
		toVertex[2][1]= 0;
		toVertex[3][0]= 3;
		toVertex[3][1]= 0;
		toVertex[4][0]= 4;
		toVertex[4][1]= 0;
		toVertex[5][0]= 5;
		toVertex[5][1]= 0;

		toVertex[6][0]= 0;
		toVertex[6][1]= 1;
		toVertex[7][0]= 1;
		toVertex[7][1]= 1;
		toVertex[8][0]= 2;
		toVertex[8][1]= 1;
		toVertex[9][0]= 3;
		toVertex[9][1]= 1;
		toVertex[10][0]= 4;
		toVertex[10][1]= 1;
		toVertex[11][0]= 5;
		toVertex[11][1]= 1;
		
		toVertex[12][0]= 0;
		toVertex[12][1]= 2;
		toVertex[13][0]= 1;
		toVertex[13][1]= 2;
		toVertex[14][0]= 2;
		toVertex[14][1]= 2;
		toVertex[15][0]= 3;
		toVertex[15][1]= 2;
		toVertex[16][0]= 4;
		toVertex[16][1]= 2;
		toVertex[17][0]= 5;
		toVertex[17][1]= 2;
		
		toTileNum[0][0]=0;
		toTileNum[1][0]=1;
		toTileNum[2][0]=2;
		toTileNum[3][0]=3;
		toTileNum[4][0]=4;
		toTileNum[5][0]=5;
		toTileNum[0][1]=6;
		toTileNum[1][1]=7;
		toTileNum[2][1]=8;
		toTileNum[3][1]=9;
		toTileNum[4][1]=10;
		toTileNum[5][1]=11;
		toTileNum[0][2]=12;
		toTileNum[1][2]=13;
		toTileNum[2][2]=14;
		toTileNum[3][2]=15;
		toTileNum[4][2]=16;
		toTileNum[5][2]=17;
	}
	public int getTileOf(BattleObject bo){
		return toTileNum[bo.getX()][bo.getY()];
	}
	public int toTileNumber(int x,int y){
		return toTileNum[x][y];
	}
	public int tileToX(int tilenum){
		return toVertex[tilenum][0];
	}
	
	public int tileToY(int tilenum){
		return toVertex[tilenum][1];
	}
}
