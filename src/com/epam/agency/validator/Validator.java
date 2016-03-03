package com.epam.agency.validator;

import com.epam.agency.util.ResourceManager;
import com.epam.agency.command.ServerMessage;


import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Никита on 19.02.2016.
 */
public class Validator {
    private static final String CARD_NUMBER_REGEX = "[0-9]{16}";
    private static final String NAME_REGEX = "^([A-Za-z]{1,40}|[а-яА-ЯЁё]{1,40})$";
    private static final String TEL_NUMBER_REGEX = "^(\\+375)(29|33|44)([1-9]){7}$";
    private static final String USERNAME_REGEX = "^(([a-zA-Z]{1})([a-zA-Z0-9]){2,18}([a-zA-z]))$";
    private static final String PASSWORD_REGEX = "^([a-zA-Z0-9]{4,20})$";
    private static final String DATE_REGEX = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";
    private static final String COST_REGEX = "^([1-9][0-9]*(.[0-9]{1,2})?|[0])$";
    private static final String ITEM_NUMBER_REGEX = "^([1]{1}|[1-9]?[0-9]+)$";

    public static boolean validateCardNumber(String str) {
        return check(str, CARD_NUMBER_REGEX);
    }

    public static boolean validateName(String str) {
        return check(str, NAME_REGEX);
    }

    public static boolean validateTelNumber(String str) {
        return check(str, TEL_NUMBER_REGEX);
    }

    public static boolean validateUsername(String str) {
        return check(str, USERNAME_REGEX);
    }

    public static boolean validatePassword(String str) {
        return check(str, PASSWORD_REGEX);
    }

    public static boolean validateCost(String str) {
        return check(str, COST_REGEX);
    }

    public static boolean validateItemNumber(String str) {
        return check(str, ITEM_NUMBER_REGEX);
    }

    public static boolean validateDate(String begDate, String endDate) {
        String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Date currentDate = java.sql.Date.valueOf(modifiedDate);
        if( !check(begDate, DATE_REGEX) && !check(endDate, DATE_REGEX)) {
            return false;
        }
        Date beginDate =  java.sql.Date.valueOf(begDate);
        Date eDate =  java.sql.Date.valueOf(endDate);
        if(eDate.compareTo(beginDate) >= 0 && beginDate.compareTo(currentDate) >= 0){
            return true;
        } else {
            return false;
        }
    }

    private static boolean check(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        if(matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
