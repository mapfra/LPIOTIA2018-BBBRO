package org.onem2m.sdt.rights;

import org.onem2m.sdt.Action;
import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.events.SDTEventListener;

public interface RightsManager {
	
	boolean isGrantedReadAccess(DataPoint dataPoint);
	
	boolean isGrantedWriteAccess(DataPoint dataPoint);
	
	boolean isGrantedAccess(Action action);
	
	boolean isGrantedEventAccess(final SDTEventListener l, final DataPoint dp);
	
	void authenticateListener(SDTEventListener listener);

}
