package io.jenkins.plugins.sample;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jenkins.plugins.mailer.tasks.MimeMessageBuilder;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class NotifyByMailSecurityNotifierStrategyImpl extends SecurityNotifierStrategy {

    private static final Logger LOGGER = Logger.getLogger(NotifyByMailSecurityNotifierStrategyImpl.class.getName());

    @DataBoundConstructor
    public NotifyByMailSecurityNotifierStrategyImpl() {
        // required for data binding
    }

    @Override
    public void sendNotification(String message) {
        try {
            MimeMessage mimeMessage = new MimeMessageBuilder().setBody(message).buildMimeMessage();
        } catch (UnsupportedEncodingException | jakarta.mail.MessagingException e) {
            LOGGER.log(Level.SEVERE, "Failed to notify via mail", e);
        }
    }

    @Extension
    @Symbol("notifyByMailSecurityNotifier")
    public static class DescriptorImpl extends SecurityNotifierStrategyDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Notify by mail";
        }
    }
}
