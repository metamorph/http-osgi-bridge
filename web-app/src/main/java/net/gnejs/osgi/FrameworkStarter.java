package net.gnejs.osgi;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.ServiceLoader;

/**
 * Start the OSGi framework.
 */
public class FrameworkStarter implements ServletContextListener {

	private Framework framework;

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("== Starting Framework");
		try {
			FrameworkFactory factory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
			framework = factory.newFramework(new HashMap<String, String>());
			framework.start();
			sce.getServletContext().setAttribute(BundleContext.class.getName(),
					framework.getBundleContext());
		}
		catch (BundleException e) {
			throw new RuntimeException(e);
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("== Stopping Framework");
		if (framework != null) {
			try {
				framework.stop();
			}
			catch (BundleException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
