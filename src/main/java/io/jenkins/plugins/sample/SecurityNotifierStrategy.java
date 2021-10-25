package io.jenkins.plugins.sample;

import hudson.ExtensionPoint;
import hudson.model.AbstractDescribableImpl;

public abstract class SecurityNotifierStrategy extends AbstractDescribableImpl<SecurityNotifierStrategy> implements ExtensionPoint {

    public abstract void sendNotification(String message);
}
