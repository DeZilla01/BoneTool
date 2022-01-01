package net.dezilla.bonetool.listener;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class DeleteLaterListener implements Listener{
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		List<EntityType> l = Arrays.asList(EntityType.ZOMBIE,
				EntityType.SLIME,
				EntityType.SKELETON,
				EntityType.SPIDER,
				EntityType.ENDERMAN);
		for(EntityType t : l) {
			if(e.getEntityType()==t) {
				e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.IRON_GOLEM);
			}
		}
	}

}
