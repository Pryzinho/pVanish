package br.pry.vanish.listener;

import br.pry.vanish.listener.core.AbstractListener;
import fr.phoenixdevt.profiles.event.ProfileCreateEvent;
import fr.phoenixdevt.profiles.event.ProfileSelectEvent;
import br.pry.vanish.main.VanishMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class MMOProfilesListener extends AbstractListener {

    public MMOProfilesListener(VanishMain instance) {
        super(instance);
    }

    @EventHandler
    public void onPlayerCreateChar(ProfileCreateEvent e) {
        // Caso o MMOProfiles esteja em modo proxy é bom garantir que estamos verificando a permissão do perfil.
        Player p = (Bukkit.getPlayer(e.getProfile().getUniqueId()) == null) ? e.getPlayer() : Bukkit.getPlayer(e.getProfile().getUniqueId());
        // Verifica se o jogador que entrou tem a permissão de administrador e se ele ainda não está registrado na lista de membros da equipe.
        assert p != null;
        if (p.hasPermission(getVanishManager().getPermission()) && getVanishManager().getStaff(p) == null) {
            // Adiciona o jogador a lista da equipe.
            getVanishManager().addStaff(p);
        }
        // Aplica a invisibilidade dos membros da equipe.
        getVanishManager().apply(p);
    }

    @EventHandler
    public void onPlayerSelectChar(ProfileSelectEvent e) {
        // Caso o MMOProfiles esteja em modo proxy é bom garantir que estamos verificando a permissão do perfil.
        Player p = (Bukkit.getPlayer(e.getProfile().getUniqueId()) == null) ? e.getPlayer() : Bukkit.getPlayer(e.getProfile().getUniqueId());
        // Verifica se o jogador que entrou tem a permissão de administrador e se ele ainda não está registrado na lista de membros da equipe.
        assert p != null;
        if (p.hasPermission(getVanishManager().getPermission()) && getVanishManager().getStaff(p) == null) {
            // Adiciona o jogador a lista da equipe.
            getVanishManager().addStaff(p);
        }
        // Aplica a invisibilidade dos membros da equipe.
        getVanishManager().apply(p);
    }
}
