package atan.Quests;

public class QuestState {
    private Quest Quest;
    public String info;
    public QuestState(String info) {
        this.info = info;
    }
    public void finish(){
        Quest.progress(this);
    }

    public String getInfo() {
        return info;
    }
}
