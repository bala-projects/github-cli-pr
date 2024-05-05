package com.bala.github.cli.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GitConfig {

    private static Properties gitConfigs;

    public static String getPersonalAccessToken() {
        return getGitConfigs().getProperty("git.personal.access.token");
    }

    public static String getUserName() {
        return getGitConfigs().getProperty("git.username");
    }

    public static String getEmailId() {
        return getGitConfigs().getProperty("git.email.id");
    }

    public static String getJwtToken() {
        return getGitConfigs().getProperty("git.jwt_token");
    }

    private static Properties getGitConfigs() {
        if(gitConfigs==null) {
            synchronized (GitConfig.class) {
                loadConfigs();
            }
        }
        return gitConfigs;
    }

    private static void loadConfigs() {
        try(InputStream input = GitConfig.class.getClassLoader().getResourceAsStream("git_config.properties")) {
            gitConfigs = new Properties();
            if (input == null) {
                System.err.println("Error in loading default git configs...");
                System.exit(0);
            }
            gitConfigs.load(input);
        } catch(IOException ex) {
            System.err.println("Error in loading default git configs...");
            System.exit(0);
        }
    }

}
