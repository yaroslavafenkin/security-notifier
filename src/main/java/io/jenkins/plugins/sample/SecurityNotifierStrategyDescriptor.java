package io.jenkins.plugins.sample;

import hudson.DescriptorExtensionList;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

public class SecurityNotifierStrategyDescriptor extends Descriptor<SecurityNotifierStrategy> {

    public static DescriptorExtensionList<SecurityNotifierStrategy, SecurityNotifierStrategyDescriptor> all() {
        return Jenkins.get().getDescriptorList(SecurityNotifierStrategy.class);
    }
}
