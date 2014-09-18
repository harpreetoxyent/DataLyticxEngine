package com.oxyent.datalyticx.engine;

import com.oxymedical.component.baseComponent.exception.ComponentException;

public class DataLyticxQualityEngineException extends ComponentException {
	
	DataLyticxQualityEngineException(){
		
	}

	public DataLyticxQualityEngineException(String message) {
		super(message);
	}
	public DataLyticxQualityEngineException(Throwable causeException){
		super(causeException);
	}
	public DataLyticxQualityEngineException(String message,Throwable th){
		super(message,th);
	}

}
