package xRunecrafting;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Game;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.SceneObject;

public class Studying implements Strategy {

    private final int LAW_RUNES = 564;
    private final int AIR_RUNES = 557;
    private final int SOFT_CLAY = 1762;
    private final int LECTERN = 13648;
    private final int MAKE_DIALOG = 2492;

    @Override
    public boolean activate() {
	SceneObject[] i = null;
	try {
	    i = SceneObjects.getNearest(LECTERN);
	} catch (final NullPointerException e) {
	}
	return i != null && i.length > 0 && Inventory.containts(LAW_RUNES) && Inventory.containts(AIR_RUNES)
		&& Inventory.containts(SOFT_CLAY);
    }

    @Override
    public void execute() {
	Variables.craftingRunes = false;
	Variables.status = "Studying";
	if (Game.getOpenBackDialogId() != MAKE_DIALOG) {
	    SceneObject[] i = null;
	    try {
		i = SceneObjects.getNearest(LECTERN);
	    } catch (final NullPointerException e) {
		System.out.println("SceneObjects.getNearest retured an error");
	    }
	    Variables.altar = "Teletabs";
	    i[0].interact(0);
	    Time.sleep(new SleepCondition() {
		@Override
		public boolean isValid() {
		    return Game.getOpenBackDialogId() == MAKE_DIALOG;
		}

	    }, 5000);
	} else if (Game.getOpenBackDialogId() == MAKE_DIALOG) {
	    Menu.sendAction(315, 1761, -1, 2496, 1);
	    Time.sleep(new SleepCondition() {
		@Override
		public boolean isValid() {
		    return Inventory.getCount() < 4;
		}

	    }, 5000);

	}
    }
}
