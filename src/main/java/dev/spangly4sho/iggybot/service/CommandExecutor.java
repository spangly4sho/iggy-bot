package dev.spangly4sho.iggybot.service;

import java.lang.reflect.Method;

public class CommandExecutor {
    private String name;

    private String usage;

    private int minArguments;

    private int maxArguments;

    private Object executor;

    private Method executorMethod;


    public String getName() {
        return name;
    }

    public CommandExecutor setName(String name) {
        this.name = name;
        return this;
    }

    public String getUsage() {
        return usage;
    }

    public CommandExecutor setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public int getMinArguments() {
        return minArguments;
    }

    public CommandExecutor setMinArguments(int minArguments) {
        this.minArguments = minArguments;
        return this;
    }

    public int getMaxArguments() {
        return maxArguments;
    }

    public CommandExecutor setMaxArguments(int maxArguments) {
        this.maxArguments = maxArguments;
        return this;
    }

    public Object getExecutor() {
        return executor;
    }

    public CommandExecutor setExecutor(Object executor) {
        this.executor = executor;
        return this;
    }

    public Method getExecutorMethod() {
        return executorMethod;
    }

    public CommandExecutor setExecutorMethod(Method executorMethod) {
        this.executorMethod = executorMethod;
        return this;
    }
}
