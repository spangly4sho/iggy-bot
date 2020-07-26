package dev.spangly4sho.iggybot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;


public class SitCommand {

    @Command(name = "iggy sit", minArguments = 1, maxArguments = Integer.MAX_VALUE, usage = "")
    public void execute(MessageReceivedEvent event) {
        File iggySitsWebM = new File("content/IggySits.webm");
                event.getChannel().sendFile(iggySitsWebM).queue();
    }
}
