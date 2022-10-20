package ua.hillel.observer.git;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Branch {

    private final String name;
    private final List<Commit> commitList;

    public Branch(final String name) {
        Objects.requireNonNull(name);
        this.name = name;
        commitList = new ArrayList<>();
    }

    public Branch(final String name, Branch sourceBranch) {
        Objects.requireNonNull(name);
        this.name = name;
        commitList = new ArrayList<>(sourceBranch.getCommitList());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Branch branch = (Branch) o;
        return name.equals(branch.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Commit> getCommitList() {
        return commitList;
    }

    public void addCommit(Commit commit) {
        commitList.add(commit);
    }
}
