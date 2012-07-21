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
package edu.cwru.sepia.experiment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.cwru.sepia.agent.Agent;
import edu.cwru.sepia.environment.Environment;
import edu.cwru.sepia.environment.model.LessSimpleModel;
import edu.cwru.sepia.environment.model.Model;
import edu.cwru.sepia.environment.model.SimpleModel;
import edu.cwru.sepia.environment.model.state.State;
import edu.cwru.sepia.environment.model.state.StateCreator;
import edu.cwru.sepia.util.GameMap;

/**
 * A {@code Runner} that runs a number of episodes using {@code edu.cwru.sepia.model.LessSimpleModel}.
 * <br> Demonstrates some 
 *
 */
public class ExampleRunner extends Runner {
	private static final Logger logger = Logger.getLogger(ExampleRunner.class.getCanonicalName());

	public ExampleRunner(Configuration configuration, StateCreator stateCreator, Agent[] agents) {
		super(configuration, stateCreator, agents);
	}

	
	private int seed;
	private int numEpisodes;
	private int episodesPerReplaySave;
	private boolean saveReplays;
	private boolean saveAgents;
	private Environment env;
	private int episodesPerAgentSave;
	private File baseReplayDirectory;
	private File baseAgentDirectory;
	@Override
	public void run() {
		seed = 6;
		numEpisodes = ConfigurationValues.ENVIRONMENT_EPISODES.getIntValue(configuration);
		numEpisodes = configuration.getInt("environment.NumEpisodes", 12);
		episodesPerReplaySave = configuration.getInt("experiment.episodesperreplaysave",4);
		episodesPerAgentSave = configuration.getInt("experiment.episodesperagentsave",4);
		baseReplayDirectory = new File(configuration.getString("experiment.save.replaydirectory", "saves/replays"));
		baseAgentDirectory = new File(configuration.getString("experiment.save.agentdirectory", "saves/agents"));
		saveAgents = (episodesPerAgentSave>=1);
		saveReplays = (episodesPerReplaySave>=1);
		Model model = new LessSimpleModel(stateCreator.createState(), seed, stateCreator);
		
		env = new Environment(agents, model, seed);
		for(int episode = 0; episode < numEpisodes; episode++)
		{
			
			boolean saveAgentsThisEpisode = episodesPerAgentSave > 0 ? saveAgents && episode % episodesPerAgentSave == 0 : false;
			boolean saveReplayThisEpisode = episodesPerReplaySave > 0 ? saveReplays && episode % episodesPerReplaySave == 0 : false;
			
			State initialStateCopy=null;
			if(logger.isLoggable(Level.FINE))
				logger.fine("\n=======> Start running episode " + episode);
			try
			{
				//Manually implement the run
				
				//Make a new state and clear the history
				env.forceNewEpisode();
				if (saveReplayThisEpisode) {
					if(logger.isLoggable(Level.FINE))
						logger.fine("Saving replay for episode " + episode);
					//copy the initialstate in case the state creator is stochastic
					try {
					initialStateCopy = env.getModel().getState().getView(Agent.OBSERVER_ID).getStateCreator().createState();
					}
					catch (IOException e) {
						saveReplayThisEpisode=false;
						logger.log(Level.WARNING, "Unable to save replay.", e);
					}
				}
				//Run steps until it returns true, indicating that it is terminated
				while(!env.step())
				{
					logger.finer("step");
				}
				//Run the terminal step
				env.terminalStep();
				if(logger.isLoggable(Level.FINE))
				{
					logger.fine("Episode " + episode + " terminated.");
				}
				if (saveReplayThisEpisode) {
					if(logger.isLoggable(Level.FINE))
					{
						logger.fine("Saving replay to file " + baseReplayDirectory + "/ep" + episode);
					}
					saveReplay(new File(baseReplayDirectory,"ep"+episode),initialStateCopy, env.getModel().getHistory());
				}
			}
			catch (InterruptedException e)
			{
				logger.log(Level.SEVERE, "Unable to complete episode " + episode + "!", e);
			}
			if(saveAgentsThisEpisode)
			{
				
				for(int j = 0; saveAgents && j < agents.length; j++)
				{
					try {
						saveAgentData(new File(baseAgentDirectory,"ep"+episode), j);
					}
					catch(FileNotFoundException ex) {
						logger.log(Level.WARNING,"Unable to save agent "+j, ex);
					}
				}
			}
		}
		//This is intentional since the runner has the authority to stop execution, but 
		//does not necessarily have access to all threads that may have been spawned. To prevent
		//this from interfering with agent cleanup, have slow agents add a shutdown hook.
		System.exit(0);
	}
}