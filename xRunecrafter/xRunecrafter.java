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
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
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
import org.rev317.min.api.events.MessageEvent;
import org.rev317.min.api.events.listeners.MessageListener;
import org.rev317.min.api.methods.Bank;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.methods.Skill;
import org.rev317.min.api.wrappers.SceneObject;

@ScriptManifest(
author = "Xunn", 
category = Category.RUNECRAFTING, 
description = "...", 
name = "xRunecrafter", 
servers = {"PKHonor"}, 
version = 0.002)

public class xRunecrafter extends Script implements Paintable, MessageListener {
 
    public boolean guiWait = true;
    private final Color color1 = new Color(Color.green.hashCode());
   	private final Font font1 = new Font("Calibri", 0, 15);

    public Timer timer;
    public int startExperience;
	public String status = "null";
	public String altar = "null";
	public int resourceCounter = 0;
	public String playerName = "Raymond Reddington";

	public final int[] altarIDs = {2478,2479,2480,2481,2482,2483,2484,2485,2486,2487,2488,2489};
	public final int[] resources = {555,556,557,558,559,560,561,562,563,564,565,566,567};
	public final int[] essence = {1437,7937};
	
	
	public final int depositBox = 9398;
	public final int closedChest = 3193;
	public final int bankBooth = 2213;
	
	public final int[] bankObject = {depositBox,closedChest, bankBooth};
	
	private final ArrayList<Strategy> strategies = new ArrayList<Strategy>();
	public boolean onExecute() {
	timer = new Timer();
	startExperience = Skill.RUNECRAFTING.getExperience();
	//playerName = Players.getMyPlayer();
	System.out.println(playerName);
	strategies.add(new Crafting());
	strategies.add(new Banking());
	provide(strategies);
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				xRunecrafterGui frame = new xRunecrafterGui();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
	while(guiWait){
		sleep(100);
		}
	return true;
	}
	
	
	@Override
	public void onFinish(){ 
		System.out.println("--------------------------------------");
		System.out.println("xRunecrafter ran for: " + timer.toString());
		System.out.println("We crafted "+resourceCounter+" rune's");
		System.out.println("Thank you for using xRunecrafter");
		System.out.println("--------------------------------------");
	}
	
	@SuppressWarnings("serial")
	public class xRunecrafterGui extends JFrame {

		private JPanel contentPane;

		public xRunecrafterGui() {
			
			setTitle("xRunecrafter GUI");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 340, 195);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);
			
			JPanel panelUI = new JPanel();
			panelUI.setBorder(new EmptyBorder(0, 0, 0, 0));
			contentPane.add(panelUI, BorderLayout.WEST);
			
			JLabel lblXminer = new JLabel("xRunecrafter");
			lblXminer.setHorizontalAlignment(SwingConstants.CENTER);
			
			JButton btnStart = new JButton("Start");
			
			btnStart.setToolTipText("Press to Start!");
			
			JButton btnStop = new JButton("Stop");
			btnStop.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent m) {
					if(btnStop.getText().matches("Stop")){
						Context.getInstance().getRunningScript().setState(Script.STATE_STOPPED);
						dispose();
					}
				}
			});
		
			GroupLayout gl_panelUI = new GroupLayout(panelUI);
			gl_panelUI.setHorizontalGroup(
				gl_panelUI.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelUI.createSequentialGroup()
						.addGroup(gl_panelUI.createParallelGroup(Alignment.LEADING)
							.addGroup(Alignment.TRAILING, gl_panelUI.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblXminer, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
							
							.addGroup(Alignment.TRAILING, gl_panelUI.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
							.addGroup(Alignment.TRAILING, gl_panelUI.createSequentialGroup()
								.addContainerGap()
								.addComponent(btnStop, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)))
						.addContainerGap())
			);
			gl_panelUI.setVerticalGroup(
				gl_panelUI.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelUI.createSequentialGroup()
						.addGap(24)
						.addComponent(lblXminer)
						.addPreferredGap(ComponentPlacement.RELATED)
						
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnStart)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnStop)
						.addContainerGap(15, Short.MAX_VALUE))
			);
			panelUI.setLayout(gl_panelUI);
					
			JPanel panelStatistics = new JPanel();
			contentPane.add(panelStatistics, BorderLayout.CENTER);
			panelStatistics.setLayout(new GridLayout(8, 2, 0, 0));
			
			JLabel lblName = new JLabel("Name:");
			lblName.setFont(new Font("Tahoma", Font.BOLD, 11));
			panelStatistics.add(lblName);
			
			JLabel lblNamedata = new JLabel("null");
			panelStatistics.add(lblNamedata);
			
			JLabel lblTime = new JLabel("Time:");
			lblTime.setFont(new Font("Tahoma", Font.BOLD, 11));
			panelStatistics.add(lblTime);
			
			JLabel lblTimedata = new JLabel("null");
			panelStatistics.add(lblTimedata);
			
			JLabel lblStatus = new JLabel("Status:");
			lblStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
			panelStatistics.add(lblStatus);
			
			JLabel lblStatusdata = new JLabel("null");
			panelStatistics.add(lblStatusdata);
			
			JLabel lblPlayersAround = new JLabel("Players Around:");
			lblPlayersAround.setFont(new Font("Tahoma", Font.BOLD, 11));
			panelStatistics.add(lblPlayersAround);
			
			JLabel lblPlayersArounddata = new JLabel("null");
			panelStatistics.add(lblPlayersArounddata);
			
			JLabel lblRock = new JLabel("Altar:");
			lblRock.setFont(new Font("Tahoma", Font.BOLD, 11));
			panelStatistics.add(lblRock);
			
			JLabel lblResourcedata = new JLabel("null");
			panelStatistics.add(lblResourcedata);
			
			JLabel lblRockdat = new JLabel("Resources:");
			lblRockdat.setFont(new Font("Tahoma", Font.BOLD, 11));
			panelStatistics.add(lblRockdat);
			
			JLabel lblResourceCount = new JLabel("null");
			panelStatistics.add(lblResourceCount);
			
			JLabel lblResourceh = new JLabel("Resource/h:");
			lblResourceh.setFont(new Font("Tahoma", Font.BOLD, 11));
			panelStatistics.add(lblResourceh);
			
			JLabel lblResourcehdata = new JLabel("null");
			panelStatistics.add(lblResourcehdata);
			
			JLabel lblExperienceh = new JLabel("Experience/h:");
			lblExperienceh.setFont(new Font("Tahoma", Font.BOLD, 11));
			panelStatistics.add(lblExperienceh);
			
			JLabel lblExperiencedata = new JLabel("null");
			panelStatistics.add(lblExperiencedata);
			
			ActionListener timerAction = new ActionListener()
		    {
		        public void actionPerformed(ActionEvent ae)
		        {
		        	lblTimedata.setText(timer.toString());
		        	lblNamedata.setText(playerName);
		        	lblStatusdata.setText(status);
		        	lblPlayersArounddata.setText(""+(Players.getNearest().length-1));
		        	lblResourcedata.setText("null"); //TODO
		        	lblResourceCount.setText(format(resourceCounter));
		            lblResourcehdata.setText(format(timer.getPerHour(resourceCounter)));
		            lblExperiencedata.setText(format(timer.getPerHour(Skill.RUNECRAFTING.getExperience()-startExperience)));
		        }
		    };
		    javax.swing.Timer guiTimer = new javax.swing.Timer(1000, timerAction);
		    
		    btnStart.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent m) {
					if(btnStart.getText().matches("Start")){
					guiTimer.start();
					guiWait = false;
					btnStart.setText("Pause");
					btnStart.setToolTipText("Press to Pause!");
					} else if (btnStart.getText().matches("Pause")){
						Context.getInstance().getRunningScript().setState(Script.STATE_PAUSE);
						btnStart.setText("Resume");
						btnStart.setToolTipText("Press to Resume!");
						
					} else if (btnStart.getText().matches("Resume")){
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
			try{
            	i = SceneObjects.getNearest(altarIDs);
            	}catch(NullPointerException e){}
			return i != null &&
					i.length > 0 &&
					Inventory.containts(essence);
		}

		@Override
		public void execute() {
			status = "Crafting";
			SceneObject[] i = null;
			try{
            	i = SceneObjects.getNearest(altarIDs);
            	}catch(NullPointerException e){
            		System.out.println("SceneObjects.getNearest retured an error");
            	}
			i[0].interact(0);
			Time.sleep(new SleepCondition(){
    			@Override
    			public boolean isValid(){
    				return !Inventory.containts(essence);
    			}
    			
    		},  5000);

		}

	}
	
	public class Banking implements Strategy {

	@Override
	public boolean activate() {
		SceneObject[] i = null;
		try{
        	i = SceneObjects.getNearest(bankObject);
        	}catch(NullPointerException e){}
		return !Inventory.containts(essence) && 
				i != null &&
				i.length > 0;
	}

	@Override
	public void execute() {
		status = "Banking";
		if(!Bank.isOpen() && !Inventory.containts(essence)){
		resourceCounter+=(TotalStackSize(resources)-resourceCounter);	
		status = "Opening Bank";
		SceneObject[] i = null;
		try{
        	i = SceneObjects.getNearest(bankObject);
        	}catch(NullPointerException e){
        		System.out.println("SceneObjects.getNearest retured an error");
        	}
		i[0].interact(0);
		Time.sleep(new SleepCondition(){
			@Override
			public boolean isValid(){
				return Bank.isOpen();
			}
			
		},  3000);
		} else if (Bank.isOpen() && !Inventory.containts(essence)){
		status = "Withdrawing";
		Menu.sendAction(53,-1,0,5382,3);
		Time.sleep(new SleepCondition(){
			@Override
			public boolean isValid(){
				return Inventory.containts(essence);
			}
			
		},  2000);
	} if (Bank.isOpen() && Inventory.containts(essence)){
		status = "Closing Bank";
		Bank.close();
	}
}

}
	public int TotalStackSize(int[] x){
		int t = 0;
		if(Inventory.getItems(x) != null){
			int i;
			int l = Inventory.getItems(x).length;
					for( i = 0; l-i !=0; i++){
			t+= Inventory.getItems(x)[i].getStackSize();
			}
		}
	return t;
}

	@Override
	public void messageReceived(MessageEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	private String format(int num){
		String POSTFIX = "";
		int NUM = num;
		if(num > 1000000){
			NUM =num/1000000;
			POSTFIX = "m";
		}else if(num > 1000 && num < 1000000){
			NUM = num/1000;
			POSTFIX = "k";
		}
		return NUM + POSTFIX;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(color1);
		g.setFont(font1);
		g.drawString("Status: "+status,10,15);
		g.drawString("State: "+Context.getInstance().getRunningScript().getState(), 10, 30);
	}
}