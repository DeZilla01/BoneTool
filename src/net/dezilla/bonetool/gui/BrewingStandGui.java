package net.dezilla.bonetool.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.BrewingStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.listener.BlockUpdateListener;
import net.dezilla.bonetool.util.Locale;

public class BrewingStandGui extends GuiPage {
	Block block;
	BrewingStand stand;

	public BrewingStandGui(Player player, Block block) {
		super(1, player);
		this.block = block;
		setName(Locale.parse(ToolUser.getUser(player), "brewstandpotions"));
		if(!(block.getBlockData() instanceof BrewingStand))
			return;
		stand = (BrewingStand) block.getBlockData();
		addItems();
	}
	
	public void refresh() {
		this.clear();
		this.addItems();
		this.display();
	}
	
	public void addItems() {
		if(!(block.getBlockData() instanceof BrewingStand))
			return;
		for(int i = 0 ; i <= 2 ; i++) {
			ItemStack icon = new ItemStack(Material.POTION);
			PotionMeta meta = (PotionMeta) icon.getItemMeta();
			meta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 1, 1), true);
			meta.setDisplayName(ChatColor.RESET+Locale.parse(ToolUser.getUser(getPlayer()), "potion")+" #"+i);
			icon.setItemMeta(meta);
			if(!stand.hasBottle(i))
				icon = Util.setName(new ItemStack(Material.GLASS_BOTTLE), ChatColor.RESET+Locale.parse(ToolUser.getUser(getPlayer()), "potion")+" #"+i);
			GuiItem item = new GuiItem(icon);
			final int I = i;
			item.setRun((event) -> {
				BlockUpdateListener.protectBlock(block, 2);
				stand.setBottle(I, !stand.hasBottle(I));
				block.setBlockData(stand);
				refresh();
			});
			setItem(0,i,item);
		}
	}

}
