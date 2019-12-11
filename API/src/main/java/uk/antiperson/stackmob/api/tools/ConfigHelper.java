package uk.antiperson.stackmob.api.tools;

import uk.antiperson.stackmob.api.IStackMob;
import org.bukkit.entity.Entity;

import java.util.List;

// Helper Class - checks both default and custom entity pathing for values
public class ConfigHelper {
    public final static String CUSTOM_PREFIX = "custom.";
    public static int getInt(IStackMob sm, String path, Entity entity) {
        String customPath = getCustomPath(path, entity);
        int value = sm.getCustomConfig().getInt(path);
        if (sm.getCustomConfig().isInt(customPath))
            value = sm.getCustomConfig().getInt(customPath);
        return value;
    }
    public static String getString(IStackMob sm, String path, Entity entity) {
        String customPath = getCustomPath(path, entity);
        String value = sm.getCustomConfig().getString(path);
        if (sm.getCustomConfig().contains(customPath))
            value = sm.getCustomConfig().getString(customPath);
        return value;
    }
    public static List<String> getStringList(IStackMob sm, String path, Entity entity) {
        String customPath = getCustomPath(path, entity);
        List<String> value = sm.getCustomConfig().getStringList(path);
        if (sm.getCustomConfig().contains(customPath))
            value = sm.getCustomConfig().getStringList(customPath);
        return value;
    }
    public static Boolean getBoolean(IStackMob sm, String path, Entity entity) {
        String customPath = getCustomPath(path, entity);
        Boolean value = sm.getCustomConfig().getBoolean(path);
        if (sm.getCustomConfig().contains(customPath))
            value = sm.getCustomConfig().getBoolean(customPath);
        return value;
    }
    private static String getCustomPath(String path, Entity entity) {
        return CUSTOM_PREFIX + entity.getType() + "." + path;
    }
}
