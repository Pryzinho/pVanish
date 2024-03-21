package br.pry.vanish.main;

import br.pry.vanish.api.VanishManager;
import br.pry.vanish.commands.VanishCommand;
import br.pry.vanish.listener.BukkitListener;
import br.pry.vanish.listener.MMOProfilesListener;
import br.pry.vanish.listener.nLoginListener;
import br.pry.vanish.utils.MessagesManager;
import br.pry.vanish.utils.PryConfig;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class VanishMain extends JavaPlugin {

    private PryConfig config;
    private MessagesManager mm;
    private VanishManager vm;

    @Override
    public void onEnable() {
        getLogger().info("Iniciando serviços");
        this.config = new PryConfig(this, "config.yml");
        this.config.saveDefaultConfig();
        mm = new MessagesManager(this);
        vm = new VanishManager(this);
        new VanishCommand(this);
        handlerOrder();
        getLogger().info("Serviços inicializados com sucesso.");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        getLogger().info("Encerrando serviços.");
    }


    public void onReload() {
        config.reloadConfig();
        vm.setCommandPermission(config.getConfig().getString("commands.vanish.permission", "vanish.admin"));
        HandlerList.unregisterAll(this);
        handlerOrder();
        getLogger().info("Serviços recarregados com sucesso");
    }

    private void handlerOrder() {
        String applyOrder = config.getConfig().getString("applyOrder");
        if (applyOrder == null) {
            applyOrder = "BUKKIT";
            getLogger().warning("Não foi possivel reconheçer o evento em que a aplicação da invisibilidade deve ocorrer, usando o padrão 'BUKKIT'");
        }
        switch (applyOrder.toLowerCase()) {
            case "bukkit" -> {
                new BukkitListener(this);
                getLogger().info("Usando serviço de aplicação padrão.");
            }
            case "nlogin" -> {
                if (!getServer().getPluginManager().isPluginEnabled("nLogin")) {
                    new BukkitListener(this);
                    getLogger().warning("Não foi possivel achar o plugin nLogin, usando serviço de aplicação padrão.");
                    break;
                }
                new nLoginListener(this);
                getLogger().info("Usando compatibilidade com o nLogin");
            }
            case "mmoprofiles" -> {
                if (!getServer().getPluginManager().isPluginEnabled("MMOProfiles")) {
                    new BukkitListener(this);
                    getLogger().warning("Não foi possivel achar o plugin nLogin, usando serviço de aplicação padrão.");
                    break;
                }
                new MMOProfilesListener(this);
                getLogger().info("Usando compatibilidade com o MMOProfiles");
            }
            default -> {
                new BukkitListener(this);
                getLogger().warning("Não foi possivel reconheçer o evento em que a aplicação da invisibilidade deve ocorrer, usando o padrão 'BUKKIT'");
            }
        }
    }

    public String getMessage(String path) {
        return mm.getMessage(path);
    }

    public PryConfig getConfigManager() {
        return config;
    }

    public VanishManager getVanishManager() {
        return vm;
    }
}
