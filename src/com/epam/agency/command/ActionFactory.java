package com.epam.agency.command;

import com.epam.agency.command.ActionCommand;
import com.epam.agency.command.CommandEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Никита on 31.01.2016.
 */
public class ActionFactory {
    public ActionCommand defineCommand(HttpServletRequest request) {
        String action = request.getParameter("command");
        CommandEnum currentEnum = CommandEnum.valueOf(modifyString(action));
        ActionCommand command = currentEnum.getCurrentCommand();
        return command;
    }

    /**
     * Modifies parameter "command" to Enum constants format
     * @param st parameter "command"
     * @return modified string
     */
    private String modifyString (String st) {
        char[] allLetters = st.toCharArray();
        ArrayList<String> upperCase = new ArrayList<>();
        StringBuilder result = new StringBuilder();

        for (char c : allLetters) {
            if(Character.isUpperCase(c)) {
                upperCase.add(String.valueOf(c));
            }
        }

        if(!upperCase.isEmpty()) {
            String[] strings = st.split("[A-Z]");
            for (int i = 0; i < strings.length; i++) {
                if (i <= strings.length - 2) {
                    result.append(strings[i].toUpperCase()+"_"+upperCase.get(i));
                } else {
                    result.append(strings[i].toUpperCase());
                }
            }
        } else {
            return st.toUpperCase();
        }
        return result.toString();
    }
}
