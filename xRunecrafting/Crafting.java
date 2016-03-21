package xRunecrafting;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.SceneObject;

public class Crafting implements Strategy {
    private final int[] ESSENCE = { 1437, 7937 };

    @Override
    public boolean activate() {
	SceneObject[] i = null;
	try {
	    i = SceneObjects.getNearest(Constants.altarIDs);
	} catch (final NullPointerException e) {
	}
	return i != null && i.length > 0 && Inventory.containts(ESSENCE);
    }

    @Override
    public void execute() {
	Variables.status = "Crafting";
	Variables.craftingRunes = true;
	SceneObject[] i = null;
	try {
	    i = SceneObjects.getNearest(Constants.altarIDs);
	} catch (final NullPointerException e) {
	    System.out.println("SceneObjects.getNearest retured an error");
	}
	Variables.altar = Methods.idToName(i[0].getId());
	i[0].interact(0);
	Time.sleep(new SleepCondition() {
	    @Override
	    public boolean isValid() {
		return !Inventory.containts(ESSENCE);
	    }

	}, 5000);

    }

}