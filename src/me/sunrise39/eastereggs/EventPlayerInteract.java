package me.sunrise39.eastereggs;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventPlayerInteract implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		Block block = event.getClickedBlock();
		
		if (action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_BLOCK) {
			if (EasterEggs.getInstance().getEasterEggs().containsKey(block.getLocation())) {
				for (String cmd : EasterEggs.getInstance().getEasterEggCommands(block.getLocation())) {
					if (cmd.contains("%player%")) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", player.getName()));
						EasterEggs.getInstance().getEasterEggs().remove(block.getLocation());
						block.setType(Material.AIR);
						
						List<String> locs = EasterEggs.getInstance().getConfig().getStringList("easter-eggs");
						locs.clear();
						for (Location loc : EasterEggs.getInstance().getEasterEggs().keySet()) {
							String world = loc.getWorld().getName();
							int x = loc.getBlockX();
							int y = loc.getBlockY();
							int z = loc.getBlockZ();
							locs.add(world + ":" + x + ":" + y + ":" + z + ":" + EasterEggs.getInstance().getEasterEggCommands(loc));
						}
						
						EasterEggs.getInstance().getConfig().set("easter-eggs", locs);
						EasterEggs.getInstance().saveConfig();
					}
				}
			}
		}
	}
}
