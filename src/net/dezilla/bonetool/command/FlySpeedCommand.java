package net.dezilla.bonetool.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;

public class FlySpeedCommand extends Command{

	public FlySpeedCommand() {
		super("flyspeed", 
				"Change the player's fly speed.", 
				"/flyspeed [speed]/[enable/disable]",
				Arrays.asList("fspeed", "fs"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command.");
			return true;
		}
		Player p = (Player) sender;
		ToolUser user = ToolUser.getUser(p);
		
		if(!Util.permCheck(p, "bonetool.tool.flyspeed")) {
			p.sendMessage(Util.MSG_START+ChatColor.RED+"You do not have access to this feature.");
			return true;
		}
		
		if(args.length>1) {
			sender.sendMessage(Util.MSG_START+ChatColor.RED+"Too many arguments");
			sender.sendMessage(Util.MSG_START+getUsage());
			return true;
		}
		else if(args.length==1) {
			if(args[0].equalsIgnoreCase("enable")) {
				user.setEditFlySpeed(true);
				displayFlySpeed(user);
			} else if(args[0].equalsIgnoreCase("disable")) {
				user.setEditFlySpeed(false);
				displayFlySpeed(user);
			} else {
				try {
					float i = Float.parseFloat(args[0])/1000;
					if(i>1)
						i = 1;
					if(i<0)
						i = 0;
					user.getPlayer().setFlySpeed(i);
					displayFlySpeed(user.getPlayer());
				}catch(Exception e) {
					sender.sendMessage(Util.MSG_START+ChatColor.RED+"Invalid argument");
					sender.sendMessage(Util.MSG_START+getUsage());
				}
			}
		}
		else {
			displayFlySpeed(user);
			displayFlySpeed(user.getPlayer());
		}
		return true;
	}
	
	private void displayFlySpeed(ToolUser user) {
		user.getPlayer().sendMessage(Util.MSG_START+"Fly speed on double crouch: "
				+(user.getEditFlySpeed() ? ChatColor.GREEN+"True" : ChatColor.RED+"False"));
	}
	
	private void displayFlySpeed(Player player) {
		ChatColor c = (Math.round(player.getFlySpeed()*100) == 10 ? ChatColor.WHITE : (player.getFlySpeed() < .1 ? ChatColor.RED : ChatColor.GREEN));
		String msg = ChatColor.GRAY+"Fly Speed: "+c+Math.round(player.getFlySpeed()*1000)+"%";
		Util.sendNotification(player, msg);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		List<String> list = new ArrayList<String>();
		if(args.length==1) {
			for(String s : Arrays.asList("Enable", "Disable")) {
				if(s.toLowerCase().startsWith(args[0].toLowerCase()))
					list.add(s);
			}
		}
		return list;
	}

}
