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
import net.dezilla.bonetool.ToolUser.PlacingOption;
import net.dezilla.bonetool.ToolUser.PlacingState;

public class NoBlockUpdateCommand extends Command{

	public NoBlockUpdateCommand() {
		super("blockupdate", 
				"Disable block updates when placing blocks.", 
				"/blockupdate [enabled/disabled/onsneak/sneaktoggle]",
				Arrays.asList("bu", "noblockupdate"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command.");
			return true;
		}
		Player p = (Player) sender;
		ToolUser user = ToolUser.getUser(p);
		
		if(!Util.permCheck(p, "bonetool.tool.nobupdate")) {
			p.sendMessage(Util.MSG_START+ChatColor.RED+"You do not have access to this feature.");
			return true;
		}
		
		if(args.length>1) {
			sender.sendMessage(Util.MSG_START+ChatColor.RED+"Too many arguments");
			sender.sendMessage(Util.MSG_START+getUsage());
			return true;
		}
		else if(args.length==1) {
			try {
				PlacingState state = PlacingState.valueOf(args[0].toUpperCase());
				user.setNoBlockUpdate(state);
				p.sendMessage(Util.MSG_START+PlacingOption.NOBLOCKUPDATE.getName()+": "+user.getBlockUpdateState().getColoredName());
			}catch(Exception e) {
				sender.sendMessage(Util.MSG_START+ChatColor.RED+"Invalid option.");
				sender.sendMessage(Util.MSG_START+getUsage());
			}
			return true;
		}
		else {
			user.ToggleOption(PlacingOption.NOBLOCKUPDATE);
			p.sendMessage(Util.MSG_START+PlacingOption.NOBLOCKUPDATE.getName()+": "+user.getBlockUpdateState().getColoredName());
			return true;
		}
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		List<String> list = new ArrayList<String>();
		if(args.length==1) {
			for(PlacingState state : PlacingState.values()) {
				if(state.toString().startsWith(args[0].toUpperCase()))
					list.add(state.toString());
			}
		}
		return list;
	}

}
