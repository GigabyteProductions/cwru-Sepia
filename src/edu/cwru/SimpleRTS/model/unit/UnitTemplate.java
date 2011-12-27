package edu.cwru.SimpleRTS.model.unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.cwru.SimpleRTS.environment.State;
import edu.cwru.SimpleRTS.model.Template;
import edu.cwru.SimpleRTS.model.prerequisite.Prerequisite;
import edu.cwru.SimpleRTS.model.resource.ResourceNode;
import edu.cwru.SimpleRTS.model.upgrade.UpgradeTemplate;
/**
 * Contains information about default and invariant attributes of units. The class
 * itself contains some invariant attributes, but defaults, invariants and factory
 * methods for specific types of units should be handles in concrete subclasses.
 * @author Tim
 *
 */

public class UnitTemplate extends Template<Unit> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String unitName;
	protected int baseHealth;
	protected int basicAttack;
	protected int piercingAttack;
	protected int range;
	protected int armor;
	protected int sightRange;
	protected boolean canGather;
	protected boolean canBuild;
	protected boolean canMove;
	protected boolean canAcceptGold;
	protected boolean canAcceptWood;
	protected int foodProvided;
	protected char character;
	protected Prerequisite prerequisite;
	protected int goldGatherRate;
	protected int woodGatherRate;
	private UnitTemplateView view;
	private List<String> produces;
	private List<Integer> producesID;
	public UnitTemplate()
	{
		producesID = new ArrayList<Integer>();
		produces = new ArrayList<String>();
	}
	@Override
	public Unit produceInstance() {
		Unit unit = new Unit(this);
		return unit;
	}
	public String getUnitName() {
		return unitName;
	}
	
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public int getBaseHealth() {
		return baseHealth;
	}
	public void setBaseHealth(int baseHealth) {
		this.baseHealth = baseHealth;
	}
	public char getCharacter() {
		return character;
	}
	public void setCharacter(char character) {
		this.character = character;
	}
	public int getBasicAttack() {
		return basicAttack;
	}
	public void setBasicAttack(int basicAttack) {
		this.basicAttack = basicAttack;
	}
	public int getPiercingAttack() {
		return piercingAttack;
	}
	public void setPiercingAttack(int piercingAttack) {
		this.piercingAttack = piercingAttack;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}
	public int getSightRange() {
		return sightRange;
	}
	public void setSightRange(int sightRange) {
		this.sightRange = sightRange;
	}
	public void setPrerequisite(Prerequisite prerequisite) {
		this.prerequisite = prerequisite;
	}
	public boolean canAttack() {
		return basicAttack > 0 || piercingAttack > 0;
	}
	public boolean canAcceptGold() {
		return canAcceptGold;
	}
	public boolean canAcceptWood() {
		return canAcceptWood;
	}
	public void setCanAcceptGold(boolean canAcceptGold) {
		this.canAcceptGold=canAcceptGold;
	}
	public void setCanAcceptWood(boolean canAcceptWood) {
		this.canAcceptWood=canAcceptWood;
	}
	public int getFoodProvided() {
		return foodProvided;
	}
	public void setFoodProvided(int numFoodProvided) {
		this.foodProvided = numFoodProvided;
	}
	public void setGoldGatherRate(int goldpertrip) {
		this.goldGatherRate = goldpertrip;
	}
	public void setWoodGatherRate(int woodpertrip) {
		this.woodGatherRate = woodpertrip;
	}
	public int getGatherRate(ResourceNode.Type type) {
		if (type == ResourceNode.Type.GOLD_MINE) {
			return goldGatherRate;
		}
		else if (type == ResourceNode.Type.TREE) {
			return woodGatherRate;
		}
			
		return 0;
	}
	public boolean canGather() { return canGather; }
	public void setCanGather(boolean canGather) { this.canGather = canGather; } 
	public boolean canBuild() { return canBuild; }
	public void setCanBuild(boolean canBuild) { this.canBuild = canBuild; }
	public boolean canMove() { return canMove; }
	public void setCanMove(boolean canMove) { this.canMove = canMove; }
	public void addProductionItem(String item) {
		this.produces.add(item);
	}
	public boolean canProduce(Template t) {
		if (t==null)
			return false;
		for (Integer i : producesID)
			if (t.ID == i)
				return true;
		return false;
	}
	@Override
	public void namesToIds(List<UnitTemplate> unittemplates, List<UpgradeTemplate> upgradetemplates) {
		super.namesToIds(unittemplates, upgradetemplates);
		producesID.clear();
		for (String s : produces) {
			for (Template t : unittemplates) {
				if (s.equals(t.getName())) {
					producesID.add(t.ID);
					break;
				}
				
			}
			for (Template t : upgradetemplates) {
				if (s.equals(t.getName())) {
					producesID.add(t.ID);
					break;
				}
				
			}
		}
	}
	public String toString() {
		return name;
	}
	@Override
	public UnitTemplateView getView() {
		if (view == null)
			view = new UnitTemplateView(this);
		return view;
	}
	@Override
	public void deprecateOldView() {
		view = null;		
	}
	/**
	 * 
	 */
	public static class UnitTemplateView extends TemplateView implements Serializable{
		//BE CAREFUL EDITING THIS CLASS OR IT'S SUPERCLASS, IT HAS A template THAT IS NOT THE SAME AS THE VARIABLE OF THE SAME NAME IN IT'S SUPERCLASS 
		private final boolean canGather;
		private final boolean canBuild;
		private final boolean canMove;
		private final boolean canAttack;
		private final String unitName;
		private final int baseHealth;
		private final int basicAttack;
		private final int piercingAttack;
		private final int range;
		private final int armor;
		private final int sightRange;
		private final List<Integer> producesID;
		private final char character;
		private final boolean acceptsGold;
		private final boolean acceptsWood;
		private final int foodProvided;
		
		private static final long serialVersionUID = 1L;
		public UnitTemplateView(UnitTemplate template) {
			super(template);
			canGather = template.canGather();
			canBuild = template.canBuild();
			canMove = template.canMove();
			canAttack = template.canAttack();
			unitName = template.getUnitName();
			baseHealth = template.getBaseHealth();
			basicAttack = template.getBasicAttack();
			piercingAttack = template.getPiercingAttack();
			range = template.getRange();
			armor = template.getArmor();
			sightRange = template.getSightRange();
			producesID = new ArrayList<Integer>(template.producesID.size());
			for (Integer i : template.producesID)
				producesID.add(i);
			character = template.getCharacter();
			acceptsGold = template.canAcceptGold;
			acceptsWood = template.canAcceptWood;
			foodProvided = template.getFoodProvided();
			
			
		}
		public boolean canGather() { return canGather; }
		public boolean canBuild() { return canBuild; }
		public boolean canMove() { return canMove; }
		public boolean canAttack() { return canAttack; }
		public String getUnitName() { return unitName; }
		public int getBaseHealth() { return baseHealth;	}
		public int getBasicAttack() { return basicAttack; }
		public int getPiercingAttack() { return piercingAttack;	}
		public int getRange() {	return range; }
		public int getArmor() {	return armor; }
		public int getSightRange() { return sightRange;	}
		public boolean canProduce(Integer templateID) {return producesID.contains(templateID);};
		public char getCharacter() { return character; }
		public boolean canAcceptGold() {return acceptsGold; }
		public boolean canAcceptWood() {return acceptsWood; }
		public int getFoodProvided() {return foodProvided; }
	}

	

	
}
