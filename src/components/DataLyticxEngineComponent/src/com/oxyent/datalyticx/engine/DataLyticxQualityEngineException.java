package com.oxyent.datalyticx.engine;

import com.oxymedical.component.baseComponent.exception.ComponentException;

public class DataLyticxQualityEngineException extends ComponentException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 29476299336963931L;
	public DataLyticxQualityEngineException(){
		
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
