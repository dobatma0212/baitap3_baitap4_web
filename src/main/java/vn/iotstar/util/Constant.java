package vn.iotstar.util;

public class Constant {
    public static final String SESSION_USERNAME = "username";
    public static final String COOKIE_REMEMBER = "username";
    public static final String REGISTER = "/views/register.jsp";
    public static final String DIR = "C:\\upload";

    public static class Path {
        public static final String REGISTER = "/views/register.jsp";
        public static final String LOGIN = "/views/login.jsp";
        public static final String VERIFY_OTP = "/views/verify-otp.jsp";
        public static final String FORGOT_PASSWORD = "/views/forgot-password.jsp";
        public static final String RESET_PASSWORD = "/views/reset-password.jsp";
        public static final String ADMIN_CATEGORY_LIST = "/views/admin/list-category.jsp";
        public static final String ADMIN_CATEGORY_ADD = "/views/admin/add-category.jsp";
        public static final String ADMIN_CATEGORY_EDIT = "/views/admin/edit-category.jsp";
        public static final String ADMIN_PRODUCT_LIST = "/views/admin/list-product.jsp";
        public static final String ADMIN_PRODUCT_ADD = "/views/admin/add-product.jsp";
        public static final String ADMIN_PRODUCT_EDIT = "/views/admin/edit-product.jsp";
        public static final String WEB_PRODUCT = "/views/product.jsp";
        public static final String WEB_PRODUCT_DETAIL = "/views/product-detail.jsp";
    }

    public static class Mail {
        public static final String HOST = "smtp.gmail.com";
        public static final String PORT = "587";
        public static final String USERNAME = "dobbt@gmail.com"; 
        public static final String PASSWORD = "zkgp hjfq yrvu oezg"; 
        public static final String FROM_NAME = "Hệ Thống Xác Thực Tài Khoản";
    }
}
