package com.example.mmbndemo.battle;

public class BattleFolderChip {
	private ChipAttackFactory.ChipType chiptype;
	private char chipcar;
	public BattleFolderChip(ChipAttackFactory.ChipType chiptype,char chipcar){
		this.chiptype=chiptype;
		this.chipcar=chipcar;
	}
	
	public char getChipcar() {
		return chipcar;
	}
	public void setChipcar(char chipcar) {
		this.chipcar = chipcar;
	}
	public ChipAttackFactory.ChipType getChiptype() {
		return chiptype;
	}
	public void setChiptype(ChipAttackFactory.ChipType chiptype) {
		this.chiptype = chiptype;
	}
}
