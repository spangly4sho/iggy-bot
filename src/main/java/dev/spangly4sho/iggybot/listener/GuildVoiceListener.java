package dev.spangly4sho.iggybot.listener;

import dev.spangly4sho.iggybot.config.IggyProperties;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
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
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

                if(channelHidden(joinEvent.getChannelJoined().getName())){
                     List<VoiceChannel> channels = Stream.of(joinEvent.getChannelJoined()).collect(Collectors.toList());
                    directMessagePrivlegedUsers(message,joinEvent.getGuild(),channels);
                }else{
                    sendToLogChannel(message, joinEvent.getJDA());
                }

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

                if(channelHidden(leaveEvent.getChannelLeft().getName())){
                    List<VoiceChannel> channels = Stream.of(leaveEvent.getChannelLeft()).collect(Collectors.toList());
                    directMessagePrivlegedUsers(message,leaveEvent.getGuild(),channels);
                }else{
                    sendToLogChannel(message, leaveEvent.getJDA());
                }

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
                if(channelHidden(moveEvent.getChannelJoined().getName()) || channelHidden(moveEvent.getChannelLeft().getName())){
                    List<VoiceChannel> channels = Stream.of(moveEvent.getChannelJoined(), moveEvent.getChannelLeft()).collect(Collectors.toList());
                    directMessagePrivlegedUsers(message,moveEvent.getGuild(),channels);
                }else{
                    sendToLogChannel(message, moveEvent.getJDA());
                }
            }catch (Exception exception){
                logger.error("Error trying to log Voice Move", exception);
            }
        }
    }

    private void sendToLogChannel(@Nonnull String message, JDA jda) {
        jda.getTextChannelById(iggyProperties.getLogChannel()).sendMessage(message).queue();
    }

    //direct message users with the proper role for tha channels involved
    private void directMessagePrivlegedUsers(@Nonnull String message, Guild guild, List<VoiceChannel> channels){
        List<Role> roleList = new ArrayList<>();
        for(VoiceChannel channel: channels){
            channel.getRolePermissionOverrides().stream().forEach(permissionOverride -> {
                if(roleWithPermissions(permissionOverride) && !roleList.contains(guild.getRoleById(permissionOverride.getId()))){
                    roleList.add(guild.getRoleById(permissionOverride.getId()));
                }
            });
        }
        List<Member> members = guild.getMembersWithRoles(roleList);

        members.stream().forEach(member -> member.getUser().openPrivateChannel().queue((channel) -> {
            channel.sendMessage("Channel Notification: " + message).queue();
        }));
    }

    private boolean channelHidden(String channel){
        return iggyProperties.getHiddenChannels().contains(channel);
    }

    private boolean roleWithPermissions(PermissionOverride permissionOverride){
        return permissionOverride.getAllowed().contains(Permission.VIEW_CHANNEL) && !permissionOverride.getAllowed().contains(Permission.MANAGE_PERMISSIONS);
    }

}
