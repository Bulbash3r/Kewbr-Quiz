package App.Models;

public class Parser {

    public static String[] parse(String message) {
        String[] strings;

        switch (message.substring(0, 3)) {
            case "<Q>":
                strings = new String[2];
                strings[0] = "Q";
                strings[1] = message.substring(3);
                break;

            case "<A>":
                strings = new String[3];
                strings[0] = "A";
                message = message.substring(3);
                strings[1] = message.split("</AN>")[0];
                strings[2] = message.split("</AN>")[1];
                break;

            case "<M>":
                strings = new String[3];
                strings[0] = "M";
                message = message.substring(3);
                strings[1] = message.split("</MN>")[0];
                strings[2] = message.split("</MN>")[1];
                break;

            case "<H>":
                strings = new String[2];
                strings[0] = "H";
                strings[1] = message.substring(3);
                break;

            case "<HA":
                strings = new String[3];
                strings[0] = "HA";
                message = message.substring(4);
                strings[1] = message.split("</HN>")[0];
                strings[2] = message.split("</HN>")[1];
                break;

            case "<I>":
                strings = new String[2];
                strings[0] = "I";
                strings[1] = message.substring(3);
                break;

            case "<O>":
                strings = new String[2];
                strings[0] = "O";
                strings[1] = message.substring(3);
                break;

            case "<W>":
                message = message.substring(3);

            default:
                strings = null;
                break;
        }

        return strings;
    }
}
