package com.gruppo1.smarthome.model;

public class Television extends Device {
    private Integer volume;
    private Integer channel;

    public void setVolume(Integer volume) {this.volume = volume;}

    public void setChannel(Integer channel) {this.channel = channel;}

    public Integer getVolume(){return volume;}

    public Integer getChannel(){return channel;}

}
