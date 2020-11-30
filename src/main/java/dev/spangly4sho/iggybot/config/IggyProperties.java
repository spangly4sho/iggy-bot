package dev.spangly4sho.iggybot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("iggy.props")
public class IggyProperties {

    private String prefix;

    private String activity;

    private long logChannel;

    private List<String> yeetisms;

    private List<String> hiddenChannels;

    public String getPrefix() {
        return prefix;
    }

    public IggyProperties setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getActivity() {
        return activity;
    }

    public IggyProperties setActivity(String activity) {
        this.activity = activity;
        return this;
    }

    public long getLogChannel() {
        return logChannel;
    }

    public IggyProperties setLogChannel(long logChannel) {
        this.logChannel = logChannel;
        return this;
    }

    public List<String> getYeetisms() {
        return yeetisms;
    }

    public IggyProperties setYeetisms(List<String> yeetisms) {
        this.yeetisms = yeetisms;
        return this;
    }

    public List<String> getHiddenChannels() {
        return hiddenChannels;
    }

    public IggyProperties setHiddenChannels(List<String> hiddenChannels) {
        this.hiddenChannels = hiddenChannels;
        return this;
    }
}
