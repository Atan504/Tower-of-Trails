package atan.Quests;

import java.util.Queue;

public class Quest {
    private String Name;
    private Queue<QuestState> states;
    public void progress(QuestState state){
        if (states.poll()==state)states.remove();
    }
    public void progress(){
        states.remove();
    }

    public void append(String info){
        states.add(new QuestState(info));
    }
    public void append(QuestState state){
        states.add(state);
    }

    public QuestState getCurrentState(){
        return states.poll();
    }
}
