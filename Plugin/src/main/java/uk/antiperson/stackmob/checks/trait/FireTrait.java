package uk.antiperson.stackmob.checks.trait;

import org.bukkit.entity.Entity;
import uk.antiperson.stackmob.api.checks.ITraitManager;
import uk.antiperson.stackmob.api.checks.ApplicableTrait;
import uk.antiperson.stackmob.api.tools.ConfigHelper;

public class FireTrait implements ApplicableTrait {

    private ITraitManager traitManager;
    public FireTrait(ITraitManager traitManager){
        this.traitManager = traitManager;
    }

    @Override
    public String getConfigPath() {
        return "fire-ticks.enabled";
    }

    @Override
    public boolean checkTrait(Entity original, Entity nearby) {
        return false;
    }

    @Override
    public void applyTrait(Entity original, Entity spawned) {
        if(ConfigHelper.getStringList(traitManager.getStackMob(), "fire-ticks.blacklist", original)
                .contains(original.getType().toString())){
            return;
        }
        spawned.setFireTicks(original.getFireTicks());
    }
}
