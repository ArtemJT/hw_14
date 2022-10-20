package ua.hillel.observer.git;

import java.util.ArrayList;
import java.util.List;

public class MergeWebHook implements WebHook {

    private final String branchName;
    private final List<Event> events;

    public MergeWebHook(String branchName) {
        this.branchName = branchName;
        events = new ArrayList<>();
    }

    @Override
    public String branch() {
        return branchName;
    }

    @Override
    public Event.Type type() {
        return Event.Type.MERGE;
    }

    @Override
    public List<Event> caughtEvents() {
        return events;
    }

    @Override
    public void onEvent(Event event) {
        events.add(event);
    }
}
