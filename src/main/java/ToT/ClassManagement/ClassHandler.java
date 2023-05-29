package ToT.ClassManagement;

import ToT.PlayerData;
import ToT.Utills;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ToT.Main.plugin;

public class ClassHandler implements Listener {
    @EventHandler
    public void onInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        PlayerData pd = new PlayerData(p.getUniqueId());
        for (Class cl : ClassList.list){

            List<Skill> AvailableSkills = new ArrayList<>();
            if (Objects.equals(pd.cla, cl.getClassName())){
                for (Skill skill : cl.getSkills()){
                    if (pd.lvl>=skill.getSkillLevel()){
                        AvailableSkills.add(skill);
                    }
                }
            }
            if (e.getHand() == EquipmentSlot.HAND) {
                boolean sit = false;
                if (Objects.equals(cl.getClassName(), "Swordsman")){
                    if (p.getInventory().getItemInMainHand().getType().equals(Material.IRON_SWORD)){
                        sit=true;
                    }
                }else if (Objects.equals(cl.getClassName(), "Mage")){
                    if (Objects.requireNonNull(p.getInventory().getItemInMainHand().getItemMeta()).getDisplayName().contains("Wand")){
                        sit=true;
                    }
                }else if (Objects.equals(cl.getClassName(), "Archer")){
                    if (p.getInventory().getItemInMainHand().getType().equals(Material.BOW)){
                        sit=true;
                    }
                }

                if (sit) {
                    if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                        List<String> a1 = new ArrayList<>();
                        List<List<String>> a2 = new ArrayList<>();
                        List<Integer> a3 = new ArrayList<>();
                        List<Integer> a4 = new ArrayList<>();

                        for (int i = 0; i < AvailableSkills.size(); i++) {
                            cl.getSkills().get(i).setPlayer(p);
                            a1.add(AvailableSkills.get(i).getSkillStatus());
                            a2.add(AvailableSkills.get(i).getSkillSubStatus());
                            a3.add(AvailableSkills.get(i).getSkillCooldown());
                            a4.add(AvailableSkills.get(i).getSkillManaCost());
                        }
//                        p.sendMessage("Skill Status: " +a1+" Skill Sub Status: "+a2+" Skill Cooldown: "+a3+" Skill Ce Cost: " + a4);
                        List<String> mvs = pd.sks;
                        if (pd.sks.get(0).equals("null")){
                            pd.set("sks",a1);
                        }
                        if (pd.sksusl.get(0).get(0).equals("null")){
                            pd.set("sksusl",a2);
                        }
                        if (pd.skc.get(0).equals(14211212)){
                            pd.set("skc",a3);
                        }
                        pd.set("skmana",a4);

                        if (p.isSneaking()) {
                            boolean done = false;
                            if (pd.cla.equals(cl.ClassName)){
                                for (int i = 1; i <= AvailableSkills.size()+1; i++) {
                                    if (pd.smn==i&& !done){
                                        if (pd.smn==AvailableSkills.size()){
                                            pd.set("smn", 1);
                                            p.sendMessage(Utills.chat("&lSkill: "+AvailableSkills.get(pd.smn-1).getSkillName()));
                                        }else{
                                            pd.set("smn", pd.smn+1);
                                            p.sendMessage(Utills.chat("&lSkill: "+AvailableSkills.get(pd.smn-1).getSkillName()));
                                            done=true;
                                        }
                                    }
                                }
                            }
                        }else if (!p.isSneaking()) {
                            if (pd.cla.equals(cl.ClassName)){
                                for (int d = 1; d <= AvailableSkills.size()+1; d++) {
                                    if (pd.smn == d) {
                                        int i = d-1;
                                        pd.update();
                                        if (Objects.equals(pd.sks.get(i), "Normal")){
                                            if (pd.mana >= AvailableSkills.get(i).getSkillManaCost()){
                                                String prevmvs= pd.sks.get(i);
                                                List<String> b1 = pd.sks;
                                                b1.set(i,"Cooldown");
                                                pd.set("sks",b1);
                                                pd.set("mana",pd.mana -pd.skmana.get(i));
                                                AvailableSkills.get(i).getSkillCode().run();
                                                new BukkitRunnable() {
                                                    final int finalI = i;
                                                    int secends = 0;
                                                    @Override
                                                    public void run() {
                                                        if (!Objects.equals(pd.sks.get(finalI), "Cooldown")){
                                                            List<Integer> d1 = pd.skc;
                                                            d1.set(finalI,0);
                                                            pd.set("skc",d1);

                                                            this.cancel();
                                                        }
                                                        if (secends>= AvailableSkills.get(finalI).getSkillCooldown()){
                                                            List<Integer> d1 = pd.skc;
                                                            d1.set(finalI,0);
                                                            pd.set("skc",d1);

                                                            List<String> b1 = pd.sks;
                                                            b1.set(finalI,prevmvs);
                                                            pd.set("sks",b1);

                                                            this.cancel();
                                                        }

                                                        List<Integer> d1 = pd.skc;
                                                        d1.set(finalI, AvailableSkills.get(finalI).getSkillCooldown()-secends);
                                                        pd.set("skc",d1);

                                                        secends++;
                                                    }}.runTaskTimer(plugin,0,20L);
                                            }else{
                                                p.sendMessage(Utills.chat("&4You Can't Use/Activate "+AvailableSkills.get(i).getSkillName()+" Because You Don't Have Enough Cursed Energy! (Required Ammount: "+pd.skmana.get(i)+")"));
                                            }
                                        }else if (Objects.equals(pd.sks.get(i), "Cooldown")){
                                            p.sendMessage(Utills.chat("&4You Can't Use/Activate "+AvailableSkills.get(i).getSkillName()+" Because It Is On Cooldown! ("+pd.skc.get(i)+" Seconds Left)"));
                                        }else{
                                            p.sendMessage(Utills.chat("&4You Can't Use/Activate "+AvailableSkills.get(i).getSkillName()+"(Reason: "+pd.sks.get(i)+")"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


//BACKUP 1
//    @EventHandler
//    public void onInteractEvent(PlayerInteractEvent e) {
//        Player p = e.getPlayer();
//        PlayerData pd = new PlayerData(p.getUniqueId());
//        if (e.getHand() == EquipmentSlot.HAND) {
//            if (p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getDisplayName() != null&&p.getItemInHand().getItemMeta().getDisplayName().equals(Utills.chat("&l&0Class Controller"))&&e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
//                List<String> a1 = new ArrayList<>();
//                List<Integer> a2 = new ArrayList<>();
//                List<Integer> a3 = new ArrayList<>();
//
//                for (int i = 0; i < getSkillsNames().size(); i++) {
//                    Skills.get(i).setPlayer(p);
//                    a1.add(Skills.get(i).getSkillStatus());
//                    a2.add(Skills.get(i).getSkillCooldown());
//                    a3.add(Skills.get(i).getSkillManaCost());
//                }
//                if (pd.mvs.get(0).equals("null")){
//                    pd.set("mvs",a1);
//                }
//                if (pd.mvc.get(0).equals(14211212)){
//                    pd.set("mvc",a2);
//                }
//                pd.set("mvce",a3);
//
//                if (p.isSneaking() == true) {
//                    boolean done = false;
//                    if (pd.tec.equals(ClassName)){
//                        for (int i = 1; i <= getSkillsNames().size()+1; i++) {
//                            if (pd.smn==i&&done!=true){
//                                if (pd.smn==getSkillsNames().size()){
//                                    pd.set("smn", 1);
//                                    p.sendMessage(Utills.chat("&lSkill: "+getSkillsNames().get(pd.smn-1)));
//                                }else{
//                                    pd.set("smn", pd.smn+1);
//                                    p.sendMessage(Utills.chat("&lSkill: "+getSkillsNames().get(pd.smn-1)));
//                                    done=true;
//                                }
//                            }
//                        }
//                    }
//                }else if (p.isSneaking() == false) {
//                    if (pd.tec.equals(ClassName)){
//                        for (int d = 1; d <= getSkillsNames().size()+1; d++) {
//                            if (pd.smn == d) {
//                                int i = d-1;
//                                pd.update();
//                                if (pd.mvs.get(i)=="Normal"){
//                                    if (pd.ce>=Skills.get(i).getSkillManaCost()){
//                                        String prevmvs= pd.mvs.get(i);
//                                        List<String> b1 = pd.mvs;
//                                        b1.set(i,"Cooldown");
//                                        pd.set("mvs",b1);
//                                        pd.set("ce",pd.ce-pd.mvce.get(i));
//                                        Skills.get(i).getSkillCode().run();
//                                        new BukkitRunnable() {
//                                            int finalI = i;
//                                            int secends = 0;
//                                            @Override
//                                            public void run() {
//                                                if (secends>=Skills.get(finalI).getSkillCooldown()){
//                                                    List<Integer> d1 = pd.mvc;
//                                                    d1.set(finalI,0);
//                                                    pd.set("mvc",d1);
//
//                                                    List<String> b1 = pd.mvs;
//                                                    b1.set(finalI,prevmvs);
//                                                    pd.set("mvs",b1);
//
//                                                    this.cancel();
//                                                }
//
//                                                List<Integer> d1 = pd.mvc;
//                                                d1.set(finalI,Skills.get(finalI).getSkillCooldown()-secends);
//                                                pd.set("mvc",d1);
//
//                                                secends++;
//                                            }}.runTaskTimer(Fabulus.plugin,0,20L);
//                                    }else{
//                                        p.sendMessage(Utills.chat("&4You Can't Use/Activate "+getSkillsNames().get(i)+" Because You Don't Have Enough Cursed Energy! (Required Ammount: "+pd.mvce.get(i)+")"));
//                                    }
//                                }else{
//                                    p.sendMessage(Utills.chat("&4You Can't Use/Activate "+getSkillsNames().get(i)+" Because It Is On Cooldown! ("+pd.mvc.get(i)+" Seconds Left)"));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}
