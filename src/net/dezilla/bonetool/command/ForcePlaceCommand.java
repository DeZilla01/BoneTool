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
import net.dezilla.bonetool.util.Locale;

public class ForcePlaceCommand extends Command{

	public ForcePlaceCommand() {
		super("forceplace", 
				"Force blocks to be placed.", 
				"/forceplace [enabled/disabled/onsneak/sneaktoggle]",
				Arrays.asList("fp", "forcep", "fplace"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Locale.parse("senderNotPlayer"));
			return true;
		}
		Player p = (Player) sender;
		ToolUser user = ToolUser.getUser(p);
		
		if(!Util.permCheck(p, "bonetool.tool.forceplace")) {
			p.sendMessage(Util.MSG_START+ChatColor.RED+Locale.parse(ToolUser.getUser(p),"accessDenied"));
			return true;
		}
		
		if(args.length>1) {
			sender.sendMessage(Util.MSG_START+ChatColor.RED+Locale.parse(ToolUser.getUser(p), "manyArguments"));
			sender.sendMessage(Util.MSG_START+getUsage());
			return true;
		}
		else if(args.length==1) {
			try {
				PlacingState state = PlacingState.valueOf(args[0].toUpperCase());
				user.setForcePlace(state);
				p.sendMessage(Util.MSG_START+PlacingOption.FORCEPLACE.getName(user)+": "+user.getForcePlacingState().getColoredName(user));
			}catch(Exception e) {
				sender.sendMessage(Util.MSG_START+ChatColor.RED+Locale.parse(ToolUser.getUser(p), "invalidArgument"));
				sender.sendMessage(Util.MSG_START+getUsage());
			}
			return true;
		}
		else {
			user.ToggleOption(PlacingOption.FORCEPLACE);
			p.sendMessage(Util.MSG_START+PlacingOption.FORCEPLACE.getName(user)+": "+user.getForcePlacingState().getColoredName(user));
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
