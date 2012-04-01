package edu.cwru.SimpleRTS.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.cwru.SimpleRTS.action.Action;
/**
 * Logs the results for a single player.
 * @author The Condor
 *
 */
public class ActionLogger implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<List<Action>> actions;
	public ActionLogger () {
		actions = new ArrayList<List<Action>>();
	}
	public void addAction(int stepnumber, Action action) {
		while (actions.size()<=stepnumber)
		{
			actions.add(new ArrayList<Action>());
		}
		actions.get(stepnumber).add(action);
	}
	/**
	 * Get the actions for a specific round.
	 * @param roundnumber
	 * @return an unmodifiable list of Actions
	 */
	public List<Action> getActions(int roundnumber) {
		if ( roundnumber<0 || roundnumber >= actions.size()) {
			return new ArrayList<Action>();
		}
		else {
			return Collections.unmodifiableList(actions.get(roundnumber));
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actions == null) ? 0 : actions.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionLogger other = (ActionLogger) obj;
		if (actions == null) {
			if (other.actions != null)
				return false;
		} else if (!actions.equals(other.actions))
			return false;
		return true;
	}
	public ActionLoggerView getView()
	{
		return new ActionLoggerView();
	}
	public class ActionLoggerView
	{
		private ActionLoggerView()
		{
			
		}
		
		/**
		 * Get the actions for a specific round.
		 * @param roundnumber
		 * @return an unmodifiable list of Actions
		 */
		public List<Action> getActions(int roundnumber) {
			//Grab the version in the containing class, then make it unmodifiable
			return Collections.unmodifiableList(ActionLogger.this.getActions(roundnumber));
		}
	}
}
