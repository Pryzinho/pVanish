package br.pry.vanish.utils;

import org.bukkit.plugin.java.JavaPlugin;

public class MessagesManager {
    private final PryConfig messages;

    public MessagesManager(JavaPlugin instance) {
        messages = new PryConfig(instance, "messages.yml");
        messages.saveDefaultConfig();
    }

    /**
     *
     * @param path Localização da mensagem no arquivo messages.yml
     * @return Retorna o valor encontrado no path indicado, aplicando Placeholder e as cores utilizadas por & ou pelo Kyori
     */
    public String getMessage(String path){
        return PryColor.color(messages.getString(path));
    }

    /**
     *
     * @param path Localização da mensagem no arquivo messages.yml
     * @return Retorna o valor encontrado no path indicado.
     */
    public String getRawMessage(String path){
        return messages.getString(path);
    }

}
