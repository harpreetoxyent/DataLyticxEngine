package com.oxyent.component.datalyticx.ui;

public class DataQuality {

    private String buName;
    private String quality;
    private Double percentage; 
    private String buEntityId;
    private String entity;
    
    
    public DataQuality(String buName, String quality, Double percentage) {
        this.buName = buName;
        this.quality = quality;
        this.percentage = percentage;
    }
    
    public DataQuality(String buName, String quality, Double percentage, String entity, String buEntityId) {
        this.buName = buName;
        this.quality = quality;
        this.percentage = percentage;
        this.entity = entity;
        this.buEntityId = buEntityId;
    }
    
    public String getBusinessUnit() {
        return buName;
    }
    public String getBuEntityId(){
    	return buEntityId;
    }
    
    public String getQuality() {
		return quality;
	}
    public String getEntity() {
		return entity;
	}
    public Double getPercentage() {
        return percentage;
    }
}
