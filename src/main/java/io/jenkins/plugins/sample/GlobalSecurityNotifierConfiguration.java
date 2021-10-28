package io.jenkins.plugins.sample;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.Descriptor;
import hudson.model.RootAction;
import hudson.util.DescribableList;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.StaplerRequest;

import java.io.IOException;
import java.util.Collections;

@Extension
@Symbol("configureSecurityEvents")
public class GlobalSecurityNotifierConfiguration extends GlobalConfiguration implements RootAction {

    private final DescribableList<SecurityNotifierStrategy, SecurityNotifierStrategyDescriptor> securityNotifiers =
            new DescribableList<>(this, Collections.singletonList(new LoggingSecurityNotifierStrategyImpl()));

    private Object readResolve() {
        this.securityNotifiers.setOwner(this);
        return this;
    }

    public DescribableList<SecurityNotifierStrategy, SecurityNotifierStrategyDescriptor> getSecurityNotifiers() {
        return this.securityNotifiers;
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) throws Descriptor.FormException {
        try {
            this.securityNotifiers.rebuildHetero(req, json, SecurityNotifierStrategyDescriptor.all(), "securityNotifiers");
            return true;
        } catch (IOException ex) {
            throw new FormException(ex, "securityNotifiers");
        }
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getUrlName() {
        return "configureSecurityEvents";
    }

    @NonNull
    @Override
    public String getDisplayName() {
        return "Security notifications configuration";
    }

    public static GlobalSecurityNotifierConfiguration get() {
        return GlobalConfiguration.all().getInstance(GlobalSecurityNotifierConfiguration.class);
    }
}
