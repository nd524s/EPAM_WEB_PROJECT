package com.epam.agency.command;

import com.epam.agency.command.check.RegistrationCheck;
import com.epam.agency.domain.User;
import com.epam.agency.util.ResourceManager;
import com.epam.agency.util.PasswordHash;
import com.epam.agency.service.UserService;
import com.epam.agency.service.impl.UserServiceImpl;
import com.epam.agency.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Никита on 05.02.2016.
 */
public class RegistrateCommand implements ActionCommand {
    private static final String PARAM_FNAME = "fname";
    private static final String PARAM_LNAME = "lname";
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_TELEPHONE_NUMBER = "telNumber";
    private static final String SESSION_ROLE = "role";
    private static final String SESSION_USER = "user";
    private static final String ATTR_WARNING = "warn";
    private static final String COMMAND = "getBurningTours";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String page;
        HttpSession session = request.getSession();
        String firstName = request.getParameter(PARAM_FNAME);
        String lastName = request.getParameter(PARAM_LNAME);
        String login = request.getParameter(PARAM_USERNAME);
        String password = request.getParameter(PARAM_PASSWORD);
        String telNumber = request.getParameter(PARAM_TELEPHONE_NUMBER);
        String warn = RegistrationCheck.checkRegistrationData(session, firstName,
                lastName, telNumber, login, password);
        if(warn != null) {
            request.setAttribute(ATTR_WARNING, warn);
            return ResourceManager.getProperty("page.registration");
        }
        password = PasswordHash.md5Hashing(password);
        User user = new User(firstName, lastName, password, login, telNumber);
        page = authenticate(user, session, request);

        return page;
    }

    /**
     * Authenticates user and depends on the result
     * returns main or registration page.
     * @param user
     * @param session
     * @param request
     * @return "registration.jsp" or URL for redirect to main page.
     * @throws ServiceException
     */
    private String authenticate(User user, HttpSession session, HttpServletRequest request) throws ServiceException {
        UserService service = UserServiceImpl.getInstance();

        if(service.addUser(user)) {
            session.setAttribute(SESSION_ROLE, service.getByLoginPassword(user.getLogin(), user.getPassword())
                    .getRole()
                    .getRoleName());
            session.setAttribute(SESSION_USER, user);
           return URLBuilder.buildFullURL(request.getRequestURL(), COMMAND);
        } else {
            request.setAttribute(ATTR_WARNING, ServerMessage.message(request.getSession(), "message.registration"));
            return ResourceManager.getProperty("page.registration");
        }
    }
}
