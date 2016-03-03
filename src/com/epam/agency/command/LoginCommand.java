package com.epam.agency.command;

import com.epam.agency.command.check.LoginCheck;
import com.epam.agency.domain.User;
import com.epam.agency.util.ResourceManager;
import com.epam.agency.util.PasswordHash;
import com.epam.agency.service.UserService;
import com.epam.agency.service.impl.UserServiceImpl;
import com.epam.agency.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Никита on 08.02.2016.
 */
public class LoginCommand implements ActionCommand {
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String SESSION_ROLE = "role";
    private static final String SESSION_USER = "user";
    private static final String ATTR_WARN = "warn";
    private static final String COMMAND = "getBurningTours";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        UserService service = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String login = request.getParameter(PARAM_USERNAME);
        String password = request.getParameter(PARAM_PASSWORD);
        String warn = LoginCheck.checkLoginData(session, login, password);
        String loginPage = ResourceManager.getProperty("page.login");

        if(warn != null) {
            request.setAttribute(ATTR_WARN, warn);
            return loginPage;
        }
        password = PasswordHash.md5Hashing(password);
        User user = service.getByLoginPassword(login, password);

        if (user != null) {
            session.setAttribute(SESSION_ROLE, user.getRole().getRoleName());
            session.setAttribute(SESSION_USER, user);
        } else {
            request.setAttribute(ATTR_WARN, ServerMessage.message(request.getSession(), "message.authorization"));
            return loginPage;
        }
        return URLBuilder.buildFullURL(request.getRequestURL(), COMMAND);
    }

}
