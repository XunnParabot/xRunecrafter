package xRunecrafting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.parabot.core.Context;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.api.utils.Timer;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.Loader;
import org.rev317.min.api.events.MessageEvent;
import org.rev317.min.api.events.listeners.MessageListener;
import org.rev317.min.api.methods.Bank;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.methods.Skill;
import org.rev317.min.api.wrappers.SceneObject;

@ScriptManifest(author = "Xunn", category = Category.RUNECRAFTING, description = "...", name = "xRunecrafter", servers = { "PKHonor" }, version = 0.004)
public class xRunecrafter extends Script implements Paintable, MessageListener {

    public boolean guiWait = true;
    private final Color color1 = new Color(Color.green.hashCode());
    private final Font font1 = new Font("Calibri", 0, 15);

    public Timer timer;
    public int startExperience;
    public String status = "";
    public String altar = "";
    public int resourceCounter = 0;
    public String playerName = "";

    public final int[] altarIDs = { 2478, 2479, 2480, 2481, 2482, 2483, 2484, 2485, 2486, 2487, 2488, 2489 };
    public final int[] resources = { 555, 556, 557, 558, 559, 560, 561, 562, 563, 564, 565, 566, 567, 9076 };
    public final String[] resourceNames = { "Fire", "Water", "Air","Earth","Mind","Body","Death","Nature","Chaos","Law", "Cosmic","Blood","Astral"};
    public final int[] essence = { 1437, 7937 };

    public final int depositBox = 9398;
    public final int closedChest = 3193;
    public final int bankBooth = 2213;

    public final int[] bankObject = { depositBox, closedChest, bankBooth };

    private final ArrayList<Strategy> strategies = new ArrayList<Strategy>();

    @Override
    public boolean onExecute() {
	timer = new Timer();
	startExperience = Skill.RUNECRAFTING.getExperience();
	playerName = getPlayerName();
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
    
    public String idToName(int id){
	    String[] altarNames = {"Soul", "Death", "Chaos","Nature","Law","Cosmic","Body","Fire","Earth", "Water","Mind","Air"};
	    int[] altarIDS = {2489,2488,2487,2486,2485,2484,2483,2482,2481,2480,2479,2478};
	   for(int i = 0; i < altarIDS.length; i++){
	    if(altarIDS[i] == id){
		return altarNames[i];
	    }
	}
    return "null";
	}

    
    public static String getPlayerName() {
	try {
            Field field = Loader.getClient().getClass().getDeclaredField("hR");
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
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
 

    @Override
    public void onFinish() {
	System.out.println("--------------------------------------");
	System.out.println("xRunecrafter ran for: " + timer.toString());
	System.out.println("We crafted " + resourceCounter + " rune's");
	System.out.println("Thank you for using xRunecrafter");
	System.out.println("--------------------------------------");
    }

    @SuppressWarnings("serial")
    public class xRunecrafterGui extends JFrame {

	private final JPanel contentPane;

	public xRunecrafterGui() {

	    setTitle("xRunecrafter - "+playerName);
	    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	    setBounds(100, 100, 340, 195);
	    contentPane = new JPanel();
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    contentPane.setLayout(new BorderLayout(0, 0));
	    setContentPane(contentPane);

	    final JPanel panelUI = new JPanel();
	    panelUI.setBorder(new EmptyBorder(0, 0, 0, 0));
	    contentPane.add(panelUI, BorderLayout.WEST);

	    final JLabel lblXminer = new JLabel("xRunecrafter");
	    lblXminer.setHorizontalAlignment(SwingConstants.CENTER);

	    final JButton btnStart = new JButton("Start");

	    btnStart.setToolTipText("Press to Start!");

	    final JButton btnStop = new JButton("Stop");
	    btnStop.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(final MouseEvent m) {
		    if (btnStop.getText().matches("Stop")) {
			Context.getInstance().getRunningScript().setState(Script.STATE_STOPPED);
			dispose();
		    }
		}
	    });

	    final GroupLayout gl_panelUI = new GroupLayout(panelUI);
	    gl_panelUI.setHorizontalGroup(gl_panelUI.createParallelGroup(Alignment.LEADING).addGroup(
		    gl_panelUI
			    .createSequentialGroup()
			    .addGroup(
				    gl_panelUI
					    .createParallelGroup(Alignment.LEADING)
					    .addGroup(
						    Alignment.TRAILING,
						    gl_panelUI
							    .createSequentialGroup()
							    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							    .addComponent(lblXminer, GroupLayout.PREFERRED_SIZE, 89,
								    GroupLayout.PREFERRED_SIZE))

					    .addGroup(
						    Alignment.TRAILING,
						    gl_panelUI
							    .createSequentialGroup()
							    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							    .addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 99,
								    GroupLayout.PREFERRED_SIZE))
					    .addGroup(
						    Alignment.TRAILING,
						    gl_panelUI
							    .createSequentialGroup()
							    .addContainerGap()
							    .addComponent(btnStop, GroupLayout.DEFAULT_SIZE, 99,
								    Short.MAX_VALUE))).addContainerGap()));
	    gl_panelUI.setVerticalGroup(gl_panelUI.createParallelGroup(Alignment.LEADING).addGroup(
		    gl_panelUI.createSequentialGroup().addGap(24).addComponent(lblXminer)
			    .addPreferredGap(ComponentPlacement.RELATED)

			    .addPreferredGap(ComponentPlacement.RELATED).addComponent(btnStart)
			    .addPreferredGap(ComponentPlacement.RELATED).addComponent(btnStop)
			    .addContainerGap(15, Short.MAX_VALUE)));
	    panelUI.setLayout(gl_panelUI);

	    final JPanel panelStatistics = new JPanel();
	    contentPane.add(panelStatistics, BorderLayout.CENTER);
	    panelStatistics.setLayout(new GridLayout(8, 2, 0, 0));

	    final JLabel lblName = new JLabel("Name:");
	    lblName.setFont(new Font("Tahoma", Font.BOLD, 11));
	    panelStatistics.add(lblName);

	    final JLabel lblNamedata = new JLabel("null");
	    panelStatistics.add(lblNamedata);

	    final JLabel lblTime = new JLabel("Time:");
	    lblTime.setFont(new Font("Tahoma", Font.BOLD, 11));
	    panelStatistics.add(lblTime);

	    final JLabel lblTimedata = new JLabel("null");
	    panelStatistics.add(lblTimedata);

	    final JLabel lblStatus = new JLabel("Status:");
	    lblStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
	    panelStatistics.add(lblStatus);

	    final JLabel lblStatusdata = new JLabel("null");
	    panelStatistics.add(lblStatusdata);

	    final JLabel lblPlayersAround = new JLabel("Players Around:");
	    lblPlayersAround.setFont(new Font("Tahoma", Font.BOLD, 11));
	    panelStatistics.add(lblPlayersAround);

	    final JLabel lblPlayersArounddata = new JLabel("null");
	    panelStatistics.add(lblPlayersArounddata);

	    final JLabel lblRock = new JLabel("Altar:");
	    lblRock.setFont(new Font("Tahoma", Font.BOLD, 11));
	    panelStatistics.add(lblRock);

	    final JLabel lblResourcedata = new JLabel("null");
	    panelStatistics.add(lblResourcedata);

	    final JLabel lblRockdat = new JLabel("Resources:");
	    lblRockdat.setFont(new Font("Tahoma", Font.BOLD, 11));
	    panelStatistics.add(lblRockdat);

	    final JLabel lblResourceCount = new JLabel("null");
	    panelStatistics.add(lblResourceCount);

	    final JLabel lblResourceh = new JLabel("Resource/h:");
	    lblResourceh.setFont(new Font("Tahoma", Font.BOLD, 11));
	    panelStatistics.add(lblResourceh);

	    final JLabel lblResourcehdata = new JLabel("null");
	    panelStatistics.add(lblResourcehdata);

	    final JLabel lblExperienceh = new JLabel("Experience/h:");
	    lblExperienceh.setFont(new Font("Tahoma", Font.BOLD, 11));
	    panelStatistics.add(lblExperienceh);

	    final JLabel lblExperiencedata = new JLabel("null");
	    panelStatistics.add(lblExperiencedata);

	    final ActionListener timerAction = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent ae) {
		    lblTimedata.setText(timer.toString());
		    lblNamedata.setText(playerName);
		    lblStatusdata.setText(status);
		    lblPlayersArounddata.setText("" + (Players.getNearest().length - 1));
		    lblResourcedata.setText(altar);
		    lblResourceCount.setText(format(resourceCounter));
		    lblResourcehdata.setText(format(timer.getPerHour(resourceCounter)));
		    lblExperiencedata.setText(format(timer.getPerHour(Skill.RUNECRAFTING.getExperience()
			    - startExperience)));
		}
	    };
	    final javax.swing.Timer guiTimer = new javax.swing.Timer(1000, timerAction);

	    btnStart.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(final MouseEvent m) {
		    if (btnStart.getText().matches("Start")) {
			guiTimer.start();
			guiWait = false;
			btnStart.setText("Pause");
			btnStart.setToolTipText("Press to Pause!");
		    } else if (btnStart.getText().matches("Pause")) {
			Context.getInstance().getRunningScript().setState(Script.STATE_PAUSE);
			btnStart.setText("Resume");
			btnStart.setToolTipText("Press to Resume!");

		    } else if (btnStart.getText().matches("Resume")) {
			Context.getInstance().getRunningScript().setState(Script.STATE_RUNNING);
			btnStart.setText("Pause");
			btnStart.setToolTipText("Press to Pause!");
		    }
		}
	    });
	}
    }

    public class Crafting implements Strategy {

	@Override
	public boolean activate() {
	    SceneObject[] i = null;
	    try {
		i = SceneObjects.getNearest(altarIDs);
	    } catch (final NullPointerException e) {
	    }
	    return i != null && i.length > 0 && Inventory.containts(essence);
	}

	@Override
	public void execute() {
	    status = "Crafting";
	    SceneObject[] i = null;
	    try {
		i = SceneObjects.getNearest(altarIDs);
	    } catch (final NullPointerException e) {
		System.out.println("SceneObjects.getNearest retured an error");
	    }
	    altar = idToName(i[0].getId());
	    i[0].interact(0);
	    Time.sleep(new SleepCondition() {
		@Override
		public boolean isValid() {
		    return !Inventory.containts(essence);
		}

	    }, 5000);

	}

    }

    public class Banking implements Strategy {

	private boolean bankContains(final int id) {
	    final int[] bankIds = Loader.getClient().getInterfaceCache()[5382].getItems();
	    for (int i = 0; i < bankIds.length; i++) {
		if (bankIds[i] == id) {
		    return true;
		}
	    }
	    return false;
	}

	private int getBankSlot(final int id) {
	    final int[] bankIds = Loader.getClient().getInterfaceCache()[5382].getItems();
	    for (int i = 0; i < bankIds.length; i++) {
		if (bankIds[i] == id) {
		    return i;
		}
	    }
	    return 0;
	}
	
	
	@Override
	public boolean activate() {
	    SceneObject[] i = null;
	    try {
		i = SceneObjects.getNearest(bankObject);
	    } catch (final NullPointerException e) {
	    }
	    return !Inventory.containts(essence) && i != null && i.length > 0;
	}

	@Override
	public void execute() {
	    status = "Banking";
	    if (!Bank.isOpen() && !Inventory.containts(essence)) {
		resourceCounter += (TotalStackSize(resources) - resourceCounter);
		status = "Opening Bank";
		SceneObject[] i = null;
		try {
		    i = SceneObjects.getNearest(bankObject);
		} catch (final NullPointerException e) {
		    System.out.println("SceneObjects.getNearest retured an error");
		}
		i[0].interact(0);
		Time.sleep(new SleepCondition() {
		    @Override
		    public boolean isValid() {
			return Bank.isOpen();
		    }

		}, 3000);

	    } else if (Bank.isOpen() && !Inventory.containts(essence)) {
		status = "Withdrawing";
		if (bankContains(essence[0])) {
		    Menu.sendAction(53, essence[0] - 1, getBankSlot(essence[0]), 5382, 3);
		    Time.sleep(new SleepCondition() {
			@Override
			public boolean isValid() {
			    return Inventory.containts(essence);
			}

		    }, 2000);
		} else if (bankContains(essence[1])) {
		    Menu.sendAction(53, essence[1] - 1, getBankSlot(essence[1]), 5382, 3);
		    Time.sleep(new SleepCondition() {
			@Override
			public boolean isValid() {
			    return Inventory.containts(essence);
			}

		    }, 2000);
		}
	    }
	}
    }

    public int TotalStackSize(final int[] x) {
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

    @Override
    public void messageReceived(final MessageEvent arg0) {
	// TODO Auto-generated method stub

    }

    private String format(final int num) {
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

    @Override
    public void paint(final Graphics g) {
	g.setColor(color1);
	g.setFont(font1);
	g.drawString("Status: " + status, 10, 15);
	
    }
}