package com.bala.github.cli.initialize;

import com.bala.github.cli.config.GitConfig;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;

public class GitCreateConnection {

    private static GitHub gitHub;

    private static void setGitHubConnection(GitHub gitHubConnectedInstance) {
        if(gitHub==null) {
            synchronized (GitHub.class) {
                gitHub = gitHubConnectedInstance;
            }
        }
    }

    public static GitHub createConnectionUsingPersonalAccessToken() {
        // default personal access token from git configs
        return createConnectionUsingPersonalAccessToken(GitConfig.getPersonalAccessToken());
    }

    public static GitHub createConnectionUsingPersonalAccessToken(String personalAccessToken) {
        try {
            if(gitHub == null) {
                setGitHubConnection(new GitHubBuilder().withOAuthToken(personalAccessToken).build());
            }
            System.out.println("Valid Credentials, connected to GitHub..." + gitHub.isCredentialValid());
        } catch (IOException e) {
            System.err.println("Invalid Credentials... Please try again...");
            System.exit(0);
        }
        return gitHub;
    }

    public static GitHub createConnectionUsingJwtToken() {
        // default jwt token from git configs
        return createConnectionUsingJwtToken(GitConfig.getJwtToken());
    }

    public static GitHub createConnectionUsingJwtToken(String jwtToken) {
        try {
            if(gitHub == null) {
                setGitHubConnection(new GitHubBuilder().withOAuthToken(jwtToken).build());
            }
            System.out.println("Valid Credentials, connected to GitHub..." + gitHub.isCredentialValid());
        } catch (IOException e) {
            System.err.println("Invalid Credentials... Please try again...");
            System.exit(0);
        }
        return gitHub;
    }

    @Deprecated
    public static void createConnectionUsingUserIdAndPassword(String userId, String password) {
        //No more supported
        System.err.println("Option not supported... Please try again...");
        System.exit(0);
    }
}
