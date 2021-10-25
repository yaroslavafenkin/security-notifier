package io.jenkins.plugins.sample;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import okhttp3.*;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotifyByHttpSecurityNotifierStrategyImpl extends SecurityNotifierStrategy {

    private static final Logger LOGGER = Logger.getLogger(NotifyByHttpSecurityNotifierStrategyImpl.class.getName());

    private String url;

    private transient OkHttpClient client;

    @DataBoundConstructor
    public NotifyByHttpSecurityNotifierStrategyImpl(String url) {
        this.url = url;
        this.client = new OkHttpClient();
    }

    private Object readResolve() {
        this.client = new OkHttpClient();
        return this;
    }

    @Override
    public void sendNotification(String message) {
        Request request = new Request.Builder()
                .url(this.url)
                .post(RequestBody.create(message, MediaType.parse("text/plain; charset=utf-8")))
                .build();

        try {
            try (Response response = this.client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                LOGGER.log(Level.FINEST, String.valueOf(response.code()));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to notify via HTTP", e);
        }
    }

    @Extension
    @Symbol("notifyByHttpSecurityNotifier")
    public static class DescriptorImpl extends SecurityNotifierStrategyDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Notify by HTTP";
        }
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
