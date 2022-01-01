package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.NoteGui;

public class NoteBlockTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.NoteBlock");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof NoteBlock;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Edit Note";
		if(block != null) {
			NoteBlock note = (NoteBlock) block.getBlockData();
			name+=" - Instrument: "+ChatColor.YELLOW+note.getInstrument().toString()+" - "+note.getNote().toString();
		}
		ItemStack icon = new ItemStack(Material.NOTE_BLOCK);
		return Util.setName(icon, name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		new NoteGui((Player) event.getWhoClicked(), block).display();
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleInstrument(block);
	}
	
	@Override
	public boolean hasGui() {
		return true;
	}
	
	@Override
	public void openGui(Player player, Block block) {
		new NoteGui(player, block).display();
	}
	
	public static Block setNote(Block block, Note note) {
		if(!(block.getBlockData() instanceof NoteBlock))
			return block;
		NoteBlock data = (NoteBlock) block.getBlockData();
		data.setNote(note);
		block.setBlockData(data);
		return block;
	}
	
	public static Block setInstrument(Block block, Instrument instrument) {
		if(!(block.getBlockData() instanceof NoteBlock))
			return block;
		NoteBlock data = (NoteBlock) block.getBlockData();
		data.setInstrument(instrument);
		block.setBlockData(data);
		return block;
	}
	
	public static void toggleInstrument(Block block) {
		if(!(block.getBlockData() instanceof NoteBlock))
			return;
		NoteBlock data = (NoteBlock) block.getBlockData();
		List<Instrument> list = Arrays.asList(Instrument.values());
		if(list.indexOf(data.getInstrument())+1 >= list.size())
			data.setInstrument(list.get(0));
		else
			data.setInstrument(list.get(list.indexOf(data.getInstrument())+1));
		block.setBlockData(data);
	}

}
