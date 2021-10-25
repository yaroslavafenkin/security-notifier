package io.jenkins.plugins.sample;

import hudson.Extension;
import hudson.ExtensionList;
import hudson.util.DescribableList;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;
import java.util.Collections;

@Extension
@Symbol("securityNotifiers")
public class SecurityNotifierConfiguration extends GlobalConfiguration {

    public static SecurityNotifierConfiguration get() {
        return ExtensionList.lookupSingleton(SecurityNotifierConfiguration.class);
    }

    private final DescribableList<SecurityNotifierStrategy, SecurityNotifierStrategyDescriptor> securityNotifiers =
            new DescribableList<>(this, Collections.singletonList(new LoggingSecurityNotifierStrategyImpl()));

    public SecurityNotifierConfiguration() {
        load();
    }

    public DescribableList<SecurityNotifierStrategy, SecurityNotifierStrategyDescriptor> getSecurityNotifiers() {
        return this.securityNotifiers;
    }

    private Object readResolve() {
        this.securityNotifiers.setOwner(this);
        return this;
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
        try {
            this.securityNotifiers.rebuildHetero(req, json, SecurityNotifierStrategyDescriptor.all(), "securityNotifiers");
            return true;
        } catch (IOException ex) {
            throw new FormException(ex, "securityNotifiers");
        }
    }
}
