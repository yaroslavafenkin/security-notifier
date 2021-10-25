package io.jenkins.plugins.sample;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingSecurityNotifierStrategyImpl extends SecurityNotifierStrategy {

    private static final Logger LOGGER = Logger.getLogger(LoggingSecurityNotifierStrategyImpl.class.getName());

    @DataBoundConstructor
    public LoggingSecurityNotifierStrategyImpl() {
        // required for data binding
    }

    @Override
    public void sendNotification(String message) {
        LOGGER.log(Level.INFO, message);
    }

    @Extension
    @Symbol("loggingSecurityNotifier")
    public static class DescriptorImpl extends SecurityNotifierStrategyDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Logging security notifier";
        }
    }
}
