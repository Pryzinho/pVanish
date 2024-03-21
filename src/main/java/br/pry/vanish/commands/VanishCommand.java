package br.pry.vanish.commands;

import br.pry.vanish.api.VanishManager;
import br.pry.vanish.main.VanishMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class VanishCommand implements CommandExecutor {
    private final VanishMain instance;
    private final VanishManager vm;

    public VanishCommand(VanishMain instance) {
        this.instance = instance;
        this.vm = instance.getVanishManager();
        PluginCommand cmd = instance.getCommand("vanish");
        assert cmd != null;
        cmd.setPermission(instance.getConfigManager().getString("commands.vanish.permission"));
        if (!instance.getConfigManager().getConfig().getStringList("commands.vanish.aliases").isEmpty()) {
            cmd.setAliases(instance.getConfigManager().getConfig().getStringList("commands.vanish.aliases"));
        }
        cmd.setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command command, @NotNull String lb, @NotNull String[] args) {
        if (!s.hasPermission(Objects.requireNonNull(command.getPermission()))) {
            if (s instanceof Player p && vm.getStaff(p) != null) {
                vm.remStaff(p);
            }
            s.sendMessage(instance.getMessage("NO_PERMISSION"));
            return true;
        }
        if (args.length == 0) {
            if (s instanceof Player p) {
                if (vm.getStaff(p) == null) {
                    vm.addStaff(p);
                }
                Objects.requireNonNull(vm.getStaff(p)).switchVanish(instance);
            } else {
                s.sendMessage(instance.getMessage("ONLY_PLAYER"));
            }
            return true;
        }
        if (args.length > 1) {
            s.sendMessage(instance.getMessage("COMMAND_USAGE"));
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            instance.onReload();
            s.sendMessage(instance.getMessage("PLUGIN_RELOADED"));
            return true;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            s.sendMessage(instance.getMessage("TARGET_OFFLINE"));
            return true;
        }
        if (!target.hasPermission(command.getPermission())) {
            // Se for rodado significa que um membro da equipe perdeu os privilégios dentro do jogo e o sistema vai remover ele.
            if (vm.getStaff(target) != null) {
                vm.remStaff(target);
            }
            s.sendMessage(instance.getMessage("BLOCKED_NORMAL_PLAYERS"));
            return true;
        }
        // O jogador provavelmente pegou a permissão após entrar no servidor e não entrou na lista de membros da equipe.
        if (vm.getStaff(target) == null) {
            vm.addStaff(target);
        }
        Objects.requireNonNull(vm.getStaff(target)).switchVanish(instance);
        target.sendMessage(instance.getMessage("SWITCHED_BY_OTHERS").replace("{dispatcher}", s.getName()));
        s.sendMessage(instance.getMessage("SWITCH_VANISH_OTHERS").replace("{target}", target.getName()));
        return true;
    }
}
