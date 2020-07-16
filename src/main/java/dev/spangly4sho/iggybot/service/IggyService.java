package dev.spangly4sho.iggybot.service;

import net.dv8tion.jda.api.JDA;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public interface IggyService {
    void startBot() throws LoginException, InterruptedException;

    void shutdownBot();

    void registerListeners(Object... listeners);

    JDA getJda();
}
