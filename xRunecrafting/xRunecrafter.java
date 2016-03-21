package xRunecrafting;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Timer;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.events.MessageEvent;
import org.rev317.min.api.events.listeners.MessageListener;
import org.rev317.min.api.methods.Skill;

@ScriptManifest(author = "Xunn", category = Category.RUNECRAFTING, description = "Supports all Altars inside the Abyss + Teletab support. Start at the designated trainingspot", name = "xRunecrafter", servers = { "PKHonor" }, version = 0.3)
public class xRunecrafter extends Script implements Paintable, MessageListener {

    public static boolean guiWait = true;
    public static Timer timer;

    private final Color color1 = new Color(78, 72, 54);
    private final Font font1 = new Font("Calibri", 0, 23);
    private final Image img1 = Methods.getImage("http://i.imgur.com/vv1zQLA.png");

    private final ArrayList<Strategy> strategies = new ArrayList<Strategy>();

    @Override
    public boolean onExecute() {
	timer = new Timer();
	Variables.startExperience = Skill.RUNECRAFTING.getExperience();
	Variables.playerName = Methods.getPlayerName();
	strategies.add(new Studying());
	strategies.add(new Crafting());
	strategies.add(new Banking());
	provide(strategies);
	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		try {
		    final xRunecrafterGui frame = new xRunecrafterGui();
		    frame.setVisible(true);
		} catch (final Exception e) {
		    e.printStackTrace();
		}
	    }
	});
	while (guiWait) {
	    sleep(100);
	}
	return true;
    }

    @Override
    public void onFinish() {
	System.out.println("--------------------------------------");
	System.out.println("xRunecrafter ran for: " + timer.toString());
	System.out.println("We crafted " + Variables.resourceCounter + " rune's");
	System.out.println("Thank you for using xRunecrafter");
	System.out.println("--------------------------------------");
    }

    @Override
    public void messageReceived(final MessageEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void paint(final Graphics g) {
	g.drawImage(img1, 232, 307, null);
	g.setFont(font1);
	g.setColor(color1);
	g.drawString(timer.toString(), 377, 398);
	g.drawString(Variables.status, 377, 429);
	g.drawString(
		(Methods.format((timer.getPerHour(Skill.RUNECRAFTING.getExperience() - Variables.startExperience)))),
		377, 464);

    }
}