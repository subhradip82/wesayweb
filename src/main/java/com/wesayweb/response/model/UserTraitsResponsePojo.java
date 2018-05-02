package com.wesayweb.response.model;

import java.io.Serializable;


public class UserTraitsResponsePojo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long traitid;
	
	private String traitname;
	private String traituniqid;
	private String traitdescripion;
	
	private String traiticonpath;
	
	private int positive;
	private int negetive;
	private int nutral;
	private String traittype;
	private int isannonymous;
	
	
	public String getTraittype() {
		return traittype;
	}
	public void setTraittype(String traittype) {
		this.traittype = traittype;
	}
	public String getTraituniqid() {
		return traituniqid;
	}
	public void setTraituniqid(String traituniqid) {
		this.traituniqid = traituniqid;
	}
	public Long getTraitid() {
		return traitid;
	}
	public void setTraitid(Long traitid) {
		this.traitid = traitid;
	}
	public String getTraitname() {
		return traitname;
	}
	public void setTraitname(String traitname) {
		this.traitname = traitname;
	}
	public String getTraitdescripion() {
		return traitdescripion;
	}
	public void setTraitdescripion(String traitdescripion) {
		this.traitdescripion = traitdescripion;
	}
	public String getTraiticonpath() {
		return traiticonpath;
	}
	public void setTraiticonpath(String traiticonpath) {
		this.traiticonpath = traiticonpath;
	}
	public int getPositive() {
		return positive;
	}
	public void setPositive(int positive) {
		this.positive = positive;
	}
	public int getNegetive() {
		return negetive;
	}
	public void setNegetive(int negetive) {
		this.negetive = negetive;
	}
	public int getNutral() {
		return nutral;
	}
	public void setNutral(int nutral) {
		this.nutral = nutral;
	}
	public int getIsannonymous() {
		return isannonymous;
	}
	public void setIsannonymous(int isannonymous) {
		this.isannonymous = isannonymous;
	}
	 	
}
