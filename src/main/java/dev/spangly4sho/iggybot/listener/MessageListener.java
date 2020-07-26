package dev.spangly4sho.iggybot.listener;

import dev.spangly4sho.iggybot.config.IggyProperties;
import dev.spangly4sho.iggybot.command.CommandExecutor;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Component
public class MessageListener {
    static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    private IggyProperties iggyProperties;
    @Autowired
    private List<CommandExecutor> textCommands;

    public MessageListener() {
    }


    @SubscribeEvent
    public void execute(GenericMessageEvent messageEvent){

        //do some pre logging maybe?
        if(messageEvent instanceof MessageReceivedEvent){
            MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent) messageEvent;
            Message message = messageReceivedEvent.getMessage();
            textCommands.stream().filter(
                    commandExecutor -> message.getContentRaw().startsWith(commandExecutor.getName())
            ).forEach(
                    commandExecutor ->{
                        try
                        {
                            commandExecutor.getExecutorMethod().setAccessible(true);
                            commandExecutor.getExecutorMethod().invoke(commandExecutor.getExecutor(), messageReceivedEvent);
                        }
                        catch (IllegalAccessException | InvocationTargetException e1)
                        {
                            logger.error("Couldn't access annotated CommandExecutor method", e1);
                        }
                        catch (Throwable throwable)
                        {
                            logger.error("One of the CommandExecutor had an uncaught exception", throwable);
                            if (throwable instanceof Error)
                                throw (Error) throwable;
                        }
                    }
            );
        }
    }
}
