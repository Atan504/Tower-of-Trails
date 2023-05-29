package ToT.ClassManagement;

import java.util.ArrayList;
import java.util.List;


public class Class {
    String ClassName;
    List<Skill> Skills;

    public Class(String tn, List<Skill> mvs){
        ClassName=tn;
        Skills=mvs;
    }

    public String getClassName() {
        return ClassName;
    }

    public List<Skill> getSkills() {
        return Skills;
    }

    public ArrayList<String> getSkillsNames() {
        ArrayList<String> mn = new ArrayList<>();
        for (Skill skill : Skills) {
            mn.add(skill.getSkillName());
        }
        return mn;
    }

    public void setSkills(ArrayList<Skill> skills) {
        Skills = skills;
    }

    public void setClassName(String classname) {
        ClassName = classname;
    }
}
