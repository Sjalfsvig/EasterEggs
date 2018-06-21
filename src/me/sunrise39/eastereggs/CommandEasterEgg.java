package me.sunrise39.eastereggs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEasterEgg implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if (command.getName().equalsIgnoreCase("easteregg")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				Block block = player.getTargetBlock(null, 100);
				
				if (player.hasPermission("easteregg.admin")) {
					if (args.length == 0) {
						player.sendMessage("§8[§eEasterEggs§8]\n" +
								"§e/EasterEgg set §7- Set a skull as an easter egg.\n" +
								"§e/EasterEgg add <command...> §7- Add a command to an easter egg.\n" +
								"§e/EasterEgg del <command...> §7- Delete a command from an easter egg.\n" +
								"§e/EasterEgg rl §7- Reload the easter egg config.");
					}
					
					if (args.length == 1) {
						if (args[0].equalsIgnoreCase("set")) {
							if (block.getType() == Material.SKULL) {
								if (!EasterEggs.getInstance().getEasterEggs().containsKey(block.getLocation())) {
									EasterEggs.getInstance().getEasterEggs().put(block.getLocation(), new ArrayList<>());	
									
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
									player.sendMessage("§8[§eEaster§8] §eThis block: §7" + block.getType().toString() + " §eat §7" + block.getLocation().getBlockX() + ", " +
											block.getLocation().getBlockY() + ", " + block.getLocation().getBlockZ() + " §ewas set to an Easter Egg.");
								} else {
									player.sendMessage("§8[§eEaster§8] §eThis block is already an egg!");
								}
							} else {
								player.sendMessage("§8[§eEaster§8] §eThis block is not a skull! Are you looking at the right block?");
							}
						}
						
						if (args[0].equalsIgnoreCase("add")) {
							player.sendMessage("§8[§eEasterEggs§8] §e/EasterEgg add <command...>");
						}
						
						if (args[0].equalsIgnoreCase("del")) {
							player.sendMessage("§8[§eEasterEggs§8] §e/EasterEgg del <command...>");
						}
						
						if (args[0].equalsIgnoreCase("rl")) {
							EasterEggs.getInstance().reloadConfig();
							EasterEggs.getInstance().init();
							player.sendMessage("§8[§eEasterEggs§8] §eEaster Eggs Reloaded.");
						}
					}
					
					if (args.length > 2) {
						StringBuilder sb = new StringBuilder();
						for (int i = 1; i < args.length; i++){
							sb.append(args[i]).append(" ");
						}
						 
						String commandTo = sb.toString().trim();
						
						if (args[0].equalsIgnoreCase("add")) {
							if (EasterEggs.getInstance().getEasterEggs().containsKey(block.getLocation())) {
								EasterEggs.getInstance().getEasterEggCommands(block.getLocation()).add(commandTo);
								player.sendMessage("§8[§eEaster§8] §7" + commandTo + " §eadded.");
								
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
							} else {
								player.sendMessage("§8[§eEaster§8] §eThis block is not an egg! Use §7/easteregg §eto set this block as an egg!");
							}
						}
						
						if (args[0].equalsIgnoreCase("del")) {
							if (EasterEggs.getInstance().getEasterEggs().containsKey(block.getLocation())) {
								EasterEggs.getInstance().getEasterEggCommands(block.getLocation()).remove(commandTo);
								player.sendMessage("§8[§eEaster§8] §7" + commandTo + " §eremoved.");
								
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
							} else {
								player.sendMessage("§8[§eEaster§8] §eThis block is not an egg! Use §7/easteregg §eto set this block as an egg!");
							}
						}
					}
				}
			}
		}
		return false;
	}
}