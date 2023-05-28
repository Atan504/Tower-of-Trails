package Atan.Quests;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class Quest implements Serializable {
    private String Name;
    private Queue<QuestState> states;

    public Quest(String name) {
        Name = name;
        states = new LinkedList<>();
    }

    public void progress(QuestState state){
        if (states.poll()==state)states.remove();
    }
    public void progress(){
        states.remove();
    }

//    public void append(String info){
//        states.add(new QuestState(info,self));
//    }
    public void append(QuestState state){
        states.add(state);
    }

    public QuestState getCurrentState(){
        return states.element();
    }

    public String getName() {
        return Name;
    }
}
