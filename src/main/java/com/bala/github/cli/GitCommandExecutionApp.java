package com.bala.github.cli;

import com.bala.github.cli.config.GitConfig;
import com.bala.github.cli.initialize.GitCreateConnection;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.Scanner;

public class GitCommandExecutionApp {

    public static void main(String[] args) {
        System.out.println("Welcome to Bala's GitCommandExecution App");
        System.out.println("Enter 1 to create pull request");
        System.out.println("Enter 2 to merge pull request");
        System.out.println("Option 3 - 10 for future usage");
        Scanner scanner = new Scanner(System.in);
        int selectedCommand = scanner.nextInt();

        if(selectedCommand==1) {
            createPullRequest();
        } else if (selectedCommand == 2) {
            mergePullRequest();
        } else {
            System.err.println("Entered option not supported at the moment...");
            System.exit(0);
        }
    }

    private static void createPullRequest() {
        // step1: connect to github using default configs in gitConfig.properties in the resources folder
        GitHub gitHub = GitCreateConnection.createConnectionUsingPersonalAccessToken();

        // step2: read repo name and connect with repo and validate connection

        GHRepository ghRepository = readAndValidateRepoName(gitHub);

        // step3: read branch name and connect with branch and validate connection

        GHBranch ghBranch = readAndValidateBranchName(ghRepository);

        // step4: create pullrequest using repo, given branch and base branch

        System.out.println("Enter tile for the pullrequests: ");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.next();
        System.out.println("Enter body details for the pullrequests: ");
        scanner = new Scanner(System.in);
        String body = scanner.next();
        System.out.println("Enter labels for the pullrequests: ");
        scanner = new Scanner(System.in);
        String labels = scanner.next(); //reading only one label per pull requests

        try {
            GHRepository forkRepo = ghRepository.fork();
            forkRepo.setEmailServiceHook(GitConfig.getEmailId());
            GHPullRequest ghPullRequest = ghRepository.createPullRequest(title, forkRepo.getOwnerName()+":"+ghBranch.getName(), ghRepository.getDefaultBranch(), body);
            ghPullRequest.setLabels(labels); //set only one label
//            ghPullRequest.setAssignees(); //future user assignees implementation
            System.out.println("A pull request is created at " + ghPullRequest.getHtmlUrl());
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Error creating pull requests... Please try again...");
            System.exit(0);
        }
    }

    private static GHRepository readAndValidateRepoName(GitHub gitHub) {
        System.out.println("Enter the repo name: ");
        Scanner scanner = new Scanner(System.in);
        String repoName = scanner.next();
        GHRepository ghRepository = null;
        try {
            ghRepository = gitHub.getRepository(repoName);
        } catch (IOException e) {
            System.err.println("Entered repo name is not valid... Please try again...");
            System.exit(0);
        }
        return ghRepository;
    }

    private static GHBranch readAndValidateBranchName(GHRepository ghRepository) {
        System.out.println("Enter the branch name for which pull request is created");
        Scanner scanner = new Scanner(System.in);
        String branchName = scanner.next();
        GHBranch ghBranch = null;
        try {
            ghBranch = ghRepository.getBranch(branchName);
        } catch (IOException e) {
            System.err.println("Entered repo name is not valid... Please try again...");
            System.exit(0);
        }
        return ghBranch;
    }

    private static void mergePullRequest() {
        // step1: connect to github using default configs in gitConfig.properties in the resources folder
        GitHub gitHub = GitCreateConnection.createConnectionUsingPersonalAccessToken();

        // step2: read repo name and connect with repo and validate connection

        GHRepository ghRepository = readAndValidateRepoName(gitHub);

        System.out.println("Enter the pull request number: ");

        Scanner scanner = new Scanner(System.in);

        int pullRequestNumber = scanner.nextInt();
        System.out.println("Enter if any message to merge: ");
        scanner = new Scanner(System.in);
        String messageToMerge = scanner.next();
        try {
            GHPullRequest ghPullRequest = ghRepository.getPullRequest(pullRequestNumber);
            ghPullRequest.merge(messageToMerge);
            System.out.println("Pull Request " + pullRequestNumber + " merged successfully...");
        } catch (IOException e) {
            System.err.println("Error merging pull requests... Please tray again...");
        }
    }
}
