package dev.spangly4sho.iggybot.command;


import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;

public class SpeakCommand  {

    @Command(name = "iggy speak", minArguments = 1, maxArguments = Integer.MAX_VALUE, usage = "")
    public void execute(MessageReceivedEvent event) {



        MessageChannel channel = event.getChannel();
        long time = System.currentTimeMillis();
        channel.sendMessage("Bark!") /* => RestAction<Message> */
                .queue(response /* => Message */ -> {
                    response.editMessageFormat("Bark! %d ms", System.currentTimeMillis() - time).queue();
                });
        channel.sendFile(new File("content/iggy.png")).queue();
    }
}
