package com.bootcamp.BootcampProject.utility;

public class ValidationRegex {
    public static final String PASSWORD = "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,}$";
    public static final String GST = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}";
    public static final String PHONE="(0/91)?[7-9][0-9]{9}";
    public static final String IMAGE= "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)";
    public static final String ISALPHA = "[a-zA-Z][a-zA-Z ]*";
    public static final String ADDRESSlINE= "^(\\w*\\s*[\\#\\-\\,\\/\\.\\(\\)\\&]*)+";
}
