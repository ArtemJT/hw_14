package ua.hillel.observer.git;

public class GitRepoObservers {

    public static Repository newRepository() {
        return new CreateRepository();
    }

    public static WebHook mergeToBranchWebHook(String branchName) {
        return new MergeWebHook(branchName);
    }

    public static WebHook commitToBranchWebHook(String branchName) {
        return new CommitWebHook(branchName);
    }
}
