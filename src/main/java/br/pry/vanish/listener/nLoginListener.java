package br.pry.vanish.listener;

import br.pry.vanish.listener.core.AbstractListener;
import br.pry.vanish.main.VanishMain;
import com.nickuc.login.api.event.bukkit.auth.AuthenticateEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class nLoginListener extends AbstractListener {

    public nLoginListener(VanishMain instance) {
        super(instance);
    }

    @EventHandler
    public void onPlayerAuth(AuthenticateEvent e) {
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
