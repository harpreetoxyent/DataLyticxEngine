package com.oxyent.datalyticx;

public class Quality {
	private Accuracy accuracy;
	private Completeness completeness;
	
	public Quality(){
		//accuracy = new Accuracy();
		//completeness = new Completeness();
	}
	public Quality(boolean isMandatory , String legitimateValue){
		if(legitimateValue != null && !"".equals(legitimateValue.trim())){
			accuracy = new Accuracy();			
		}
		if(isMandatory){
			completeness = new Completeness();
		}
	}
	public Accuracy getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Accuracy accuracy) {
		this.accuracy = accuracy;
	}
	public Completeness getCompleteness() {
		return completeness;
	}
	public void setCompleteness(Completeness completeness) {
		this.completeness = completeness;
	}
	public void accuracy(boolean val){
		if(accuracy == null){
			accuracy = new Accuracy();
		}
		accuracy.setAccurate(val);
	}
	public void completeness(boolean val){
		if(completeness == null){
			completeness = new Completeness();
		}
		completeness.setCompleteness(val);
	}
}
