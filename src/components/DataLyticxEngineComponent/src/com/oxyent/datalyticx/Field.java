package com.oxyent.datalyticx;

public class Field {
	
	private String name;
	private String value;
	public Quality quality;
	private boolean isMandatory;
	private String legitimateValue;

	public Field(String name, String value, boolean isMandatory,String legitimateVal, Quality quality) {
		this.name = name;
		this.value = value;
		this.quality = quality;
		this.isMandatory = isMandatory;
		this.legitimateValue = legitimateVal;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLegitimateValue() {
		return legitimateValue;
	}
	public void setLegitimateValue(String legitimateValue) {
		this.legitimateValue = legitimateValue;
	}
	public boolean isMandatory() {
		return isMandatory;
	}
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	public Quality getQuality() {
		return quality;
	}
	public void setQuality(Quality quality) {
		this.quality = quality;
	}
	
	//Returns value of Field : Similar to getValue()
	public String value(){
		return value;
	}

}