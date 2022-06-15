package me.combatborn.skills;

public abstract class Skill {

    private String name;

    private int level;
    private int experience;

    private boolean elite;

    private boolean combat;
    private boolean crafting;
    private boolean gathering;

    public void Skill(){

    }

    public boolean levelUp(){
        if (this.level == 100){
            return false;
        }
        this.level++;
        return true;
    }


}
