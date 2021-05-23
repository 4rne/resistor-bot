FROM openjdk:16

COPY lib/ /app/lib
COPY  src/ /app/src

RUN javac -cp /app/lib/pircbot.jar /app/src/com/resistorbot/Bot.java /app/src/com/resistorbot/TwitchChatBot.java /app/src/com/resistorbot/ResistorColorCodeParser.java

CMD ["java", "-cp", "/app/lib/pircbot.jar:/app/src/", "com.resistorbot.Bot"]