package com.epam.agency.command;

import com.epam.agency.util.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Никита on 10.02.2016.
 */
public class ChangeLanguageCommand implements ActionCommand {
    private static final String PARAM_LANGUAGE = "language";
    private static final String COMMAND = "getBurningTours";

    @Override
    public String execute(HttpServletRequest request) {

        String language = request.getParameter(PARAM_LANGUAGE);
        HttpSession session = request.getSession();
        session.setAttribute(PARAM_LANGUAGE, language);
        return URLBuilder.buildFullURL(request.getRequestURL(), COMMAND);
    }
}
