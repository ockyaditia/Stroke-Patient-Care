package com.yarsi.skripsi.app;

public class AppConfig {

    public String con() {
        return "http://gatrine.hol.es";
    }

    // Server user login url
    public static String URL_LOGIN = "http://gatrine.hol.es/login.php";

    // Server user register url
    public static String URL_REGISTER = "http://gatrine.hol.es/register.php";

    // Server user medication url
    public static String URL_MEDICATION = "http://gatrine.hol.es/medication.php";

    // Server user medication url
    public static String URL_GET_MEDICATION = "http://gatrine.hol.es/get_medication.php";

    // Server user delete medication url
    public static String URL_DELETE_MEDICATION = "http://gatrine.hol.es/delete_medication.php";
}
