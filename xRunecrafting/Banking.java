package xRunecrafting;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.SceneObject;

public class Banking implements Strategy {

    private final int[] ESSENCE = { 1437, 7937 };
    private final int SOFT_CLAY = 1762;
    private final int[] RESOURCES = { 1437, 7937, 1762 };
    private final int DEPOSITBOX = 9398;
    private final int CLOSED_CHEST = 3193;
    private final int BANKBOOTH = 2213;

    private final int[] BANK_OBJECT = { DEPOSITBOX, CLOSED_CHEST, BANKBOOTH };

    private final int BANK_INTERFACE = 23350;

    @Override
    public boolean activate() {
	SceneObject[] i = null;
	try {
	    i = SceneObjects.getNearest(BANK_OBJECT);
	} catch (final NullPointerException e) {
	}
	return !Inventory.containts(RESOURCES) && i != null && i.length > 0;
    }

    @Override
    public void execute() {
	Variables.status = "Banking";
	if (!Methods.isOpenInterface(BANK_INTERFACE) && !Inventory.containts(RESOURCES)) {
	    Variables.resourceCounter += (Methods.getTotalStackSize(Constants.resources) - Variables.resourceCounter);
	    Variables.status = "Opening Bank";
	    SceneObject[] i = null;
	    try {
		i = SceneObjects.getNearest(BANK_OBJECT);
	    } catch (final NullPointerException e) {
		System.out.println("SceneObjects.getNearest retured an error");
	    }
	    i[0].interact(0);
	    Time.sleep(new SleepCondition() {
		@Override
		public boolean isValid() {
		    return Methods.isOpenInterface(BANK_INTERFACE);
		}

	    }, 3000);

	} else if (Methods.isOpenInterface(BANK_INTERFACE) && !Inventory.containts(RESOURCES)) {
	    Variables.status = "Withdrawing";
	    if (Variables.craftingRunes == true) {
		if (Methods.bankContains(ESSENCE[0])) {
		    Menu.sendAction(53, ESSENCE[0] - 1, Methods.getBankSlot(ESSENCE[0]), 5382, 3);
		    Time.sleep(new SleepCondition() {
			@Override
			public boolean isValid() {
			    return Inventory.containts(ESSENCE);
			}

		    }, 2000);
		} else if (Methods.bankContains(ESSENCE[1])) {
		    Menu.sendAction(53, ESSENCE[1] - 1, Methods.getBankSlot(ESSENCE[1]), 5382, 3);
		    Time.sleep(new SleepCondition() {
			@Override
			public boolean isValid() {
			    return Inventory.containts(ESSENCE);
			}

		    }, 2000);
		}
	    } else if (Variables.craftingRunes == false) {
		System.out.println("Withdrawing soft Clay");
		if (Methods.bankContains(SOFT_CLAY)) {
		    Menu.sendAction(53, SOFT_CLAY - 1, Methods.getBankSlot(SOFT_CLAY), 5382, 3);
		    Time.sleep(new SleepCondition() {
			@Override
			public boolean isValid() {
			    return Inventory.containts(SOFT_CLAY);
			}

		    }, 2000);
		}
	    }
	}
    }
}