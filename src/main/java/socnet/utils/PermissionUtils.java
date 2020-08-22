package socnet.utils;

public class PermissionUtils {
    private static String methodToHttp(String method) {
        switch (method) {
            case "doGet":
                return "GET";
            case "doPost":
                return "POST";
        }
        return "";
    }

    public static String getServletPermissionName(String servletName, String methodName) {
        return servletName + "-" + methodToHttp(methodName);
    }

    public static String getHttpPermissionName(String servletName, String method) {
        return servletName + "-" + method;
    }
}
