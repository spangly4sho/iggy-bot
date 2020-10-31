package dev.spangly4sho.iggybot.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.internal.requests.restaction.MessageActionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SitCommandTest {


    @Mock
    Message message;

    @Mock
    MessageChannel channel;

    @Mock
    MessageActionImpl messageAction;

    @Mock
    JDA api;

    @BeforeEach
    public void setUp(){


        when(message.getIdLong()).thenReturn(Long.valueOf(5678));
        when(message.getChannel()).thenReturn(channel);
        when(channel.sendFile(any())).thenReturn(messageAction);
    }

    @Test
    public void testExecute(){
        SitCommand command = new SitCommand();

        MessageReceivedEvent event = new MessageReceivedEvent(api, 1234, message);

        command.execute(event);

       verify(messageAction, atLeast(1)).queue();
    }
}
