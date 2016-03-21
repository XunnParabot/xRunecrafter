package xRunecrafting;

import java.awt.Image;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;

import javax.imageio.ImageIO;

import org.rev317.min.Loader;
import org.rev317.min.api.methods.Game;
import org.rev317.min.api.methods.Inventory;

public class Methods {

    public static String idToName(final int id) {
	final String[] altarNames = { "Soul", "Death", "Chaos", "Nature", "Law", "Cosmic", "Body", "Fire", "Earth",
		"Water", "Mind", "Air" };
	final int[] altarIDS = { 2489, 2488, 2487, 2486, 2485, 2484, 2483, 2482, 2481, 2480, 2479, 2478 };
	for (int i = 0; i < altarIDS.length; i++) {
	    if (altarIDS[i] == id) {
		return altarNames[i];
	    }
	}
	return "null";
    }

    public static String getPlayerName() {
	try {
	    final Field field = Loader.getClient().getClass().getDeclaredField("hR");
	    field.setAccessible(true);
	    Object value = null;
	    try {
		value = field.get(Loader.getClient());
	    } catch (IllegalArgumentException | IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    String s = value.toString();
	    s = s.substring(0, 1).toUpperCase() + s.substring(1);
	    return s;
	} catch (final NoSuchFieldException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public static boolean bankContains(final int id) {
	final int[] bankItems = Loader.getClient().getInterfaceCache()[5382].getItems();
	for (final int item : bankItems) {
	    if (item == id) {
		return true;
	    }
	}
	return false;
    }

    public static int getBankSlot(final int id) {
	final int[] bankIds = Loader.getClient().getInterfaceCache()[5382].getItems();
	for (int i = 0; i < bankIds.length; i++) {
	    if (bankIds[i] == id) {
		return i;
	    }
	}
	return 0;
    }

    public static int getTotalStackSize(final int[] x) {
	int t = 0;
	if (Inventory.getItems(x) != null) {
	    int i;
	    final int l = Inventory.getItems(x).length;
	    for (i = 0; l - i != 0; i++) {
		t += Inventory.getItems(x)[i].getStackSize();
	    }
	}
	return t;
    }

    public static String format(final int num) {
	String POSTFIX = "";
	int NUM = num;
	if (num > 1000000) {
	    NUM = num / 1000000;
	    POSTFIX = "m";
	} else if (num > 1000 && num < 1000000) {
	    NUM = num / 1000;
	    POSTFIX = "k";
	}
	return NUM + POSTFIX;
    }

    // START: Code generated using Enfilade's Easel
    public static Image getImage(final String url) {
	try {
	    return ImageIO.read(new URL(url));
	} catch (final IOException e) {
	    return null;
	}
    }

    public static boolean isOpenInterface(final int interfaceid) {
	return Game.getOpenInterfaceId() == interfaceid;
    }
}
