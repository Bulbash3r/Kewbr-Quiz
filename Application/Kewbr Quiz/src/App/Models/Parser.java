package App.Models;

public class Parser {

    public static String[] parse(String message) {
        String[] strs;

        if (message.substring(0, 3).equals("<M>"))
            strs = new String[3];
        else
            strs = new String[2];

        switch (message.substring(0, 3)) {
            case "<M>":
                strs[0] = "M";
                message = message.substring(3);
                strs[1] = message.split("</MN>")[0];
                strs[2] = message.split("</MN>")[1];
                break;

            case "<A>":
                strs[0] = "A";
                strs[1] = message.substring(3);
                break;

            case "<Q>":
                strs[0] = "Q";
                strs[1] = message.substring(3);
                break;

            case "<H>":
                strs[0] = "H";
                strs[1] = message.substring(3);
                break;

            default:
                break;
        }

        return strs;
    }
}
