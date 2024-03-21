package br.pry.vanish.api;

import br.pry.vanish.main.VanishMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class VanishManager implements Listener {
    private final VanishMain instance;
    private final HashMap<UUID, StaffMember> staffMemberList;
    private String commandPermission;

    public VanishManager(VanishMain instance) {
        this.instance = instance;
        this.staffMemberList = new HashMap<>();
        this.commandPermission = instance.getConfigManager().getConfig().getString("commands.vanish.permission", "vanish.admin");
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }


    @Nullable
    public StaffMember getStaff(Player target) {
        return staffMemberList.get(target.getUniqueId());
    }

    public void addStaff(Player t) {
        this.staffMemberList.put(t.getUniqueId(), new StaffMember(t.getUniqueId()));
    }

    public void remStaff(Player t) {
        this.staffMemberList.remove(t.getUniqueId());
    }

    public void apply(Player t) {
        if (!t.hasPermission(getPermission())) {
            staffMemberList.values().stream().filter(StaffMember::isOnline).filter(StaffMember::isVanished).forEach(s -> {
                t.hidePlayer(instance, s.getOnlinePlayer());
            });
        }
    }

    public String getPermission() {
        return commandPermission;
    }

    public void setCommandPermission(String permission) {
        this.commandPermission = permission;
    }

    @EventHandler
    public void onStaffQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if (getStaff(p) != null && !p.hasPermission(getPermission())){
            remStaff(p);
        }
    }
}
