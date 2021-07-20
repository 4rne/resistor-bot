package com.resistorbot;

import java.io.IOException;

import org.jibble.pircbot.*;

public class TwitchChatBot extends PircBot {
    private ResistorColorCodeParser resistorColorCodeParser;
    private String token;
    private String[] channels;


    public boolean init(String token, String[] channels) {
        resistorColorCodeParser = new ResistorColorCodeParser();
        token = this.token;
        channels = this.channels.clone();

        return connect();
    }

    public boolean connect() {
        try {
            connect("irc.twitch.tv", 6667, "oauth:" + token);
        } catch (NickAlreadyInUseException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (IrcException e) {
            e.printStackTrace();
            return false;
        }
        for (String channel : channels) {
            joinChannel("#" + channel);
            System.out.println("Connected to: " + channel);
        }

        return true;
    }

    @Override
    protected void onDisconnect() {
        super.onDisconnect();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connect();
    }

	public void onMessage(String channel, String sender, String login, String hostname, String message)
    {
        if(message.toLowerCase().startsWith("!resistor help") || message.toLowerCase().startsWith("!widerstand help")) {
            System.out.println(login + ": " + message);
            sendMessage(channel, "@" + login + " Find me on GitHub /4rne/resistor-bot");
        }
        else if(message.toLowerCase().startsWith("!resistor ") || message.toLowerCase().startsWith("!widerstand ")) {
            System.out.println(login + ": " + message);
            sendMessage(channel, "@" + login + " " + resistorColorCodeParser.parse(message.substring(message.indexOf(" ") + 1)));
        }
    }
}
