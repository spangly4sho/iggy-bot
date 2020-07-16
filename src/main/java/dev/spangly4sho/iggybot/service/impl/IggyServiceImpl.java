package dev.spangly4sho.iggybot.service.impl;

import dev.spangly4sho.iggybot.service.IggyService;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.security.auth.login.LoginException;

public class IggyServiceImpl  implements IggyService {

    @Autowired
    private JDA jda;

    @Override
    public void startBot() throws LoginException, InterruptedException {
        jda.awaitReady();
    }

    @Override
    public void shutdownBot() {
        this.jda.shutdown();
    }

    @Override
    public void registerListeners(Object... listeners) {
        this.jda.addEventListener(listeners);
    }

    @Override
    public JDA getJda() {
        return this.jda;
    }
}
