package io.jenkins.plugins.sample;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import jenkins.security.SecurityListener;
import org.springframework.security.core.userdetails.UserDetails;

@Extension
public class SecurityNotifierImpl extends SecurityListener {

    @Override
    protected void authenticated2(@NonNull UserDetails details) {
//        notify2(details.getUsername() + " has authenticated.");
    }

    @Override
    protected void failedToAuthenticate(@NonNull String username) {
//        notify2(username + " has failed to authenticate.");
    }

    @Override
    protected void loggedIn(@NonNull String username) {
//        notify2(username + " has logged in.");
    }

    @Override
    protected void userCreated(@NonNull String username) {
//        notify2(username + " was created.");
    }

    @Override
    protected void failedToLogIn(@NonNull String username) {
//        notify2(username + " has failed to log in.");
    }

    @Override
    protected void loggedOut(@NonNull String username) {
//        notify2(username + " has logged out.");
    }

//    private void notify2(String message) {
//        SecurityNotifierConfiguration.get().getSecurityNotifiers()
//                .forEach(securityNotifierStrategy -> securityNotifierStrategy.sendNotification(message));
//    }
}
