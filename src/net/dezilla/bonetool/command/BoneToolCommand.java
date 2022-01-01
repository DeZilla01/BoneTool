package net.dezilla.bonetool.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.ToolGui;
import net.dezilla.bonetool.util.ToolConfig;

public class BoneToolCommand extends Command{

	public BoneToolCommand() {
		super("bonetool");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(sender instanceof ConsoleCommandSender && args.length!=0&&args[0].equalsIgnoreCase("reload")) {
			ToolConfig.reloadConfig();
			sender.sendMessage("BoneTool configuration has been reloaded.");
			return true;
		}
		if(!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command.");
			return true;
		}
		Player p = (Player) sender;
		if(!Util.permCheck(p, "bonetool.tool.use")) {
			p.sendMessage(Util.MSG_START+ChatColor.RED+"You do not have access to this feature.");
			return true;
		}
		if(args.length!=0&&args[0].equalsIgnoreCase("reload")&&Util.permCheck(p, "bonetool.admin.reload")) {
			ToolConfig.reloadConfig();
			sender.sendMessage(Util.MSG_START+"BoneTool configuration has been reloaded.");
			return true;
		}
		new ToolGui(p).display();
		return true;
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player && !Util.permCheck((Player) sender, "bonetool.admin.reload")) {
			return new ArrayList<String>();
		}
		List<String> list = new ArrayList<String>();
		if(args.length==1) {
			for(String s : Arrays.asList("reload")) {
				if(s.toLowerCase().startsWith(args[0].toLowerCase()))
					list.add(s);
			}
		}
		return list;
	}

}
