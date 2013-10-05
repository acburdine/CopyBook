package com.acburdine.copybook;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void interact(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_BLOCK) {
			Material block = event.getClickedBlock().getType();
			if (block == Material.SIGN_POST || block == Material.WALL_SIGN) {
				Sign sign = (Sign)event.getClickedBlock().getState();
				String line1 = sign.getLine(1);
				String line2 = sign.getLine(2);
				String line3 = sign.getLine(3);
				boolean isInt = Commands.isInt(line3);
				int price = 0;
				if (isInt)
					price = Integer.parseInt(line3);
				if (line1.contains("[CopyBook]")) {
					if (!player.hasPermission("copybook.signload")) {
						player.sendMessage(ChatColor.RED + "You don't have permission to recieve books from signs.");
						event.setCancelled(true);
					} else {
						String[] bName = line2.split("\\s+");
						if (line3.isEmpty() || !isInt) {
							if (!Commands.priceHandling("signload", player, price)) {
								return;
							}
							Functions.signLoad(player, bName);
						} else {
							if (!Commands.priceHandling("signload", player, price)) {
								return;
							}
							Functions.signLoad(player, bName);
						}
					}
				}
			}return;
		}return;
	}
}
