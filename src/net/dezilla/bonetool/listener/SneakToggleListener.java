package net.dezilla.bonetool.listener;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;

public class SneakToggleListener implements Listener{
	private static Map<Player, Timestamp> ts = new HashMap<Player, Timestamp>();
	private static Map<Player, Boolean> edit_fly_speed = new HashMap<Player, Boolean>();
	private static Map<Player, Timestamp> triple_sneak = new HashMap<Player, Timestamp>();
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
		if(event.isSneaking()) {
			Timestamp now = new Timestamp(new Date().getTime());
			ToolUser user = ToolUser.getUser(event.getPlayer());
			
			if(user.getEditFlySpeed() && triple_sneak.containsKey(event.getPlayer()) && triple_sneak.get(event.getPlayer()).getTime()+500>now.getTime()) {
				event.getPlayer().setFlySpeed(.1f);
				displayFlySpeed(event.getPlayer());
				triple_sneak.remove(event.getPlayer());
				return;
			}
			if(!ts.containsKey(event.getPlayer())) {
				ts.put(event.getPlayer(), now);
				return;
			}
			if(ts.get(event.getPlayer()).getTime()+500>now.getTime()) {
				triple_sneak.put(event.getPlayer(), now);
				if(user.getEditFlySpeed()) {
					edit_fly_speed.put(event.getPlayer(), true);
					displayFlySpeed(event.getPlayer());
				}
				user.sneakToggle();
				ts.remove(event.getPlayer());
				return;
			}
			ts.put(event.getPlayer(), now);
		}
		else if(edit_fly_speed.containsKey(event.getPlayer()) && edit_fly_speed.get(event.getPlayer())) {
			edit_fly_speed.put(event.getPlayer(), false);
		}
	}
	
	@EventHandler
	public void playerChangeSlot(PlayerItemHeldEvent event) {
		if(edit_fly_speed.containsKey(event.getPlayer()) && edit_fly_speed.get(event.getPlayer())) {
			int m = 1;
			if(event.getPreviousSlot() == 4) {
				m = event.getNewSlot()-4;
			}else if(event.getPreviousSlot() < 4) {
				int newSlot = findNearest(event.getPreviousSlot(), Arrays.asList(event.getNewSlot()-9, event.getNewSlot()));
				if(newSlot < 0) {
					m = ((newSlot*-1)+event.getPreviousSlot())*-1;
				} else {
					m = newSlot-event.getPreviousSlot();
				}
			}else if(event.getPreviousSlot() > 4) {
				int newSlot = findNearest(event.getPreviousSlot(), Arrays.asList(event.getNewSlot()+9, event.getNewSlot()));
				m = newSlot - event.getPreviousSlot();
			}

			float newSpeed = event.getPlayer().getFlySpeed() + (.02f  * m);
			if(newSpeed>1)
				newSpeed = 1;
			else if(newSpeed<0)
				newSpeed = 0;
			event.getPlayer().setFlySpeed(newSpeed);
			displayFlySpeed(event.getPlayer());
		}
	}
	
	private void displayFlySpeed(Player player) {
		ChatColor c = (Math.round(player.getFlySpeed()*100) == 10 ? ChatColor.WHITE : (player.getFlySpeed() < .1 ? ChatColor.RED : ChatColor.GREEN));
		String msg = ChatColor.GRAY+"Fly Speed: "+c+Math.round(player.getFlySpeed()*1000)+"%";
		Util.sendNotification(player, msg);
	}
	
	private int findNearest(int base, List<Integer> values) {
		int nearest = values.get(0);
		int distance = 9999;
		for(int i : values) {
			int dist;
			if(i < 0) {
				dist = (i*-1)+base;
			} else {
				dist = base-i;
				if(dist<0)
					dist*=-1;
			}
			if(dist < distance) {
				distance = dist;
				nearest = i;
			}
		}
		return nearest;
	}

}
//⢀⡴⠑⡄⠀⠀⠀⠀⠀⠀⠀⣀⣀⣤⣤⣤⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ 
//⠸⡇⠀⠿⡀⠀⠀⠀⣀⡴⢿⣿⣿⣿⣿⣿⣿⣿⣷⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀ 
//⠀⠀⠀⠀⠑⢄⣠⠾⠁⣀⣄⡈⠙⣿⣿⣿⣿⣿⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀ 
//⠀⠀⠀⠀⢀⡀⠁⠀⠀⠈⠙⠛⠂⠈⣿⣿⣿⣿⣿⠿⡿⢿⣆⠀⠀⠀⠀⠀⠀⠀ 
//⠀⠀⠀⢀⡾⣁⣀⠀⠴⠂⠙⣗⡀⠀⢻⣿⣿⠭⢤⣴⣦⣤⣹⠀⠀⠀⢀⢴⣶⣆ 
//⠀⠀⢀⣾⣿⣿⣿⣷⣮⣽⣾⣿⣥⣴⣿⣿⡿⢂⠔⢚⡿⢿⣿⣦⣴⣾⠁⠸⣼⡿ 
//⠀⢀⡞⠁⠙⠻⠿⠟⠉⠀⠛⢹⣿⣿⣿⣿⣿⣌⢤⣼⣿⣾⣿⡟⠉⠀⠀⠀⠀⠀ 
//⠀⣾⣷⣶⠇⠀⠀⣤⣄⣀⡀⠈⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀ 
//⠀⠉⠈⠉⠀⠀⢦⡈⢻⣿⣿⣿⣶⣶⣶⣶⣤⣽⡹⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀ 
//⠀⠀⠀⠀⠀⠀⠀⠉⠲⣽⡻⢿⣿⣿⣿⣿⣿⣿⣷⣜⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀ 
//⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣷⣶⣮⣭⣽⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀ 
//⠀⠀⠀⠀⠀⠀⣀⣀⣈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠇⠀⠀⠀⠀⠀⠀⠀ 
//⠀⠀⠀⠀⠀⠀⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀ 
//⠀⠀⠀⠀⠀⠀⠀⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀ 
//⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⠻⠿⠿⠿⠿⠛⠉
// Shrek is love, Shrek is life.