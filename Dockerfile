FROM openjdk:16

COPY lib/ src/ /app/

RUN javac -cp /app/pircbot.jar /app/com/resistorbot/Bot.java /app/com/resistorbot/TwitchChatBot.java /app/com/resistorbot/ResistorColorCodeParser.java

CMD ["java", "-cp", "/app/src/", "com.resistorbot.Bot"]