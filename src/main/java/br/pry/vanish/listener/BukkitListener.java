package br.pry.vanish.listener;

import br.pry.vanish.api.VanishManager;
import br.pry.vanish.listener.core.AbstractListener;
import br.pry.vanish.main.VanishMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class BukkitListener extends AbstractListener {
    public BukkitListener(VanishMain instance) {
        super(instance);
    }

    // Evento processado quando um jogador entra no servidor.
    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        // Verifica se o jogador que entrou tem a permissão de administrador e se ele ainda não está registrado na lista de membros da equipe.
        if (p.hasPermission(getVanishManager().getPermission()) && getVanishManager().getStaff(p) == null) {
            // Adiciona o jogador a lista da equipe.
            getVanishManager().addStaff(p);
        }
        // Aplica a invisibilidade dos membros da equipe.
        getVanishManager().apply(p);
    }

}
