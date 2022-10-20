package ua.hillel;

import ua.hillel.observer.git.*;

public class Main {
    public static void main(String[] args) {
        Repository repo = GitRepoObservers.newRepository();

        Branch mainBranch = repo.getBranch("main");
        System.out.println(mainBranch);

        Branch devBranch = repo.newBranch(mainBranch, "dev");

        WebHook mergeDevWebHook = GitRepoObservers.mergeToBranchWebHook("dev");
        WebHook commitMainWebHook = GitRepoObservers.commitToBranchWebHook("main");

        repo.addWebHook(mergeDevWebHook);
        repo.addWebHook(commitMainWebHook);

        repo.commit(mainBranch, "First Author", new String[]{"Initial commit"});
        repo.commit(mainBranch, "First Author", new String[]{"Added README.md",});
        repo.merge(mainBranch, devBranch);
        repo.commit(mainBranch, "Second Author", new String[]{"Commit from second author",});

        mergeDevWebHook.caughtEvents().forEach(System.out::println);
        commitMainWebHook.caughtEvents().forEach(System.out::println);
    }
}