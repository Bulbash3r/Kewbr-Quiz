package App.Models;

public class Parser {

    public static String[] parse(String message) {
        String[] strings;

        if (message.substring(0, 3).equals("<M>") || message.substring(0, 3).equals("<A>") || message.substring(0, 3).equals("<I>"))
            strings = new String[3];
        else
            strings = new String[2];

        switch (message.substring(0, 3)) {
            case "<Q>":
                strings[0] = "Q";
                strings[1] = message.substring(3);
                break;

            case "<A>":
                strings[0] = "A";
                message = message.substring(3);
                strings[1] = message.split("</AN>")[0];
                strings[2] = message.split("</AN>")[1];
                break;

            case "<M>":
                strings[0] = "M";
                message = message.substring(3);
                strings[1] = message.split("</MN>")[0];
                strings[2] = message.split("</MN>")[1];
                break;

            case "<H>":
                strings[0] = "H";
                strings[1] = message.substring(3);
                break;

            case "<I>":
                strings[0] = "I";
                message = message.substring(3);
                strings[1] = message.split("<IN>")[0];
                strings[2] = message.split("<IN>")[1];
                break;

            case "<C>":
                strings[0] = "C";
                strings[1] = message.substring(3);
                break;

            default:
                break;
        }

        return strings;
    }
}
