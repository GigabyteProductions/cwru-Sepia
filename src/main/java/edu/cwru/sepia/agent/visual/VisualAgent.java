/**
 *  Strategy Engine for Programming Intelligent Agents (SEPIA)
    Copyright (C) 2012 Case Western Reserve University

    This file is part of SEPIA.

    SEPIA is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SEPIA is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SEPIA.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.cwru.sepia.agent.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import javax.swing.SwingUtilities;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.cwru.sepia.action.Action;
import edu.cwru.sepia.agent.Agent;
import edu.cwru.sepia.environment.model.history.History.HistoryView;
import edu.cwru.sepia.environment.model.state.ResourceType;
import edu.cwru.sepia.environment.model.state.State.StateView;
import edu.cwru.sepia.experiment.ExampleRunner;

/**
 * A visual agent
 * This serves two purposes: it allows a human to play the game, and, more importantly, 
 * it allows one to look at what the agent is doing through its effect on the state.
 *
 */
public class VisualAgent extends Agent implements ActionListener {

	private static final long serialVersionUID = 1L;

	transient Map<Integer, Action> actions;
	GameScreen screen;
    GamePanel gamePanel;
    ControlPanel controlPanel = new ControlPanel();
    StatusPanel statusPanel = new StatusPanel();
	private final Semaphore stepSignal = new Semaphore(0);

	
	protected final boolean humanControllable;
	protected final boolean infoVis;
	
	public VisualAgent(int playernum, String[] otherargs) {
		super(playernum);
		humanControllable = Boolean.parseBoolean(otherargs[0]);
		infoVis = Boolean.parseBoolean(otherargs[1]);
		setupScreen();
	}
	GamePanel createGamePanel() {
		return new GamePanel(this, new DefaultGameDrawer());
	}
	void setupScreen() {
		gamePanel = createGamePanel();
		actions = new HashMap<Integer, Action>();
		final KeyAdapter canvasKeyListener = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
//				System.out.println(e.getKeyCode());
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					stepSignal.drainPermits();
					stepSignal.release();
				}
			}
		};
		Runnable runner = new Runnable() {
			@Override
			public void run() {
				screen = new GameScreen(gamePanel, controlPanel, statusPanel);
                screen.pack();
				gamePanel.addKeyListener(canvasKeyListener);
				controlPanel.addStepperListener(VisualAgent.this);
			}					
		};
		SwingUtilities.invokeLater(runner);
	}
	/**
	 * 
	 * @param playerNum The player number that this agent watches
	 * @param humanControllable Whether you can use the visual interface to control units.
	 */
	public VisualAgent(int playerNum, boolean humanControllable) {
		//Always sets infoVis to true, since there doesn't seem to be a good reason to turn it off
		super(playerNum);
		this.humanControllable = humanControllable;
		infoVis=true;
		setupScreen();
	}

	@Override
	public Map<Integer, Action> initialStep(StateView newstate, HistoryView statehistory) {
		if(gamePanel!=null)
			gamePanel.reset();
        return middleStep(newstate, statehistory);
	}

	@Override
	public Map<Integer, Action> middleStep(StateView newstate, HistoryView statehistory) {
		refreshStatusPanel("#clear");
		refreshStatusPanel(getStateInfo(newstate));
		if(gamePanel != null)
			gamePanel.updateState(newstate, statehistory);
		try {
			stepSignal.acquire();
		} catch (InterruptedException e) {
			System.err.println("Unable to wait for step button to be pressed.");
		}
		Map<Integer, Action> toReturn = actions;
		actions = new HashMap<Integer, Action>();
		return toReturn;
	}

	@Override
	public void terminalStep(StateView newstate, HistoryView statehistory) {
		if(gamePanel != null) 
			gamePanel.updateState(newstate, statehistory);
		if(controlPanel!=null)
			controlPanel.stopPlay();
		//JOptionPane.showMessageDialog(null, "Congratulations! You finished the task!");
		writeLineVisual("=======> You've finished current episode!");
	}
	
	public void addAction(Action action) {
		actions.put(action.getUnitId(),action);
	}
	
    @Override
    public void actionPerformed(ActionEvent e) {
        stepSignal.drainPermits();
        stepSignal.release();
    }
    
    private String getStateInfo(StateView state) {
		int currentGold = state.getResourceAmount(0, ResourceType.GOLD);
		int currentWood = state.getResourceAmount(0, ResourceType.WOOD);
		int currentFood = state.getSupplyAmount(playernum);
		StringBuffer sb = new StringBuffer();
		sb.append("Current Gold:\n" + 
					"  " + currentGold + "\n" + 
					"Current Wood:\n" + 
					"  " + currentWood + "\n" + 
					"Current Food:\n" + 
					"  " + currentFood + "\n");
		sb.append("Current Turn:\n"+" " + state.getTurnNumber()+"\n");
		return sb.toString();
    }
    
    public void refreshStatusPanel(String status) {
    	if(statusPanel==null)
    		return ;
    	if(status.startsWith("#clear"))
    		statusPanel.clear();
    	else {
    		statusPanel.append(status);
    	}
    }

	public static String getUsage() {
		return "It takes two parameters (--agentparam): a boolean for whether it can be controlled by human, a boolean for whether it visualizes info for units";
	}
	@Override
	public void savePlayerData(OutputStream os) {
		//this agent lacks learning and so has nothing to persist.
		
	}
	@Override
	public void loadPlayerData(InputStream is) {
		//this agent lacks learning and so has nothing to persist.
	}
}
