package ResistorBot;

import java.util.HashMap;

public class ResistorColorCodeParser {
    HashMap<String, ResistorRingColor> colorCodes;
    HashMap<ResistorRingColor, String> tolerances;
    String[] MULTIPLIER_PREFIX = new String[] {"", "k", "M", "G"};
    enum ResistorRingColor {
        BLACK,
        BROWN,
        RED,
        ORANGE,
        YELLOW,
        GREEN,
        BLUE,
        VIOLET,
        GREY,
        WHITE,
        GOLD,
        SILVER,
        NONE
    }

    public ResistorColorCodeParser() {
        colorCodes = new HashMap<String, ResistorRingColor>();
        colorCodes.put("black", ResistorRingColor.BLACK);
        colorCodes.put("schwarz", ResistorRingColor.BLACK);
        colorCodes.put("brown", ResistorRingColor.BROWN);
        colorCodes.put("braun", ResistorRingColor.BROWN);
        colorCodes.put("red", ResistorRingColor.RED);
        colorCodes.put("rot", ResistorRingColor.RED);
        colorCodes.put("orange", ResistorRingColor.ORANGE);
        colorCodes.put("yellow", ResistorRingColor.YELLOW);
        colorCodes.put("gelb", ResistorRingColor.YELLOW);
        colorCodes.put("green", ResistorRingColor.GREEN);
        colorCodes.put("grün", ResistorRingColor.GREEN);
        colorCodes.put("gruen", ResistorRingColor.GREEN);
        colorCodes.put("blue", ResistorRingColor.BLUE);
        colorCodes.put("blau", ResistorRingColor.BLUE);
        colorCodes.put("violet", ResistorRingColor.VIOLET);
        colorCodes.put("violett", ResistorRingColor.VIOLET);
        colorCodes.put("purple", ResistorRingColor.VIOLET);
        colorCodes.put("pink", ResistorRingColor.VIOLET);
        colorCodes.put("grey", ResistorRingColor.GREY);
        colorCodes.put("gray", ResistorRingColor.GREY);
        colorCodes.put("grau", ResistorRingColor.GREY);
        colorCodes.put("white", ResistorRingColor.WHITE);
        colorCodes.put("weiß", ResistorRingColor.WHITE);
        colorCodes.put("weiss", ResistorRingColor.WHITE);
        colorCodes.put("gold", ResistorRingColor.GOLD);
        colorCodes.put("silver", ResistorRingColor.SILVER);
        colorCodes.put("none", ResistorRingColor.NONE);

        tolerances = new HashMap<ResistorRingColor, String>();
        tolerances.put(ResistorRingColor.BROWN, "1");
        tolerances.put(ResistorRingColor.RED, "2");
        tolerances.put(ResistorRingColor.GREEN, "0,5");
        tolerances.put(ResistorRingColor.BLUE, "0,25");
        tolerances.put(ResistorRingColor.VIOLET, "0,1");
        tolerances.put(ResistorRingColor.GREY, "0,05");
        tolerances.put(ResistorRingColor.GREY, "0,05");
        tolerances.put(ResistorRingColor.GOLD, "5");
        tolerances.put(ResistorRingColor.SILVER, "10");
        tolerances.put(ResistorRingColor.NONE, "20");
    }

    public String parse(String message) {
        String[] colors = message.split("\s+");
        if (colors.length == 1 && getRingColor(colors[0]) == ResistorRingColor.BLACK) {
            return "0 \u03A9";
        }
        else if (colors.length == 3) {
            try {
                return getValueFromColors("black", colors[0], colors[1], colors[2], "none");
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        else if (colors.length == 4) {
            try {
                return getValueFromColors("black", colors[0], colors[1], colors[2], colors[3]);
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        else if (colors.length == 5) {
            try {
                return getValueFromColors(colors[0], colors[1], colors[2], colors[3], colors[4]);
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        } else {
            return error();
        }
    }

    public String getValueFromColors(String firstRing, String secondRing, String thirdRing, String fourthRing) throws Exception {
        return getValueFromColors("black", firstRing, secondRing, thirdRing, fourthRing);
    }

    public String getValueFromColors(String firstRing, String secondRing, String thirdRing, String fourthRing, String fifthRing) throws Exception {
        long value = getResistorValueFromRing(firstRing) * 100;
        value += getResistorValueFromRing(secondRing) * 10;
        value += getResistorValueFromRing(thirdRing);
        value *= Math.pow(10, getMultiplierFromRing(fourthRing));
        return formatResistorValue(value, fifthRing);
    }

    public String formatResistorValue(long value, String toleranceColor) throws Exception {
        String stringifiedValue = Long.toString(value);
        int mult = 0;
        while(stringifiedValue.endsWith("000")) {
            stringifiedValue = stringifiedValue.substring(0, stringifiedValue.length() - 3);
            mult++;
            if(mult > 5) {
                return error();
            }
        }
        if(stringifiedValue.endsWith("00") && stringifiedValue.length() > 3) {
            mult++;
            stringifiedValue = stringifiedValue.substring(0, stringifiedValue.length() - 3) + "," + stringifiedValue.substring(stringifiedValue.length() - 3, stringifiedValue.length() - 2);
        }

        String tolerance = getToleranceFromRing(toleranceColor) + "%";

        return stringifiedValue + " " + MULTIPLIER_PREFIX[mult] + "\u03A9 " + tolerance;
    }

    public long getResistorValueFromRing(String color) throws Exception {
        int value = getRingColor(color).ordinal();
        if(value > 9) {
            throw new Exception("resistor value impossible, colors do not match");
        }
        return value;
    }

    public int getMultiplierFromRing(String color) throws Exception {
        int multiplier = getRingColor(color).ordinal();
        if(multiplier > 9) {
            throw new Exception("can not parse multiplier smaller than 1");
        }
        return multiplier;
    }

    public ResistorRingColor getRingColor(String color) {
        return colorCodes.get(color.toLowerCase());
    }

    public String getToleranceFromRing(String color) throws Exception {
        String tolerance = tolerances.get(colorCodes.get(color.toLowerCase()));
        if(tolerance == null) {
            throw new Exception("tolerance is null");
        }
        return tolerance;
    }

    public String error() {
        return "Ich verstehe dich nicht.";
    }
}
