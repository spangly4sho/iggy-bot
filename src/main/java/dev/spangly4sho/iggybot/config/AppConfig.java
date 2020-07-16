package dev.spangly4sho.iggybot.config;

import dev.spangly4sho.iggybot.listener.PingListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@Configuration
public class AppConfig {

    @Value("${jda.discord.token}")
    private String token;

    private final IggyProperties iggyProperties;

    public AppConfig(IggyProperties iggyProperties) {
        this.iggyProperties = iggyProperties;
    }


    @Bean
    @Qualifier("jdaBuilder")
    public JDABuilder jdaBuilder(){
      return JDABuilder.createDefault(token);
    };

    @Bean
    @Qualifier("jda")
    public JDA jda(JDABuilder jdaBuilder) throws LoginException {
        JDA jda =  jdaBuilder.
                setEventManager(new AnnotatedEventManager())
                .build();
        jda.addEventListener(new PingListener());
        return jda;
    }

}
