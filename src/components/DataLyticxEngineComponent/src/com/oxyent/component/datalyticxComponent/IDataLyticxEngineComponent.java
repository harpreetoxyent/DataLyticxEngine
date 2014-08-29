package com.oxyent.component.datalyticxComponent;

import com.oxyent.datalyticx.DataLyticxEntity;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.core.commonData.IHICData;

public interface IDataLyticxEngineComponent {

	public double checkForCompleteness(DataLyticxEntity entity);
	public double checkForValidation(DataLyticxEntity entity);
	public double checkForAccuracy(DataLyticxEntity entity);

}
