package jdkproxy;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

import java.util.concurrent.TimeUnit;

public class HttpRequestTemplateImpl implements HttpRequestTemplate {
    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpResponse doPosy(HttpRequest httpRequest) {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
