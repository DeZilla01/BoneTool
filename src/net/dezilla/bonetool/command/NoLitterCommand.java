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

public class NoLitterCommand extends Command{
	
	public NoLitterCommand() {
		super("nolitter", "Automatically delete items that are dropped", "/nolitter [enable/disable]", Arrays.asList("nl", "litter"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command.");
			return true;
		}
		Player p = (Player) sender;
		ToolUser u = ToolUser.getUser(p);
		if(!Util.permCheck(p, "bonetool.tool.nolitter")) {
			p.sendMessage(Util.MSG_START+ChatColor.RED+"You do not have access to this feature.");
			return true;
		}
		if(args.length != 0 && args[0].equalsIgnoreCase("enable")) {
			u.setNoLitter(true);
		} else if(args.length != 0 && args[0].equalsIgnoreCase("disable")) {
			u.setNoLitter(false);
		} else {
			u.setNoLitter(!u.getNoLitter());
		}
		sender.sendMessage(Util.MSG_START+"No litter: "+(u.getNoLitter() ? ChatColor.GREEN+"Enabled":ChatColor.RED+"Disabled"));
		return true;
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
