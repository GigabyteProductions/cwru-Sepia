package edu.cwru.SimpleRTS.agent.visual.editor;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.json.JSONException;
import edu.cwru.SimpleRTS.agent.Agent;
import edu.cwru.SimpleRTS.agent.visual.GamePanel;
import edu.cwru.SimpleRTS.agent.visual.GameScreen;
import edu.cwru.SimpleRTS.environment.State;
import edu.cwru.SimpleRTS.environment.State.StateBuilder;
import edu.cwru.SimpleRTS.model.Template;
import edu.cwru.SimpleRTS.model.resource.ResourceNode;
import edu.cwru.SimpleRTS.model.unit.Unit;
import edu.cwru.SimpleRTS.model.unit.UnitTemplate;
import edu.cwru.SimpleRTS.util.GameMap;
import edu.cwru.SimpleRTS.util.TypeLoader;

public class Editor extends JFrame {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	GameScreen screen;
    GamePanel gamePanel;
	State state;
	JComboBox templateSelector;
	JComboBox playerSelector;
	JButton addPlayer;
	ButtonGroup cursorGroup;
	JTextField resourceAmount;
	JRadioButton selectPointer;
	JRadioButton selectUnit;
	JRadioButton selectTree;
	JRadioButton selectMine;
	JRadioButton selectRemove;
	ButtonGroup fogOfWar;
	JRadioButton fogOn;
	JRadioButton fogOff;
	ButtonGroup revealResources;
	JRadioButton revealResourcesOn;
	JRadioButton revealResourcesOff;
	JTextField xSize;
	JTextField ySize;
	JButton setSize;
	JButton save;
	JTextArea error;
	
	public Editor(GameScreen screen, GamePanel gamePanel, State state, String templatefilename) {
		this.screen = screen;
        this.gamePanel = gamePanel;
		this.state = state;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(screen.getLocation().x+screen.getWidth()+1, screen.getLocation().y);
		setSize(150,screen.getHeight());
		setTitle("Editor");
		setLayout(new FlowLayout());
		
		gamePanel.addMouseListener(this.new EditorMouseListener());
		
		DefaultComboBoxModel model = new DefaultComboBoxModel(new Object[]{});
		playerSelector = new JComboBox(model);
		addPlayer = new JButton("Add Player");
		addPlayer.addActionListener(new ActionListener() {
			DefaultComboBoxModel model;
			String templatefilename;
			State state;
			public ActionListener setOutsideInformationAndClick(DefaultComboBoxModel model, String templatefilename, State state) {
				this.model = model;
				this.templatefilename = templatefilename;
				this.state = state;
				actionPerformed(null); //click it once so it starts with one player
				return this;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getSize() < 8)
				{
					int newPlayerNum = model.getSize();
					try {
						List<Template<?>> newPlayerTemplates = TypeLoader.loadFromFile(templatefilename, newPlayerNum,state);
						for (Template<?> t : newPlayerTemplates) {
							state.addTemplate(t);
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					model.addElement("Player "+newPlayerNum);
				}
			}			
		}.setOutsideInformationAndClick(model,templatefilename,state));
		cursorGroup = new ButtonGroup();
		selectPointer = new JRadioButton("Pointer");
		selectPointer.setSelected(true);
		cursorGroup.add(selectPointer);
		selectUnit = new JRadioButton("Unit");
		cursorGroup.add(selectUnit);
		selectTree = new JRadioButton("Tree");
		cursorGroup.add(selectTree);
		selectMine = new JRadioButton("Mine");
		cursorGroup.add(selectMine);
		selectRemove = new JRadioButton("Remove");
		cursorGroup.add(selectRemove);
		
		
		//Make a button group for fog of war, one radio button for on and one for off
		//  and make listeners to set the state
		//  then make it default to off
		fogOfWar = new ButtonGroup();
		fogOn = new JRadioButton("Fog on war");
		fogOfWar.add(fogOn);
		fogOn.addActionListener(new ActionListener() {
			State state;
			Editor ed;
			public ActionListener setThings(State state, Editor ed) {
				this.state = state;
				this.ed = ed;
				return this;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				state.setFogOfWar(true);
				ed.updateScreen();
			}			
		}.setThings(state,this));
		fogOff = new JRadioButton("Fog off war");
		fogOfWar.add(fogOff);
		fogOff.addActionListener(new ActionListener() {
			State state;
			Editor ed;
			public ActionListener setThings(State state, Editor ed) {
				this.state = state;
				this.ed = ed;
				return this;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				state.setFogOfWar(false);
				ed.updateScreen();
			}			
		}.setThings(state,this));
		fogOff.setSelected(true);
		
		//Make a button group for revealed resources, one radio button for on and one for off
		//  and make listeners to set the state
		//  then make it default to off
//		revealResources = new ButtonGroup();
//		revealResourcesOn = new JRadioButton("Reveal Resources");
//		revealResources.add(revealResourcesOn);
//		revealResourcesOn.addActionListener(new ActionListener() {
//			State state;
//			Editor ed;
//			public ActionListener setThings(State state, Editor ed) {
//				this.state = state;
//				this.ed = ed;
//				return this;
//			}
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				state.setRevealedResources(true);
//				ed.updateScreen();
//			}			
//		}.setThings(state,this));
//		revealResourcesOff = new JRadioButton("Unreveal Resources");
//		revealResources.add(revealResourcesOff);
//		revealResourcesOff.addActionListener(new ActionListener() {
//			State state;
//			Editor ed;
//			public ActionListener setThings(State state, Editor ed) {
//				this.state = state;
//				this.ed = ed;
//				return this;
//			}
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				state.setRevealedResources(false);
//				ed.updateScreen();
//			}			
//		}.setThings(state,this));
//		revealResourcesOff.setSelected(true);
		
		String[] unitnames;
		List<UnitTemplate> tempunittemplates=null;
		try {
			tempunittemplates = TypeLoader.loadUnitsFromFile(templatefilename, -3, new State());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if(tempunittemplates == null)
			tempunittemplates = new ArrayList<UnitTemplate>(0);
		unitnames = new String[tempunittemplates.size()];
		for (int i = 0; i<unitnames.length;i++) {
			unitnames[i] = tempunittemplates.get(i).getName();
		}
		templateSelector = new JComboBox(unitnames);		
		templateSelector.addActionListener(new ActionListener() {
			JRadioButton button;
			public ActionListener setButton(JRadioButton button) {
				this.button = button;
				return this;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				button.setSelected(true);
			}			
		}.setButton(selectUnit));
		error = new JTextArea(4,10);
		error.setForeground(Color.RED);
		error.setEditable(false);
		error.setLineWrap(true);
		error.setWrapStyleWord(true);
		resourceAmount = new JTextField(4);
		resourceAmount.setText("100");
		
		
		save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			State state;
			public ActionListener setState(State state) {
				this.state = state;
				return this;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				int result = jfc.showSaveDialog(Editor.this);
				if(result != JFileChooser.APPROVE_OPTION)
					return;
				GameMap.storeState(jfc.getSelectedFile().toString(), state);
			}
			
		}.setState(state));
		
		
		
		//Add a couple of text fields and a button to resize the map
			//x size
		xSize = new JTextField(3);
		xSize.setText(Integer.toString(state.getXExtent()));
			//y size
		ySize = new JTextField(3);
		ySize.setText(Integer.toString(state.getYExtent()));
			//button to set size
		setSize = new JButton("Set Size");
			//make a listener on the text fields to activate the button when you press enter from the fields
		ActionListener pressSetSize = new ActionListener() {
			JButton button;
			public ActionListener setButtonToPress(JButton button)
			{
				this.button = button;
				return this;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				button.doClick();
			}
		}.setButtonToPress(setSize);
		xSize.addActionListener(pressSetSize);
		ySize.addActionListener(pressSetSize);
			//make a listener for the button that checks the text fields for validity of their numbers
			//   and if it would make it so small that it would exclude some units/resources that are placed
			//   then it needs to confirm it before deleting those units/nodes
		setSize.addActionListener(new ActionListener() {
			State state;
			JTextField xSize;
			JTextField ySize;
			JTextArea errorOut;
			Editor editor;
			public ActionListener setStateAndFields(State state, JTextField xSize, JTextField ySize, JTextArea errorOut, Editor editor) {
				this.state = state;
				this.xSize = xSize;
				this.ySize = ySize;
				this.errorOut = errorOut;
				this.editor = editor;
				return this;
			}
			/**
			 * Reset the text fields to the actual size of the state
			 */
			private void resetTextFields()
			{
				xSize.setText(Integer.toString(state.getXExtent()));
				ySize.setText(Integer.toString(state.getYExtent()));
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				int newxsize=-1;
				int newysize=-1;
				//make sure that they are integers
				try {
					newxsize = Integer.parseInt(xSize.getText());
				}
				catch (NumberFormatException nfe)
				{
					errorOut.setText("x Size: "+xSize.getText()+" is not an integer");
					resetTextFields();
					return;
				}
				try {
					newysize = Integer.parseInt(ySize.getText());
				}
				catch (NumberFormatException nfe)
				{
					errorOut.setText("y Size: "+ySize.getText()+" is not an integer");
					resetTextFields();
					return;
				}
				if (newxsize < 1)
				{
					errorOut.setText("x Size: must be positive");
					resetTextFields();
					return;
				}
				if (newysize < 1)
				{
					errorOut.setText("y Size: must be positive");
					resetTextFields();
					return;
				}
				//get all units and resources that would be out of bounds
				List<Integer> unitsToRemove = new LinkedList<Integer>();
				for (Unit u : state.getUnits().values())
				{
					if (u.getxPosition() >= newxsize || u.getyPosition() >= newysize)
					{
						unitsToRemove.add(u.ID);
					}
				}
				List<Integer> resourcesToRemove = new LinkedList<Integer>();
				for (ResourceNode r : state.getResources())
				{
					if (r.getxPosition() >= newxsize || r.getyPosition() >= newysize)
					{
						resourcesToRemove.add(r.ID);
					}
				}
				if (unitsToRemove.size() + resourcesToRemove.size() > 0)
				{
					int response = JOptionPane.showConfirmDialog(editor, "Resizing to "+newxsize+"x"+newysize+" will delete "+unitsToRemove.size() + " units\n and "+ resourcesToRemove.size()+" resources\nAre you sure you want to do that?", "Resizing", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == 0)//0 is yes, 1 is no
					{
						for (int id : unitsToRemove) {
							state.removeUnit(id);
						}
						for (int id : resourcesToRemove) {
							state.removeResourceNode(id);
						}
					}
					else
					{
						resetTextFields();
						return;
					}
				}
				state.setSize(newxsize,newysize);
				editor.updateScreen();
			}
			
		}.setStateAndFields(state,xSize,ySize,error,this));
		add(templateSelector);
		add(playerSelector);
		add(addPlayer);
		add(selectPointer);
		add(selectUnit);
		add(selectTree);
		add(selectMine);
		add(selectRemove);
		add(resourceAmount);
		add(save);
		add(error);
		add(fogOn);
		add(fogOff);
//		add(revealResourcesOn);
//		add(revealResourcesOff);
		add(xSize);
		add(ySize);
		add(setSize);
		setVisible(true);
		updateScreen();
	}
	
	
	private class EditorMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			int x = gamePanel.unscaleX(e.getX());
			int y = gamePanel.unscaleY(e.getY());
			System.out.println(x+","+y);
			int player = playerSelector.getSelectedIndex();
			if (!state.inBounds(x, y))
			{
				error.setText(x + "," + y + " is out of bounds.");
			}
			if(!selectPointer.isSelected() && !selectRemove.isSelected() && (state.unitAt(x, y) != null || state.resourceAt(x, y) != null))
			{
				error.setText("Cannot place on top of existing object.");
				return;
			}
			if(selectUnit.isSelected())
			{
				String name = (String)templateSelector.getSelectedItem();
				Unit u = ((UnitTemplate)state.getTemplate(player,name)).produceInstance(state);
				state.addUnit(u,x,y);
			}
			else if(selectTree.isSelected())
			{
				int amount;
				try {
					amount = Integer.parseInt(resourceAmount.getText());
				}
				catch(Exception ex) {
					error.setText("Invalid resource quantity.");
					return;
				}
				ResourceNode r = new ResourceNode(ResourceNode.Type.TREE, x, y, amount,state.nextTargetID());
				state.addResource(r);
			}
			else if(selectMine.isSelected())
			{
				int amount;
				try {
					amount = Integer.parseInt(resourceAmount.getText());
				}
				catch(Exception ex) {
					error.setText("Invalid resource quantity.");
					return;
				}
				ResourceNode r = new ResourceNode(ResourceNode.Type.GOLD_MINE, x, y, amount,state.nextTargetID());
				state.addResource(r);
			}
			else if(selectRemove.isSelected())
			{
				//Remove something on that position
				//Try to grab a resource first
				ResourceNode resourcethere = state.resourceAt(x, y);
				if (resourcethere != null) //if there was a resource there
				{//then remove it
					state.removeResourceNode(resourcethere.ID);
				}
				else //otherwise, see about units
				{
					Unit unitthere = state.unitAt(x, y);
					if (unitthere != null)//if there was a unit there
					{//then remove it
						state.removeUnit(unitthere.ID);
					}
				}
					
			}
			updateScreen();
		}

	}
	public void updateScreen()
	{
		gamePanel.updateState(state.getView(Agent.OBSERVER_ID), null);
	}
	public static void main(String[] args) throws FileNotFoundException, JSONException {
		State state = null;
		if(args.length > 0)
		{
			try {
			state = GameMap.loadState(args[0]);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		if(state == null)
		{
			StateBuilder builder = new StateBuilder();
			builder.setSize(25, 19);
			state = builder.build();
		}
        final State fstate = state;
        final GamePanel gamePanel = new GamePanel(null);
		final GameScreen screen = new GameScreen(gamePanel);
        
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                screen.pack();
                Editor editor = new Editor(screen, gamePanel, fstate, "data/unit_templates");
                editor.setVisible(true);
            }
        });
	}
}
