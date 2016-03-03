package com.epam.agency.controller;

import com.epam.agency.command.ActionCommand;
import com.epam.agency.command.ActionFactory;
import com.epam.agency.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Никита on 31.01.2016.
 */
@WebServlet("/epam")
public class Controller extends HttpServlet {

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ActionFactory client = new ActionFactory();
        ActionCommand actionCommand = client.defineCommand(req);

        try {
            String page = actionCommand.execute(req);
            if(page.contains("?")) {
                resp.sendRedirect(page);
            } else {
                req.getRequestDispatcher(page).forward(req, resp);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }
}
