package xRunecrafting;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import org.parabot.environment.scripts.Script;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.Skill;

@SuppressWarnings("serial")
public class xRunecrafterGui extends JFrame {

    private final JPanel contentPane;

    public xRunecrafterGui() {

	setTitle("xRunecrafter - " + Variables.playerName);
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

	final JLabel lblRock = new JLabel("Making:");
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
		lblTimedata.setText(xRunecrafter.timer.toString());
		lblNamedata.setText(Variables.playerName);
		lblStatusdata.setText(Variables.status);
		lblPlayersArounddata.setText("" + (Players.getNearest().length - 1));
		lblResourcedata.setText(Variables.altar);
		lblResourceCount.setText(Methods.format(Variables.resourceCounter));
		lblResourcehdata.setText(Methods.format(xRunecrafter.timer.getPerHour(Variables.resourceCounter)));
		lblExperiencedata.setText(Methods.format(xRunecrafter.timer.getPerHour(Skill.RUNECRAFTING
			.getExperience() - Variables.startExperience)));
	    }
	};
	final javax.swing.Timer guiTimer = new javax.swing.Timer(1000, timerAction);

	btnStart.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(final MouseEvent m) {
		if (btnStart.getText().matches("Start")) {
		    guiTimer.start();
		    xRunecrafter.guiWait = false;
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