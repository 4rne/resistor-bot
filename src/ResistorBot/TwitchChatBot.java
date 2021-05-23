package ResistorBot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.jibble.pircbot.*;

public class TwitchChatBot extends PircBot {
    private ResistorColorCodeParser resistorColorCodeParser;

    public boolean init() {
        String token = "oauth:";
		setVerbose(true);

        resistorColorCodeParser = new ResistorColorCodeParser();

        try {
            URL url = getClass().getResource("./twitch_secret_token");
            BufferedReader reader = new BufferedReader(new FileReader(url.getPath()));
            token += reader.readLine();
            reader.close();
        } catch (Exception e) {
            System.err.println("Could not load chat oauth token from file: twitch_secret_token");
            System.exit(0);
        }
		try {
            connect("irc.twitch.tv", 6667, token);
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

	// joinChannel("#chrisfigge");
        return true;
    }

	public void onMessage(String channel, String sender, String login, String hostname, String message)
    {
        if(message.toLowerCase().startsWith("!resistor ") || message.toLowerCase().startsWith("!widerstand ")) {
            sendMessage(channel, "@" + login + " " + resistorColorCodeParser.parse(message.substring(message.indexOf(" ") + 1)));
        }
    }
}
