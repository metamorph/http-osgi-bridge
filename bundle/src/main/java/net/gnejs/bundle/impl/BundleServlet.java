package net.gnejs.bundle.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BundleServlet extends HttpServlet {

	private final String path;

	public BundleServlet(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	@Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello from bundle servlet, registered @ " + path);
	}
}
