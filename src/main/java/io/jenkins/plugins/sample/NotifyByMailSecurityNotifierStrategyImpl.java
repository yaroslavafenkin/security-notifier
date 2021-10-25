package io.jenkins.plugins.sample;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import jenkins.plugins.mailer.tasks.MimeMessageBuilder;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("c_yafenkin@cloudbees.com"));
            Transport.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
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
