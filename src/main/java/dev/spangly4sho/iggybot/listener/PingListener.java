package dev.spangly4sho.iggybot.listener;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.springframework.stereotype.Component;

@Component
public class PingListener {

    @SubscribeEvent
    public void execute(MessageReceivedEvent messageEvent){

        Message msg = messageEvent.getMessage();
        if (msg.getContentRaw().equals("!ping"))
        {
            MessageChannel channel = messageEvent.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                    .queue(response /* => Message */ -> {
                        response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                    });
        }
    }
}
