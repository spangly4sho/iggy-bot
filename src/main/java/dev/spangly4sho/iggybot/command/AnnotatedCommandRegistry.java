package dev.spangly4sho.iggybot.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class AnnotatedCommandRegistry {


    public AnnotatedCommandRegistry() {

    }

    public Set<Object> register() {
        Set<Object> commands = ConcurrentHashMap.newKeySet();

        commands.add(new SpeakCommand());
        commands.add(new SitCommand());

        return commands;
    }

    @Bean
    @Qualifier("textCommands")
    public List<CommandExecutor> textCommands() {
        List<CommandExecutor> methods = new ArrayList<>();
        Set<Object> commands = register();

        updateMethods(methods, commands);
        return methods;
    }

    private void updateMethods(List<CommandExecutor> methods, Set<Object> commands) {
        methods.clear();
        for (Object command : commands) {
            boolean isClass = command instanceof Class;
            Class<?> c = isClass ? (Class) command : command.getClass();
            Method[] allMethods = c.getDeclaredMethods();
            for (Method m : allMethods) {
                if (m.isAnnotationPresent(Command.class)) {
                    Command commandInfo = m.getAnnotation(Command.class);
                    methods.add(new CommandExecutor()
                            .setName(commandInfo.name())
                            .setUsage(commandInfo.usage())
                            .setMaxArguments(commandInfo.maxArguments())
                            .setMinArguments(commandInfo.minArguments())
                            .setExecutorMethod(m)
                            .setExecutor(command)
                    );
                }
            }
        }
    }
}
