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
package edu.cwru.sepia.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.cwru.sepia.action.Action;
import edu.cwru.sepia.action.ActionType;
import edu.cwru.sepia.action.DirectedAction;
import edu.cwru.sepia.action.TargetedAction;
import edu.cwru.sepia.agent.Agent;
import edu.cwru.sepia.environment.model.SimpleModel;
import edu.cwru.sepia.environment.model.state.State;
import edu.cwru.sepia.environment.model.state.Template;
import edu.cwru.sepia.environment.model.state.Unit;
import edu.cwru.sepia.environment.model.state.UnitTemplate;
import edu.cwru.sepia.environment.model.state.State.StateBuilder;
import edu.cwru.sepia.experiment.DefaultConfigurationGenerator;
import edu.cwru.sepia.util.Direction;
import edu.cwru.sepia.util.TypeLoader;
/**
 * Contains tests relating to the creation and manipulation of units
 * @author Tim
 *
 */
public class GameUnitTest {
	static SimpleModel model;
	static List<Template<?>> templates;

	/**
	 * Creates an initial state with one of each unit type.
	 */
	@BeforeClass
	public static void setup() throws Exception {
		State.StateBuilder builder = new StateBuilder();
		State state = builder.build();
		state.setSize(64,64);
		templates = TypeLoader.loadFromFile("data/unit_templates",0,state);
		
		int x = 0;
		int y = 0;
		
		for(Template<?> t : templates)
		{
			if(!(t instanceof UnitTemplate))
				continue;
			Unit u = ((UnitTemplate)t).produceInstance(state);
			builder.addUnit(u,x,y);
			x += 5;
			y += 5;
		}
		model = new SimpleModel(state, 5336,null, DefaultConfigurationGenerator.getDefaultConfiguration());
		model.setVerbose(true);
	}
	/**
	 * Move unit 1 Southeast
	 * @throws OutOfDateException 
	 */
	@Test
	public void test1() {
		DirectedAction a = new DirectedAction(1, ActionType.PRIMITIVEMOVE, Direction.SOUTHEAST);
		{
			Map<Integer, Action> actions = new HashMap<Integer, Action>();
			actions.put(1,a);
			model.addActions(actions, 0);
		}
//		model.setActions(new Action[]{a});
		model.executeStep();
		Unit.UnitView u = model.getState().getView(Agent.OBSERVER_ID).getUnit(1);
		assertEquals("Unit was not in expected row!",6,u.getXPosition());
		assertEquals("Unit was not in expected column!",6,u.getYPosition());		
	}
	/**
	 * Move unit 2 Northwest unitl it bumps into unit 1
	 * @throws OutOfDateException 
	 */
	@Test
	public void test2(){
		Action a = new DirectedAction(2, ActionType.PRIMITIVEMOVE, Direction.NORTHWEST);
//		Action[] actions = new Action[]{a};
//		model.setActions(actions);
		{
			Map<Integer, Action> actions = new HashMap<Integer, Action>();
			actions.put(2,a);
			model.addActions(actions, 0);
			
		}
		model.executeStep();
		Unit.UnitView u = model.getState().getView(Agent.OBSERVER_ID).getUnit(2);
		assertEquals("Unit was not in expected column!",9,u.getXPosition());
		assertEquals("Unit was not in expected row!",9,u.getYPosition());
//		model.setActions(actions);
		{
			Map<Integer, Action> actions = new HashMap<Integer, Action>();
			actions.put(2,a);
			model.addActions(actions, 0);
		}
		model.executeStep();
		u = model.getState().getView(Agent.OBSERVER_ID).getUnit(2);
		assertEquals("Unit was not in expected column!",8,u.getXPosition());
		assertEquals("Unit was not in expected row!",8,u.getYPosition());
//		model.setActions(actions);
		{
			Map<Integer, Action> actions = new HashMap<Integer, Action>();
			actions.put(2,a);
			model.addActions(actions, 0);
		}
		model.executeStep();
		u = model.getState().getView(Agent.OBSERVER_ID).getUnit(2);
		assertEquals("Unit was not in expected column!",7,u.getXPosition());
		assertEquals("Unit was not in expected row!",7,u.getYPosition());
//		model.setActions(actions);
		{
			Map<Integer, Action> actions = new HashMap<Integer, Action>();
			actions.put(2,a);
			model.addActions(actions, 0);
		}
		model.executeStep();
		u = model.getState().getView(Agent.OBSERVER_ID).getUnit(2);
		assertEquals("Unit was not in expected column!",7,u.getXPosition());
		assertEquals("Unit was not in expected row!",7,u.getYPosition());
		
	}
	/**
	 * Have unit 2 attack unit 1
	 * @throws OutOfDateException 
	 */
	@Test
	public void test3() {
		Unit.UnitView u1 = model.getState().getView(Agent.OBSERVER_ID).getUnit(1);
		int hp = u1.getHP();
		Action a = new TargetedAction(2, ActionType.PRIMITIVEATTACK, 1);
		{
			Map<Integer, Action> actions = new HashMap<Integer, Action>();
			actions.put(2,a);
			model.addActions(actions, 0);
		}
//		Action[] actions = new Action[]{a};
//		model.setActions(actions);
		model.executeStep();
		u1 = model.getState().getView(Agent.OBSERVER_ID).getUnit(1);
		assertTrue("Attack failed!",hp > u1.getHP());
		System.out.printf("Attack reduced unit 1's HP from %d to %d\n",hp,u1.getHP());		
	}
	/**
	 * Have unit 2 move away from unit 1, then try to attack from beyond its maximum range
	 * @throws OutOfDateException 
	 */
	@Test
	public void test4() {
		Action a = new DirectedAction(2, ActionType.PRIMITIVEMOVE, Direction.SOUTH);
//		Action[] actions = new Action[]{a};
//		model.setActions(actions);
		{
			Map<Integer, Action> actions = new HashMap<Integer, Action>();
			actions.put(2,a);
			model.addActions(actions, 0);
		}
		model.executeStep();
		Unit.UnitView u = model.getState().getView(Agent.OBSERVER_ID).getUnit(2);
		assertEquals("Unit was not in expected column!",7,u.getXPosition());
		assertEquals("Unit was not in expected row!",8,u.getYPosition());		
		Unit.UnitView u2 = model.getState().getView(Agent.OBSERVER_ID).getUnit(2);
		int hp = u2.getHP();
		a = new TargetedAction(1, ActionType.PRIMITIVEATTACK, 2);
//		actions = new Action[]{a};
//		model.setActions(actions);
		{
		Map<Integer, Action> actions = new HashMap<Integer, Action>();
		actions.put(1,a);
		model.addActions(actions, 0);
		}
		model.executeStep();
		assertTrue("Attack range check failed!",hp == u2.getHP());	
	}
}
