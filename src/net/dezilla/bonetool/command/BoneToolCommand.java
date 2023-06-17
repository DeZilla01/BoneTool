package net.dezilla.bonetool.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.LocaleGui;
import net.dezilla.bonetool.gui.ToolGui;
import net.dezilla.bonetool.util.ItemSaver;
import net.dezilla.bonetool.util.Locale;
import net.dezilla.bonetool.util.ToolConfig;

public class BoneToolCommand extends Command{

	public BoneToolCommand() {
		super("bonetool");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(sender instanceof ConsoleCommandSender && args.length!=0&&args[0].equalsIgnoreCase("reload")) {
			ToolConfig.reloadConfig();
			sender.sendMessage(Locale.parse("configReload"));
			return true;
		}
		if(!(sender instanceof Player)) {
			sender.sendMessage(Locale.parse("senderNotPlayer"));
			return true;
		}
		Player p = (Player) sender;
		if(!Util.permCheck(p, "bonetool.tool.use")) {
			p.sendMessage(Util.MSG_START+ChatColor.RED+Locale.parse(ToolUser.getUser(p),"accessDenied"));
			return true;
		}
		if(args.length!=0&&args[0].equalsIgnoreCase("reload")&&Util.permCheck(p, "bonetool.admin.reload")) {
			ToolConfig.reloadConfig();
			sender.sendMessage(Util.MSG_START+Locale.parse(ToolUser.getUser(p), "configReload"));
			return true;
		}
		if(args.length!=0&&args[0].equalsIgnoreCase("locale")) {
			new LocaleGui(p).display();
			return true;
		}
		if(p.isOp() && args.length!=0&&args[0].equalsIgnoreCase("itemsaver")) {
			ItemSaver.saveHeldItem(p);
		}
		new ToolGui(p).display();
		return true;
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		List<String> argList = new ArrayList<String>();
		if(!(sender instanceof Player) || Util.permCheck((Player) sender, "bonetool.admin.reload")) {
			argList.add("reload");
		}
		if(sender instanceof Player && Util.permCheck((Player) sender, "bonetool.tool.use")) {
			argList.add("locale");
		}
		List<String> list = new ArrayList<String>();
		if(args.length==1) {
			for(String s : argList) {
				if(s.toLowerCase().startsWith(args[0].toLowerCase()))
					list.add(s);
			}
		}
		return list;
	}

}
