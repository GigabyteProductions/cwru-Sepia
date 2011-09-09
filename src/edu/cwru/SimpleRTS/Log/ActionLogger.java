package edu.cwru.SimpleRTS.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cwru.SimpleRTS.action.Action;
/**
 * Note: as of 9/9/11, the addAction function is being called based on the controller of the unit, rather than directly from the player issuing the order
 * @author The Condor
 *
 */
public class ActionLogger {
	Map<Integer, List<List<Action>>> actions; //Map of playernum -> (List by round number of (List of actions done by that player in that round))
	int roundnumber;
	public ActionLogger () {
		actions = new HashMap<Integer, List<List<Action>>>();
		roundnumber=-1;//it is the 0th round, and 0 is a very round number
		nextRound();
	}
	public void nextRound()
	{
		for (int playerid : actions.keySet()) {
			List<List<Action>> actionsets=actions.get(playerid);
			actionsets.add(roundnumber,new ArrayList<Action>());
		}
		roundnumber++;
	}
	public void addPlayer(int playernumber) {
		List<List<Action>> actionset = new ArrayList<List<Action>>();
		actions.put(playernumber, actionset);
		for (int i = 0; i<roundnumber+1;i++)
		{
			actionset.add(i,new ArrayList<Action>());
		}
	}
	public void addAction(int playernum, Action action) {
		if (!actions.containsKey(playernum))
		{
			addPlayer(playernum);
			System.out.println("Lists for player number"+actions.get(playernum));
		}
		actions.get(playernum).get(roundnumber).add(action);
	}
	/**
	 * Get the actions of a player
	 * @param playernum
	 * @param roundnumber
	 * @return an unmodifiable list of actions performed by a player in a specific round (or an empty list if the player or round is not found)
	 */
	public List<Action> getActions(int playernum, int roundnumber) {
		if (!actions.containsKey(playernum) || roundnumber<0 || roundnumber < this.roundnumber) {
			return new ArrayList<Action>();
		}
		else {
			return Collections.unmodifiableList(actions.get(playernum).get(roundnumber));
		}
	}
}
