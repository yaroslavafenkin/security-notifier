package io.jenkins.plugins.sample;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.ExtensionList;
import hudson.model.ManagementLink;
import org.jenkinsci.Symbol;

import java.util.logging.Logger;

@Extension
@Symbol("")
public class SecurityNotifierLink extends ManagementLink {

    private static final Logger LOGGER = Logger.getLogger(SecurityNotifierLink.class.getName());

    public static SecurityNotifierLink get() {
        return ExtensionList.lookupSingleton(SecurityNotifierLink.class);
    }

    @Override
    public String getIconFileName() {
        return "secure.png";
    }

    @Override
    public String getDisplayName() {
        return "Security notifications configuration";
    }

    @Override
    public String getUrlName() {
        return "configureSecurityEvents";
    }

    @NonNull
    @Override
    public Category getCategory() {
        return Category.SECURITY;
    }
}
