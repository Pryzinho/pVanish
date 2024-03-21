package br.pry.vanish.listener.core;

import br.pry.vanish.api.VanishManager;
import br.pry.vanish.main.VanishMain;
import org.bukkit.event.Listener;

public class AbstractListener implements Listener {
    private final VanishMain instance;
    private final VanishManager vm;

    public AbstractListener(VanishMain instance) {
        this.instance = instance;
        this.vm = instance.getVanishManager();
        // Registra o listener no servidor.
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    public VanishManager getVanishManager() {
        return vm;
    }

    public VanishMain getInstance() {
        return instance;
    }

}
