package jdkproxy;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

public interface HttpRequestTemplate {
    HttpResponse doGet(HttpRequest httpRequest);

    HttpResponse doPosy(HttpRequest httpRequest);
}
