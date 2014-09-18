package com.oxyent.component.datalyticxComponent.constants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DLQCommonMethods {

	public String getActualValue(Object entityObject, String fieldName)  {
		System.out.println("Inside getActualValue ="+entityObject+"fieldName="+fieldName);
		String returnObject = null;
		
			String fieldToGetter = fieldName.replace("_", "");//Complete the code, apply other cases for capitalize next letter
			fieldToGetter = fieldToGetter.toLowerCase();
			fieldToGetter = "get" + fieldToGetter.substring(0, 1).toUpperCase() + fieldToGetter.substring(1);//Complete this
			Method method;
			try {
				method = entityObject.getClass().getMethod(fieldToGetter);
				returnObject = (String)method.invoke(entityObject);
			} catch (SecurityException e) {
				System.out.print("SecurityException in DataLyticxQualityEngine.getActualValue :");
				//throw new DataLyticxQualityEngineException("SecurityException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			} catch (NoSuchMethodException e) {
				System.out.print("NoSuchMethodException in DataLyticxQualityEngine.getActualValue :");
				//throw new DataLyticxQualityEngineException("NoSuchMethodException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			} catch (IllegalArgumentException e) {
				System.out.print("IllegalArgumentException in DataLyticxQualityEngine.getActualValue :");
				//throw new DataLyticxQualityEngineException("IllegalArgumentException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			} catch (IllegalAccessException e) {
				System.out.print("IllegalAccessException in DataLyticxQualityEngine.getActualValue :");
				//throw new DataLyticxQualityEngineException("IllegalAccessException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			} catch (InvocationTargetException e) {
				System.out.print("InvocationTargetException in DataLyticxQualityEngine.getActualValue :");
				//throw new DataLyticxQualityEngineException("InvocationTargetException in DataLyticxQualityEngine.getActualValue :"+e.getMessage());
			}
		return returnObject;
	}
}
