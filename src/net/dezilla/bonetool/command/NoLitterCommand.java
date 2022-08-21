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
import net.dezilla.bonetool.util.Locale;

public class NoLitterCommand extends Command{
	
	public NoLitterCommand() {
		super("nolitter", "Automatically delete items that are dropped", "/nolitter [enable/disable]", Arrays.asList("nl", "litter"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Locale.parse("senderNotPlayer"));
			return true;
		}
		Player p = (Player) sender;
		ToolUser u = ToolUser.getUser(p);
		if(!Util.permCheck(p, "bonetool.tool.nolitter")) {
			p.sendMessage(Util.MSG_START+ChatColor.RED+Locale.parse(u,"accessDenied"));
			return true;
		}
		if(args.length != 0 && args[0].equalsIgnoreCase("enable")) {
			u.setNoLitter(true);
		} else if(args.length != 0 && args[0].equalsIgnoreCase("disable")) {
			u.setNoLitter(false);
		} else {
			u.setNoLitter(!u.getNoLitter());
		}
		sender.sendMessage(Util.MSG_START+Locale.parse(u, "noLitter")+": "+(u.getNoLitter() ? ChatColor.GREEN+Locale.parse(u, "enabled"):ChatColor.RED+Locale.parse(u, "disabled")));
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
