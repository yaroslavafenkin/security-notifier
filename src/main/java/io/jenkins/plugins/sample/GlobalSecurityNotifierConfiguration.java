package io.jenkins.plugins.sample;

import hudson.Extension;
import hudson.model.PersistentDescriptor;
import hudson.util.DescribableList;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;
import java.util.Collections;

@Extension
@Symbol("securityNotifiers")
public class GlobalSecurityNotifierConfiguration extends GlobalConfiguration implements PersistentDescriptor {

    private final DescribableList<SecurityNotifierStrategy, SecurityNotifierStrategyDescriptor> securityNotifiers =
            new DescribableList<>(this, Collections.singletonList(new LoggingSecurityNotifierStrategyImpl()));

    private Object readResolve() {
        this.securityNotifiers.setOwner(this);
        return this;
    }

    public DescribableList<SecurityNotifierStrategy, SecurityNotifierStrategyDescriptor> getSecurityNotifiers() {
        return this.securityNotifiers;
    }

    public void setSecurityNotifiers(StaplerRequest req, JSONObject json) throws FormException {
        try {
            this.securityNotifiers.rebuildHetero(req, json, SecurityNotifierStrategyDescriptor.all(), "securityNotifiers");
        } catch (IOException ex) {
            throw new FormException(ex, "securityNotifiers");
        }
    }

    public static GlobalSecurityNotifierConfiguration get() {
        return GlobalConfiguration.all().getInstance(GlobalSecurityNotifierConfiguration.class);
    }
}
