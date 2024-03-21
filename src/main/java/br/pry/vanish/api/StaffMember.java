package br.pry.vanish.api;

import br.pry.vanish.main.VanishMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class StaffMember {
    private final UUID uuid;
    private boolean vanished;
    private Inventory inventory;

    public StaffMember(UUID uuid) {
        this.uuid = uuid;
    }

    public void switchVanish(VanishMain instance) {
        Player staff = getOnlinePlayer();
        if (vanished) {
            Bukkit.getOnlinePlayers().stream().filter(p -> !p.canSee(staff)).forEach(t -> {
                t.showPlayer(instance, staff);
            });
            staff.sendMessage(instance.getMessage("NOW_VISIBLE"));
        } else {
            Bukkit.getOnlinePlayers().stream().filter(p -> p.canSee(getOnlinePlayer())).forEach(t -> {
                t.hidePlayer(instance, staff);
            });
            staff.sendMessage(instance.getMessage("NOW_VANISHED"));
        }
    }

    public boolean isVanished() {
        return vanished;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public Player getOnlinePlayer() {
        return Bukkit.getPlayer(uuid) == null ? Bukkit.getPlayer(uuid) : null;
    }

    public boolean isOnline() {
        return getOnlinePlayer().isOnline();
    }
}
