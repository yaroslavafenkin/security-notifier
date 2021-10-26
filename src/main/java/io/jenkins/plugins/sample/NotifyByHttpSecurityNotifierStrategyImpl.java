package io.jenkins.plugins.sample;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.ProxyConfiguration;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotifyByHttpSecurityNotifierStrategyImpl extends SecurityNotifierStrategy {

    private static final Logger LOGGER = Logger.getLogger(NotifyByHttpSecurityNotifierStrategyImpl.class.getName());

    private String url;

    @DataBoundConstructor
    public NotifyByHttpSecurityNotifierStrategyImpl(String url) {
        this.url = url;
    }

    @Override
    public void sendNotification(String message) {
        URLConnection connection;
        try {
            // should also have a path when proxy isn't configured
            connection = ProxyConfiguration.open(new URL(this.url));

            if (connection instanceof HttpURLConnection) {
                HttpURLConnection con = (HttpURLConnection) connection;
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "text/plain; utf-8");
                con.setDoOutput(true);

                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = message.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                if (con.getResponseCode() != 200) {
                    LOGGER.log(Level.WARNING, "Failed to notify " + this.url + "Response code: " + con.getResponseCode());
                }
            }
        } catch (MalformedURLException e) {
            LOGGER.log(Level.SEVERE, "URL is invalid: " + this.url, e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to open connection to: " + this.url, e);
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
