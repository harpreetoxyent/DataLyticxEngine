package com.oxyent.datalyticx;

import com.oxymedical.component.baseComponent.exception.ComponentException;

public class DataLyticxEntityException extends ComponentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4621370243219754600L;
	public DataLyticxEntityException(){
		
	}

	public DataLyticxEntityException(String message) {
		super(message);
	}
	public DataLyticxEntityException(Throwable causeException){
		super(causeException);
	}
	public DataLyticxEntityException(String message,Throwable th){
		super(message,th);
	}
}
