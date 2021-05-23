package com.resistorbot;

import java.io.IOException;

import org.jibble.pircbot.*;

public class TwitchChatBot extends PircBot {
    private ResistorColorCodeParser resistorColorCodeParser;

    public boolean init(String token, String[] channels) {
        resistorColorCodeParser = new ResistorColorCodeParser();

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
        }

        return true;
    }

	public void onMessage(String channel, String sender, String login, String hostname, String message)
    {
        if(message.toLowerCase().startsWith("!resistor ") || message.toLowerCase().startsWith("!widerstand ")) {
            sendMessage(channel, "@" + login + " " + resistorColorCodeParser.parse(message.substring(message.indexOf(" ") + 1)));
        }
    }
}
