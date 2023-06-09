package ToT;

import ToT.ClassManagement.ClassList;
import ToT.ClassManagement.Skill;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CustomMenu {

    public static Inventory skills_selector;
    public static Inventory skills_select;
    public static Inventory profile_menu;

    public static List<Skill> skills = ClassList.list.get(0).getSkills();

    public static Integer getSlot(Inventory inv, String item) {
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) != null && ChatColor.stripColor(Objects.requireNonNull(Objects.requireNonNull(inv.getItem(i)).getItemMeta()).getDisplayName()).equals(ChatColor.stripColor(item))) {
                return i;
            }
        }
        return -1;
    }

    public static void update_values(Player player, Inventory inv, String name, Integer amount) {

        PlayerData pd = new PlayerData(player.getUniqueId());

        ItemStack item = new ItemStack(Material.AIR);
        String pd_format = null;
        int old_pd_data = 0;
        ChatColor color = null;
        String displayName = null;
        int slot = 0;
        int multiply = 1;
        int mod = 0;
        int max = 0;
        String arrows = " >>> ";
        String data_name = null;

        if(name.equals("mana")) {
            item = inv.getItem(19);
            pd_format = "maxmana";
            old_pd_data = pd.maxmana;
            color = ChatColor.AQUA;
            displayName = "Mana";
            slot = 19;
            multiply = 10;
            arrows = ChatColor.WHITE + " >" + ChatColor.AQUA + ">" + ChatColor.WHITE + "> ";
            data_name = "Mana";
        }

        if(name.equals("str")) {
            item = inv.getItem(20);
            pd_format = "str";
            old_pd_data = pd.str;
            color = ChatColor.DARK_RED;
            displayName = "Strength";
            slot = 20;
            arrows = ChatColor.RED + " >" + ChatColor.DARK_RED + ">" + ChatColor.RED + "> ";
            data_name = "Strength";
        }

        if(name.equals("hp")) {
            item = inv.getItem(21);
            pd_format = "maxhp";
            old_pd_data = pd.maxhp;
            color = ChatColor.RED;
            displayName = "Health";
            slot = 21;
            multiply = 10;
            arrows = ChatColor.DARK_RED + " >" + ChatColor.RED + ">" + ChatColor.DARK_RED + "> ";
            data_name = "Health";
        }

        if(name.equals("def")) {
            item = inv.getItem(23);
            pd_format = "def";
            old_pd_data = pd.def;
            color = ChatColor.DARK_GRAY;
            displayName = "Defense";
            slot = 23;
            mod = 20;
            max = 1000;
            arrows = ChatColor.GRAY + " >" + ChatColor.DARK_GRAY + ">" + ChatColor.GRAY + "> ";
            data_name = "Defense";
        }

        if(name.equals("speed")) {
            item = inv.getItem(24);
            pd_format = "spe";
            old_pd_data = pd.spe;
            color = ChatColor.WHITE;
            displayName = "Speed";
            slot = 24;
            arrows = ChatColor.GRAY + " >" + ChatColor.WHITE + ">" + ChatColor.GRAY + "> ";
            data_name = "Speed";
        }

        if(name.equals("magic")) {
            item = inv.getItem(25);
            pd_format = "magic";
            old_pd_data = pd.magic;
            color = ChatColor.DARK_PURPLE;
            displayName = "Magic";
            slot = 25;
            arrows = ChatColor.LIGHT_PURPLE + " >" + ChatColor.DARK_PURPLE + ">" + ChatColor.LIGHT_PURPLE + "> ";
            data_name = "Magic";
        }

        assert item != null;
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();

        if(player.getMetadata("skills.point." + name).isEmpty()) player.setMetadata("skills.point" + name, new FixedMetadataValue(Main.plugin, 0));

        int data = player.getMetadata("skills.point." + name).get(0).asInt();

        int amount_math = data + amount;

        if(amount_math < 0) return;

        int points_amount_math = player.getMetadata("skills.point.count").get(0).asInt() - amount;

        if(points_amount_math < 0) return;

        player.setMetadata("skills.point.count", new FixedMetadataValue(Main.plugin, points_amount_math));

        ItemStack item2 = inv.getItem(4);
        assert item2 != null;
        ItemMeta meta2 = item2.getItemMeta();
        assert meta2 != null;
        meta2.setDisplayName(ChatColor.DARK_GREEN + String.valueOf(ChatColor.BOLD) + points_amount_math + " Skill Points");

        item2.setItemMeta(meta2);

        item2.setAmount(Math.min(Math.max(points_amount_math, 1), 64));

        player.setMetadata("skills.point." + name, new FixedMetadataValue(Main.plugin, amount_math));

        assert lore != null;
        if(max != 0) {
            lore.set(0, ChatColor.GRAY + "Points: " + color + player.getMetadata("skills.point." + name).get(0).asInt() + "/" + max);
        } else {
            lore.set(0, ChatColor.GRAY + "Points: " + color + player.getMetadata("skills.point." + name).get(0).asInt());
        }

        if(mod != 0) {
            if(amount_math != 0 && ((amount_math) % mod) == 0) {
                pd.set(pd_format, old_pd_data + 1);
                lore.set(1, ChatColor.GRAY + "Current " + displayName + ": " + color + (old_pd_data + 1) + "/" + max / mod + arrows + color + (old_pd_data + 1) + "/" + max / mod);
            } else if(amount_math != 0 && ((amount_math + amount) % mod) == 0) {
                lore.set(1, ChatColor.GRAY + "Current " + displayName + ": " + color + (old_pd_data) + "/" + max / mod + arrows + color + (old_pd_data + 1) + "/" + max / mod);
            }
        } else {
            assert pd_format != null;
            pd.set(pd_format, old_pd_data + (amount * multiply));
            lore.set(1, ChatColor.GRAY + "Current " + displayName + ": " + color + (old_pd_data + (amount * multiply)) + arrows + color + ((old_pd_data + (amount * multiply)) + (multiply)));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        // int updated_amount = player.getMetadata("skills.point." + name).get(0).asInt();
        int updated_amount = Utils.getData(pd, data_name);

        item.setAmount(Math.min(Math.max(updated_amount, 1), 64));

        inv.setItem(slot, item);
    }

    public static Inventory getInventory(Player player, String name, Integer num) {

        PlayerData pd = new PlayerData(player.getUniqueId());

        if(player.getMetadata("skills.point.mana").isEmpty()) player.setMetadata("skills.point.mana", new FixedMetadataValue(Main.plugin, 0));
        if(player.getMetadata("skills.point.str").isEmpty()) player.setMetadata("skills.point.str", new FixedMetadataValue(Main.plugin, 0));
        if(player.getMetadata("skills.point.hp").isEmpty()) player.setMetadata("skills.point.hp", new FixedMetadataValue(Main.plugin, 0));
        if(player.getMetadata("skills.point.def").isEmpty()) player.setMetadata("skills.point.def", new FixedMetadataValue(Main.plugin, 0));
        if(player.getMetadata("skills.point.speed").isEmpty()) player.setMetadata("skills.point.speed", new FixedMetadataValue(Main.plugin, 0));
        if(player.getMetadata("skills.point.magic").isEmpty()) player.setMetadata("skills.point.magic", new FixedMetadataValue(Main.plugin, 0));

        if(player.getMetadata("skills.point.count").isEmpty()) player.setMetadata("skills.point.count", new FixedMetadataValue(Main.plugin, pd.lvl * 2));

        if(Objects.equals(name, "profile_menu")) {
            profile_menu = Bukkit.createInventory(player, 45, ChatColor.GOLD + String.valueOf(ChatColor.BOLD) + "        Profile Menu");

            for(int i = 0; i < 45; i++) {
                ItemStack item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
                ItemMeta meta = item.getItemMeta();
                assert meta != null;
                meta.setDisplayName(" ");
                item.setItemMeta(meta);

                profile_menu.setItem(i, item);
            }

            ItemStack item7 = new ItemStack(Material.ENDER_EYE);
            item7.setAmount(player.getMetadata("skills.point.count").get(0).asInt() == 0 ? 1 : player.getMetadata("skills.point.count").get(0).asInt());
            ItemMeta meta7 = item7.getItemMeta();
            assert meta7 != null;
            meta7.setDisplayName(ChatColor.DARK_GREEN + String.valueOf(ChatColor.BOLD) + player.getMetadata("skills.point.count").get(0).asInt() + " Skill Points");
            meta7.setLore(Arrays.asList(
                    ChatColor.GRAY + "Each lvl gain you 2 Skill Points",
                    "",
                    ChatColor.RED + "Shift-Click to reset skills"
            ));
            item7.setItemMeta(meta7);
            profile_menu.setItem(4, item7);

            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);

            // int updated_amount = player.getMetadata("skills.point.mana").get(0).asInt();
            int updated_amount = Utils.getData(pd, "Mana");

            item.setAmount(Math.max(updated_amount, 1));
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            meta.setDisplayName(ChatColor.AQUA + String.valueOf(ChatColor.BOLD) + "Mana");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Points: " + ChatColor.AQUA + player.getMetadata("skills.point.mana").get(0).asInt(),
                    ChatColor.GRAY + "Current Mana: " + ChatColor.AQUA + pd.maxmana + ChatColor.WHITE + " >" + ChatColor.AQUA + ">" + ChatColor.WHITE + "> " + ChatColor.AQUA + (pd.maxmana + (10)),
                    "",
                    ChatColor.GRAY + "Each point in Mana increases your Mana in 10,",
                    ChatColor.GRAY + "allowing you to cast more spells in short time.",
                    "",
                    ChatColor.GREEN + "Left-Click to Increase in 1",
                    // ChatColor.RED + "Right-Click to Decrease in 1",
                    ChatColor.GOLD + "Shift-Click for Increase in 5"
                    // ChatColor.DARK_RED + "Middle-Click for reset"
            ));
            item.setItemMeta(meta);
            profile_menu.setItem(19, item);

            ItemStack item2 = new ItemStack(Material.IRON_SWORD);

            // int updated_amount2 = player.getMetadata("skills.point.str").get(0).asInt();
            int updated_amount2 = Utils.getData(pd, "Strength");

            item2.setAmount(Math.max(updated_amount2, 1));
            ItemMeta meta2 = item2.getItemMeta();
            assert meta2 != null;
            meta2.setDisplayName(ChatColor.DARK_RED + String.valueOf(ChatColor.BOLD) + "Strength");
            meta2.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta2.setLore(Arrays.asList(
                    ChatColor.GRAY + "Points: " + ChatColor.DARK_RED + player.getMetadata("skills.point.str").get(0).asInt(),
                    ChatColor.GRAY + "Current Strength: " + ChatColor.DARK_RED + pd.str + ChatColor.RED + " >" + ChatColor.DARK_RED + ">" + ChatColor.RED + "> " + ChatColor.DARK_RED + (pd.str + (1)),
                    "",
                    ChatColor.GRAY + "Each point in Strength enhances your physical power,",
                    ChatColor.GRAY + "making you a formidable warrior.",
                    ChatColor.GRAY + "Gain increased melee damage, improved weapon effectiveness,",
                    ChatColor.GRAY + "and additional strength-based abilities as you level up.",
                    "",
                    ChatColor.GREEN + "Left-Click to Increase in 1",
                    // ChatColor.RED + "Right-Click to Decrease in 1",
                    ChatColor.GOLD + "Shift-Click for Increase in 5"
                    // ChatColor.DARK_RED + "Middle-Click for reset"
            ));
            item2.setItemMeta(meta2);
            profile_menu.setItem(20, item2);

            ItemStack item3 = new ItemStack(Material.APPLE);

            // int updated_amount3 = player.getMetadata("skills.point.hp").get(0).asInt();
            int updated_amount3 = Utils.getData(pd, "Health");

            item3.setAmount(Math.max(updated_amount3, 1));
            ItemMeta meta3 = item3.getItemMeta();
            assert meta3 != null;
            meta3.setDisplayName(ChatColor.RED + String.valueOf(ChatColor.BOLD) + "Health");
            meta3.setLore(Arrays.asList(
                    ChatColor.GRAY + "Points: " + ChatColor.RED + player.getMetadata("skills.point.hp").get(0).asInt(),
                    ChatColor.GRAY + "Current Health: " + ChatColor.RED + pd.maxhp + ChatColor.DARK_RED + " >" + ChatColor.RED + ">" + ChatColor.DARK_RED + "> " + ChatColor.RED + (pd.maxhp + (10)),
                    "",
                    ChatColor.GRAY + "Each point in Health increases your Health in 10,",
                    ChatColor.GRAY + "boosts your resilience and vitality,",
                    ChatColor.GRAY + "making you harder to defeat. Increase your maximum health,",
                    "",
                    ChatColor.GREEN + "Left-Click to Increase in 1",
                    // ChatColor.RED + "Right-Click to Decrease in 1",
                    ChatColor.GOLD + "Shift-Click for Increase in 5"
                    // ChatColor.DARK_RED + "Middle-Click for reset"
            ));
            item3.setItemMeta(meta3);
            profile_menu.setItem(21, item3);

            ItemStack item4 = new ItemStack(Material.DIAMOND_CHESTPLATE);

            // int updated_amount4 = player.getMetadata("skills.point.def").get(0).asInt();
            int updated_amount4 = Utils.getData(pd, "Defense");

            item4.setAmount(Math.max(updated_amount4, 1));
            ItemMeta meta4 = item4.getItemMeta();
            assert meta4 != null;
            meta4.setDisplayName(ChatColor.DARK_GRAY + String.valueOf(ChatColor.BOLD) + "Defense");
            meta4.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta4.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta4.setLore(Arrays.asList(
                    ChatColor.GRAY + "Points: " + ChatColor.DARK_GRAY + player.getMetadata("skills.point.def").get(0).asInt() + "/1000",
                    ChatColor.GRAY + "Current Defense: " + ChatColor.DARK_GRAY + pd.def + "/50" + ChatColor.GRAY + " >" + ChatColor.DARK_GRAY + ">" + ChatColor.GRAY + "> " + ChatColor.DARK_GRAY + (pd.def) + "/50",
                    "",
                    ChatColor.GRAY + "each point in defence cost 20 skill points",
                    ChatColor.GRAY + "fortifies your defenses,",
                    ChatColor.GRAY + "ensuring your survival in the face of adversity.",
                    ChatColor.GRAY + "Gain increased armor effectiveness, damage reduction,",
                    ChatColor.GRAY + "and improved resistance against various types of damage as you level up.",
                    "",
                    ChatColor.GREEN + "Left-Click to Increase in 1",
                    // ChatColor.RED + "Right-Click to Decrease in 1",
                    ChatColor.GOLD + "Shift-Click for Increase in 5"
                    // ChatColor.DARK_RED + "Middle-Click for reset"
            ));
            item4.setItemMeta(meta4);
            profile_menu.setItem(23, item4);

            ItemStack item5 = new ItemStack(Material.SUGAR);

            // int updated_amount5 = player.getMetadata("skills.point.speed").get(0).asInt();
            int updated_amount5 = Utils.getData(pd, "Speed");

            item5.setAmount(Math.max(updated_amount5, 1));
            ItemMeta meta5 = item5.getItemMeta();
            assert meta5 != null;
            meta5.setDisplayName(ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + "Speed");
            meta5.setLore(Arrays.asList(
                    ChatColor.GRAY + "Points: " + ChatColor.WHITE + player.getMetadata("skills.point.speed").get(0).asInt(),
                    ChatColor.GRAY + "Current Speed: " + ChatColor.WHITE + pd.spe + ChatColor.GRAY + " >" + ChatColor.WHITE + ">" + ChatColor.GRAY + "> " + ChatColor.WHITE + (pd.spe + (1)),
                    "",
                    ChatColor.GRAY + "Each point in Speed enhances your agility and swiftness,",
                    ChatColor.GRAY + "allowing you to move with lightning-fast speed.",
                    ChatColor.GRAY + "Increase your movement speed, sprinting speed",
                    "",
                    ChatColor.GREEN + "Left-Click to Increase in 1",
                    // ChatColor.RED + "Right-Click to Decrease in 1",
                    ChatColor.GOLD + "Shift-Click for Increase in 5"
                    // ChatColor.DARK_RED + "Middle-Click for reset"
            ));
            item5.setItemMeta(meta5);
            profile_menu.setItem(24, item5);

            ItemStack item6 = new ItemStack(Material.ENDER_PEARL);

            // int updated_amount6 = player.getMetadata("skills.point.magic").get(0).asInt();
            int updated_amount6 = Utils.getData(pd, "Magic");

            item6.setAmount(Math.max(updated_amount6, 1));
            ItemMeta meta6 = item6.getItemMeta();
            assert meta6 != null;
            meta6.setDisplayName(ChatColor.DARK_PURPLE + String.valueOf(ChatColor.BOLD) + "Magic");
            meta6.setLore(Arrays.asList(
                    ChatColor.GRAY + "Points: " + ChatColor.DARK_PURPLE + player.getMetadata("skills.point.magic").get(0).asInt(),
                    ChatColor.GRAY + "Current Magic: " + ChatColor.DARK_PURPLE + pd.magic + ChatColor.LIGHT_PURPLE + " >" + ChatColor.DARK_PURPLE + ">" + ChatColor.LIGHT_PURPLE + "> " + ChatColor.DARK_PURPLE + (pd.magic + (1)),
                    "",
                    ChatColor.GRAY + "magic makes magic related skills and attacks stornger",
                    "",
                    ChatColor.GREEN + "Left-Click to Increase in 1",
                    // ChatColor.RED + "Right-Click to Decrease in 1",
                    ChatColor.GOLD + "Shift-Click for Increase in 5"
                    // ChatColor.DARK_RED + "Middle-Click for reset"
            ));
            item6.setItemMeta(meta6);
            profile_menu.setItem(25, item6);

            return profile_menu;
        }

        if(Objects.equals(name, "skills_selector")) {
            skills_selector = Bukkit.createInventory(player, 27, ChatColor.YELLOW + String.valueOf(ChatColor.BOLD) + "        Skill Selector");

            for(int i = 0; i < 27; i++) {
                ItemStack item;
                if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                    if(!player.getMetadata("skills.slot." + ((i - 10) + 2)).isEmpty()) {
                        item = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                        ItemMeta meta = item.getItemMeta();
                        assert meta != null;
                        meta.setDisplayName(ChatColor.GREEN + "[" + player.getMetadata("skills.slot." + ((i - 10) + 2)).get(0).asString() + "] Skill Slot #" + ((i - 10) + 2));
                        item.setItemMeta(meta);
                    } else {
                        item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                        ItemMeta meta = item.getItemMeta();
                        assert meta != null;
                        meta.setDisplayName(ChatColor.RED + "[None] Skill Slot #" + ((i - 10) + 2));
                        item.setItemMeta(meta);
                    }
                } else {
                    item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
                    ItemMeta meta = item.getItemMeta();
                    assert meta != null;
                    meta.setDisplayName(" ");
                    item.setItemMeta(meta);
                }
                skills_selector.setItem(i, item);
            }

            return skills_selector;
        }
        if(Objects.equals(name, "skills_select")) {

            skills_select = Bukkit.createInventory(player, 45, ChatColor.YELLOW + String.valueOf(ChatColor.BOLD) + "        Skill Select #" + num);

            for(int i = 0; i < 45; i++) {
                ItemStack item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
                ItemMeta meta = item.getItemMeta();
                assert meta != null;
                meta.setDisplayName(" ");
                item.setItemMeta(meta);
                skills_select.setItem(i, item);

                ItemStack item2 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                ItemMeta meta2 = item2.getItemMeta();
                assert meta2 != null;
                meta2.setDisplayName(ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + skills.get(0).getSkillName());
                item2.setItemMeta(meta2);
                skills_select.setItem(11, item2);

                ItemStack item3 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                ItemMeta meta3 = item3.getItemMeta();
                assert meta3 != null;
                meta3.setDisplayName(ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + skills.get(1).getSkillName());
                item3.setItemMeta(meta3);
                skills_select.setItem(12, item3);

                ItemStack item4 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                ItemMeta meta4 = item4.getItemMeta();
                assert meta4 != null;
                meta4.setDisplayName(ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + skills.get(2).getSkillName());
                item4.setItemMeta(meta4);
                skills_select.setItem(13, item4);

                ItemStack item5 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                ItemMeta meta5 = item5.getItemMeta();
                assert meta5 != null;
                meta5.setDisplayName(ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + skills.get(3).getSkillName());
                item5.setItemMeta(meta5);
                skills_select.setItem(14, item5);

                ItemStack item6 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                ItemMeta meta6 = item6.getItemMeta();
                assert meta6 != null;
                meta6.setDisplayName(ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + skills.get(4).getSkillName());
                item6.setItemMeta(meta6);
                skills_select.setItem(15, item6);

                ItemStack item7 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                ItemMeta meta7 = item7.getItemMeta();
                assert meta7 != null;
                meta7.setDisplayName(ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + skills.get(5).getSkillName());
                item7.setItemMeta(meta7);
                skills_select.setItem(20, item7);

                ItemStack item8 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                ItemMeta meta8 = item8.getItemMeta();
                assert meta8 != null;
                meta8.setDisplayName(ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + skills.get(6).getSkillName());
                item8.setItemMeta(meta8);
                skills_select.setItem(21, item8);

                ItemStack item9 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                ItemMeta meta9 = item9.getItemMeta();
                assert meta9 != null;
                meta9.setDisplayName(ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + skills.get(7).getSkillName());
                item9.setItemMeta(meta9);
                skills_select.setItem(22, item9);

                ItemStack item10 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                ItemMeta meta10 = item10.getItemMeta();
                assert meta10 != null;
                meta10.setDisplayName(ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + skills.get(8).getSkillName());
                item10.setItemMeta(meta10);
                skills_select.setItem(23, item10);

                ItemStack item11 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                ItemMeta meta11 = item11.getItemMeta();
                assert meta11 != null;
                meta11.setDisplayName(ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + skills.get(9).getSkillName());
                item11.setItemMeta(meta11);
                skills_select.setItem(24, item11);

                ItemStack item12 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                ItemMeta meta12 = item12.getItemMeta();
                assert meta12 != null;
                meta12.setDisplayName(ChatColor.WHITE + String.valueOf(ChatColor.BOLD) + skills.get(10).getSkillName());
                item12.setItemMeta(meta12);
                skills_select.setItem(31, item12);

            }

            return skills_select;
        }
        return Bukkit.createInventory(player, 27, "null");
    }

    public static void openInventory(Player player, Inventory inventory) {
        Bukkit.getScheduler().runTask(Main.plugin, () -> player.openInventory(inventory));
    }

}
