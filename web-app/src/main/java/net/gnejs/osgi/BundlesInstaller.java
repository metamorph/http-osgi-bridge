package net.gnejs.osgi;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

/**
 * Install bundles from the class-path.
 */
public class BundlesInstaller implements ServletContextListener {

	LinkedList<Bundle> bundles = new LinkedList<>();

	@Override public void contextInitialized(ServletContextEvent sce) {
		System.out.println("== Installing bundles from classpath");
		bundles = bundleUrls().stream()
				.map( u -> install(bundleContext(sce.getServletContext()), u))
				.collect(toCollection(LinkedList::new));
		bundles.forEach(this::start);
	}

	@Override public void contextDestroyed(ServletContextEvent sce) {
		bundles.descendingIterator()
				.forEachRemaining(this::uninstall);
	}

	private BundleContext bundleContext(ServletContext ctx) {
		return Objects.requireNonNull((BundleContext) ctx.getAttribute(BundleContext.class.getName()),
				"No Bundle Context bound");
	}

	private List<URL> bundleUrls() {
		try {
			return Stream.of(
					new PathMatchingResourcePatternResolver().getResources("classpath*:/bundles/*.jar"))
						.map(r -> {
							try {
								return r.getURL();
							}
							catch (IOException e) {
								throw new RuntimeException(e);
							}
						}).collect(Collectors.toList());
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Bundle install(BundleContext context, URL url) {
		try {
			System.out.println("== Installing from " + url);
			return context.installBundle(url.toString(), url.openStream());
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void start(Bundle bundle) {
		try {
			System.out.println("== Starting " + bundle.getSymbolicName());
			bundle.start();
		}
		catch (BundleException e) {
			throw new RuntimeException(e);
		}
	}

	private void uninstall(Bundle b) {
		try {
			System.out.println("== Uninstalling " + b.getSymbolicName());
			b.uninstall();
		}
		catch (BundleException e) {
			throw new RuntimeException(e);
		}
	}
}
