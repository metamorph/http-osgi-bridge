# Hackish sample app for Felix Osgi/Servlet Bridge

`mvn install` from root - then `cd web-app` and then `mvn jetty:run`

The Felix `ProxyServlet` is mapped to `/osgi`.

Two servlets are registered with the `HttpService`, one to `/s1` and one to `/osgi/s2`.

## Expected:

    > curl http://localhost:8080/osgi/s1

    < 200 OK
    < "Hello from bundle servlet, registered @ /s1"

## Actual

    > curl http://localhost:8080/osgi/s2

    < 404 Not Found


    > curl http://localhost:8080/osgi/s2

    < 200 OK
    < "Hello from bundle servlet, registered @ /osgi/s2"


## Felix Webconsole

The Felix Webconsole is also installed as a bundle. It should be available at `http://localhost:8080/osgi/system/console`.

However, in order for it to show up there, it's necessary to tell the Webconsole to register the root-servlet at the
full path:

    mvn clean install jetty:run -Dfelix.webconsole.manager.root=/osgi/system/console


