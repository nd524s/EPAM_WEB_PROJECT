package com.epam.agency.command;

import com.epam.agency.util.ResourceManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Никита on 10.03.2016.
 */
public class ErrorHandler {
    private static final String ERROR_ATR = "error";

    public static void handleError(HttpServletRequest req, HttpServletResponse resp, Exception e) throws ServletException, IOException {
        req.setAttribute(ERROR_ATR, e.getMessage());
        req.getRequestDispatcher(ResourceManager.getProperty("page.error")).forward(req, resp);
    }


}
