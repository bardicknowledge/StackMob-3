package uk.antiperson.stackmob.tasks;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitRunnable;
import sun.security.krb5.Config;
import uk.antiperson.stackmob.api.IStackMob;
import uk.antiperson.stackmob.api.compat.PluginCompat;
import uk.antiperson.stackmob.api.tools.ConfigHelper;
import uk.antiperson.stackmob.compat.hooks.MythicMobsHook;
import uk.antiperson.stackmob.api.entity.StackTools;
import uk.antiperson.stackmob.api.tools.WorldTools;

/**
 * Created by nathat on 25/07/17.
 */
public class TagTask extends BukkitRunnable {

    private IStackMob sm;
    public TagTask(IStackMob sm){
        this.sm = sm;
    }

    public void run() {
        MythicMobsHook mobsHook = (MythicMobsHook) sm.getHookManager().getHook(PluginCompat.MYTHICMOBS);
        for (Entity e : WorldTools.getLoadedEntities()) {
            if (!ConfigHelper.getStringList(sm, "no-stack-worlds", e).contains(e.getWorld().getName())) {
                if(!(e instanceof Mob)){
                    continue;
                }
                if (StackTools.hasValidStackData(e)) {
                    String typeString = e.getType().toString();

                    int stackSize = StackTools.getSize(e);
                    int removeAt = ConfigHelper.getInt(sm,"tag.remove-at", e);

                    if (stackSize > removeAt) {
                        String format = ConfigHelper.getString(sm, "tag.format", e);

                        // Change if it is a mythic mob.
                        if (sm.getHookManager().isHookRegistered(PluginCompat.MYTHICMOBS) && mobsHook.isMythicMob(e)) {
                            typeString = mobsHook.getDisplayName(e);
                        } else if (sm.getCustomConfig().getBoolean("tag.use-translations")) {
                            typeString = "" + sm.getTranslationConfig().getString(e.getType().toString());
                        }

                        String formattedType = WordUtils.capitalizeFully(typeString.replaceAll("[^A-Za-z0-9]", " "));
                        String nearlyFinal = StringUtils.replace(StringUtils.replace(StringUtils.replace(format,
                                "%bukkit_type%", e.getType().toString()),
                                "%type%", formattedType),
                                "%size%", String.valueOf(stackSize));
                        String finalString = ChatColor.translateAlternateColorCodes('&', nearlyFinal);
                        if(!finalString.equals(e.getCustomName())){
                             e.setCustomName(finalString);
                        }

                        if(!(sm.getHookManager().isHookRegistered(PluginCompat.PROCOTOLLIB))){
                            boolean alwaysVisible = ConfigHelper.getBoolean(sm, "tag.always-visible", e);
                            e.setCustomNameVisible(alwaysVisible);
                        }
                    }
                }

            }
        }
    }

}
