package ResistorBot;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TwitchChatBot bot = new TwitchChatBot();
        bot.init();

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
