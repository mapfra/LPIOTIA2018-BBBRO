package org.eclipse.om2m.sdt.home.mocked.modules;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.actions.DecrementNumberValue;
import org.eclipse.om2m.sdt.home.actions.IncrementNumberValue;
import org.eclipse.om2m.sdt.home.actions.ResetNumberValue;
import org.eclipse.om2m.sdt.home.modules.NumberValue;
import org.eclipse.om2m.sdt.home.types.DatapointType;

public class MockedNumberValue extends NumberValue {
	
	private Thread t;

	public MockedNumberValue(String name, Domain domain) {
		super(name, domain, new FloatDataPoint(DatapointType.numberValue) {
			
			private float innerValue = 0;
			
			@Override
			protected Float doGetValue() throws DataPointException {
				return innerValue;
			}
			
			@Override
			protected void doSetValue(Float value) throws DataPointException {
				innerValue = value;
			}
		});
		
		// decrement
		setDecrementNumberValue(new DecrementNumberValue("decrement") {
			
			@Override
			protected void doDecrementNumberValue() throws ActionException {
				FloatDataPoint nv = (FloatDataPoint) getDataPointByShortName(DatapointType.numberValue.getShortName());
				try {
					nv.setValue(nv.getValue()-1);
				} catch (DataPointException e) {
					throw new ActionException(e);
				} catch (AccessException e) {
					throw new ActionException(e);
				}
			}
		});
		
		
		// increment
		setIncrementNumberValue(new IncrementNumberValue("increment") {
			
			@Override
			protected void doIncrementNumberValue() throws ActionException {
				FloatDataPoint nv = (FloatDataPoint) getDataPointByShortName(DatapointType.numberValue.getShortName());
				try {
					nv.setValue(nv.getValue()+1);
				} catch (DataPointException e) {
					throw new ActionException(e);
				} catch (AccessException e) {
					throw new ActionException(e);
				}
				
			}
		});
		
		
		// reset
		resetNumberValue(new ResetNumberValue("reset") {
			
			@Override
			protected void doResetNumberValue() throws ActionException {
				FloatDataPoint nv = (FloatDataPoint) getDataPointByShortName(DatapointType.numberValue.getShortName());
				try {
					nv.setValue(0f);
				} catch (DataPointException e) {
					throw new ActionException(e);
				} catch (AccessException e) {
					throw new ActionException(e);
				}
				
			}
		});
		
	}

}
