package org.eclipse.om2m.ipe.sample.sdt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.ipe.sample.sdt.controller.LifeCycleManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *  Manages the starting and stopping of the bundle.
 */
public class Activator implements BundleActivator {
    /** Logger */
    private static Log logger = LogFactory.getLog(Activator.class);

    @Override
    public void start(final BundleContext bundleContext) throws Exception {
        new Thread() {
        	public void run() {
        		try {
        			LifeCycleManager.start(bundleContext);
        		} catch (Exception e) {
        			logger.error("IpeMonitor Sample error", e);
        		}
        	}
        }.start();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        logger.info("Stop Ipe Sample");
        try {
        	LifeCycleManager.stop();
        } catch (Exception e) {
            logger.error("Stop IPE Sample error", e);
        }
    }

}
