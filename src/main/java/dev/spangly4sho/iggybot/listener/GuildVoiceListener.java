package dev.spangly4sho.iggybot.listener;

import dev.spangly4sho.iggybot.config.IggyProperties;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Random;

@Component
public class GuildVoiceListener {
    static final Logger logger = LoggerFactory.getLogger(GuildVoiceListener.class);

    @Autowired
    private IggyProperties iggyProperties;


    private Random rand;

    public GuildVoiceListener() {
        this.rand = new Random();
    }

    @SubscribeEvent
    public void execute(GenericGuildVoiceEvent genericGuildVoiceEvent){

        //do some pre logging maybe?

        if(genericGuildVoiceEvent instanceof GuildVoiceJoinEvent){
            try{
                GuildVoiceJoinEvent joinEvent = (GuildVoiceJoinEvent) genericGuildVoiceEvent;

                String message = String.format(
                        "%s joined %s voice channel. %s",
                        joinEvent.getMember().getUser().getName(),
                        joinEvent.getChannelJoined().getName(),
                        iggyProperties.getYeetisms().get(rand.nextInt(iggyProperties.getYeetisms().size()))
                );

                sendToLogChannel(message, joinEvent.getJDA());
            }catch (Exception exception){
                logger.error("Error trying to log VoiceJoin", exception);
            }

        }else if(genericGuildVoiceEvent instanceof GuildVoiceLeaveEvent){
            try{
                GuildVoiceLeaveEvent leaveEvent = (GuildVoiceLeaveEvent) genericGuildVoiceEvent;

                String message = String.format(
                        "%s left %s voice channel. %s",
                        leaveEvent.getMember().getUser().getName(),
                        leaveEvent.getChannelLeft().getName(),
                        iggyProperties.getYeetisms().get(rand.nextInt(iggyProperties.getYeetisms().size()))
                );

                sendToLogChannel(message, leaveEvent.getJDA());
            }catch (Exception exception){
                logger.error("Error trying to log VoiceLeave", exception);
            }
        }else if(genericGuildVoiceEvent instanceof GuildVoiceMoveEvent){
            try{
                GuildVoiceMoveEvent moveEvent = (GuildVoiceMoveEvent) genericGuildVoiceEvent;

                String message = String.format(
                        "%s moved from  %s to %s. %s",
                        moveEvent.getMember().getUser().getName(),
                        moveEvent.getChannelLeft().getName(),
                        moveEvent.getChannelJoined().getName(),
                        iggyProperties.getYeetisms().get(rand.nextInt(iggyProperties.getYeetisms().size()))
                );

                sendToLogChannel(message, moveEvent.getJDA());
            }catch (Exception exception){
                logger.error("Error trying to log Voice Move", exception);
            }
        }
    }

    private void sendToLogChannel(@Nonnull String message, JDA jda) {
        jda.getTextChannelById(iggyProperties.getLogChannel()).sendMessage(message).queue();
    }


}
