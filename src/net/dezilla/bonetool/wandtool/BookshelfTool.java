package net.dezilla.bonetool.wandtool;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.ChiseledBookshelf;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.BookshelfGui;
import net.dezilla.bonetool.util.Locale;

public class BookshelfTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.ChiseledBookshelf");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getState() instanceof ChiseledBookshelf;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		new BookshelfGui((Player) event.getWhoClicked(), block).display();;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		return Util.setName(new ItemStack(Material.CHISELED_BOOKSHELF), Locale.parse(user, "bookshelfcontents"));
	}
	
	@Override
	public boolean hasGui() {
		return true;
	}
	
	@Override
	public void openGui(Player player, Block block) {
		new BookshelfGui(player, block).display();;
	}

}
