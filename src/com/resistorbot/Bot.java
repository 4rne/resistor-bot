package com.resistorbot;

import java.util.Scanner;

public class Bot {
    public static void main(String[] args) {
        TwitchChatBot bot = new TwitchChatBot();
        String token = System.getenv().get("TWITCH_API_TOKEN");
        if(token == null) {
            System.err.println("no TWITCH_API_TOKEN provided");
            System.exit(1);
        }
        String channels = System.getenv().get("JOIN_CHANNELS");
        if(channels == null) {
            System.err.println("JOIN_CHANNELS not provided");
            System.exit(1);
        }
        String[] channelList = channels.split(",");
        bot.init(token, channelList);

        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                String input = scanner.nextLine();
                if(input.equals("stop")) {
                    bot.disconnect();
                    scanner.close();
                    System.exit(0);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
