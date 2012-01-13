package edu.cwru.SimpleRTS.environment.state.persistence;

import java.util.List;
import java.util.Map;

import edu.cwru.SimpleRTS.environment.PlayerState;
import edu.cwru.SimpleRTS.environment.State;
import edu.cwru.SimpleRTS.environment.State.StateBuilder;
import edu.cwru.SimpleRTS.environment.state.persistence.generated.XmlPlayer;
import edu.cwru.SimpleRTS.environment.state.persistence.generated.XmlResourceNode;
import edu.cwru.SimpleRTS.environment.state.persistence.generated.XmlState;
import edu.cwru.SimpleRTS.model.Template;
import edu.cwru.SimpleRTS.model.resource.ResourceNode;

public class StateAdapter {
	
	PlayerAdapter playerAdapter = new PlayerAdapter();
	ResourceNodeAdapter resourceNodeAdapter = new ResourceNodeAdapter();

	public XmlState toXml(State state) {
		XmlState xml = new XmlState();
		
		List<XmlPlayer> players = xml.getPlayer();
		for(PlayerState ps : state.getPlayerStates())
		{
			players.add(playerAdapter.toXml(ps));
		}
		
		List<XmlResourceNode> resources = xml.getResourceNode();
		for(ResourceNode rn : state.getResources())
		{
			resources.add(resourceNodeAdapter.toXml(rn));
		}
		
		xml.setXExtent(state.getXExtent());		
		xml.setYExtent(state.getYExtent());
		
		return xml;
	}
	
	public State fromXml(XmlState xml, @SuppressWarnings("rawtypes") Map<Integer,Map<Integer,Template>> templates) {
		StateBuilder builder = new StateBuilder();
		
		for(XmlPlayer player : xml.getPlayer())
		{
			builder.addPlayer(playerAdapter.fromXml(player, templates.get(player.getID())));
		}
		
		
		if(xml.getResourceNode() != null)
		{
			for(XmlResourceNode resource : xml.getResourceNode())
			{
				builder.addResource(resourceNodeAdapter.fromXml(resource));
			}
		}
		
		builder.setSize(xml.getXExtent(), xml.getYExtent());
		
		State state = builder.build();
		state.updateGlobalListsFromPlayers();
				
		return state;
	}
}
