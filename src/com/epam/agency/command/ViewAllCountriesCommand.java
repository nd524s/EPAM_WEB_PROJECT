package com.epam.agency.command;

import com.epam.agency.domain.Country;
import com.epam.agency.util.ResourceManager;
import com.epam.agency.service.CountryService;
import com.epam.agency.service.impl.CountryServiceImpl;
import com.epam.agency.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Никита on 31.01.2016.
 */
public class ViewAllCountriesCommand implements ActionCommand {
    private static final String ATTR_WARNING = "warn";
    private static final String ATTR_COUNTRIES = "countries";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        CountryService countryServiceImpl = CountryServiceImpl.getInstance();
        String page = ResourceManager.getProperty("page.countries");
        ArrayList<Country> countries = countryServiceImpl.getAllCountries();

        if(countries.isEmpty()) {
            request.setAttribute(ATTR_WARNING, ServerMessage.message(request.getSession(), "message.noCountries"));
        }
        request.setAttribute(ATTR_COUNTRIES, countries);
        return page;
    }
}
