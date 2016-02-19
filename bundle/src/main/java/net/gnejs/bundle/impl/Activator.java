package net.gnejs.bundle.impl;

import com.eclipsesource.jaxrs.publisher.ApplicationConfiguration;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class Activator implements BundleActivator {

	private ServiceTracker<HttpService, Runnable> tracker;

	public void start(final BundleContext bundleContext) throws Exception {

		bundleContext.registerService(ApplicationConfiguration.class, new JaxRsConfig(), new Hashtable<String, Object>() {{
			put("jax-rs.resource", "yes");
		}});
		bundleContext.registerService(Object.class, new RestEndpoint(), new Hashtable<String, Object>() {{
			put("jax-rs.resource", "yes");
		}});

		tracker = new ServiceTracker<HttpService, Runnable>(bundleContext, HttpService.class, null) {
			@Override public Runnable addingService(ServiceReference<HttpService> ref) {
				List<BundleServlet> servlets = Arrays.asList(
						new BundleServlet("/s1"),
						new BundleServlet("/osgi/s2"));
				final HttpService svc = bundleContext.getService(ref);
				servlets.forEach(s -> {
					try {
						svc.registerServlet(s.getPath(), s, null, null);
					}
					catch (Exception e) {
						throw new RuntimeException(e);
					}
				});

				return () -> servlets.forEach(s -> svc.unregister(s.getPath()));
			}

			@Override public void removedService(ServiceReference<HttpService> reference, Runnable service) {
				service.run();
			}
		};
		tracker.open();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		if (tracker != null) tracker.close();
	}
}
