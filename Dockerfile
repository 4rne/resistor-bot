FROM openjdk:8-alpine

COPY lib src /app/

RUN javac -cp /app/lib/pircbot.jar /app/src/com/resistorbot/Bot.java /app/src/com/resistorbot/TwitchChatBot.java /app/src/com/resistorbot/ResistorColorCodeParser.java

CMD ["java", "-cp", "/app/lib/pircbot.jar:/app/src/", "com.resistorbot.Bot"]