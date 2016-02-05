package net.gnejs.osgi;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Start the OSGi framework.
 */
public class FrameworkStarter implements ServletContextListener {

	private Framework framework;

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("== Starting Framework");
		try {
			FrameworkFactory factory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
			Map<String, String> fwProps = new HashMap<>();
			fwProps.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, servletApiExports());
			framework = factory.newFramework(fwProps);
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

	private String servletApiExports() {
		return Stream.of("javax.servlet", "javax.servlet.annotation", "javax.servlet.descriptor", "javax.servlet.http")
				.map(p -> p + ";version=3.1.0")
				.collect(Collectors.joining(","));
	}
}
