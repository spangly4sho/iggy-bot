package dev.spangly4sho.iggybot.service.impl;


import dev.spangly4sho.iggybot.service.Command;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SpeakCommand  {

    @Command(name = "speak", minArguments = 1, maxArguments = Integer.MAX_VALUE, usage = "")
    public void execute(MessageReceivedEvent event) {



        MessageChannel channel = event.getChannel();
        long time = System.currentTimeMillis();
        channel.sendMessage("Bark!") /* => RestAction<Message> */
                .queue(response /* => Message */ -> {
                    response.editMessageFormat("Bark! %d ms", System.currentTimeMillis() - time).queue();
                });
    }
}
