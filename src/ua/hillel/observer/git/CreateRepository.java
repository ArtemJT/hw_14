package ua.hillel.observer.git;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateRepository implements Repository {

    private final List<Branch> branchList;
    private final List<WebHook> webHookList;

    public CreateRepository() {
        branchList = new ArrayList<>();
        branchList.add(new Branch("main"));
        webHookList = new ArrayList<>();
    }

    @Override
    public Branch getBranch(String name) {
        Branch branch = new Branch(name);
        return branchList.contains(branch) ? branch : null;
    }

    @Override
    public Branch newBranch(Branch sourceBranch, String name) {
        if (!branchList.contains(sourceBranch)) throw new IllegalArgumentException();
        Branch createBranch = new Branch(name, sourceBranch);
        if (!branchList.contains(createBranch)) {
            branchList.add(createBranch);
            return createBranch;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Commit commit(Branch branch, String author, String[] changes) {
        Commit commit = new Commit(author, changes);
        branch.addCommit(commit);
        createEvent(branch, Event.Type.COMMIT, commit);
        return commit;
    }

    @Override
    public void merge(Branch sourceBranch, Branch targetBranch) {
        List<Commit> sourceCommits = sourceBranch.getCommitList();
        List<Commit> targetCommits = targetBranch.getCommitList();
        List<Commit> newCommits = sourceCommits.stream()
                .filter(com -> !targetCommits.contains(com))
                .collect(Collectors.toList());
        if (!newCommits.isEmpty()) {
            targetCommits.addAll(newCommits);
            createEvent(targetBranch, Event.Type.MERGE, newCommits.toArray(new Commit[0]));
        }
    }

    @Override
    public void addWebHook(WebHook webHook) {
        webHookList.add(webHook);
    }

    private void createEvent(Branch branch, Event.Type type, Commit... commitList) {
        if (webHookList != null) {
            webHookList.stream()
                    .filter(elem -> elem.type().equals(type) && elem.branch().equals(branch.toString()))
                    .findFirst().ifPresent(webHook -> {
                        Event mergeEvent = new Event(type, branch, Arrays.asList(commitList));
                        webHook.onEvent(mergeEvent);
                    });
        }
    }
}
