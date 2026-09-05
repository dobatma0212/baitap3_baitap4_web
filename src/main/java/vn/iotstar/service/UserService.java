package vn.iotstar.service;

import vn.iotstar.model.User;

public interface UserService {
    User login(String username, String password);
    User get(String username);
    User findByEmail(String email);
    void insert(User user);
    void update(User user);
    default void edit(User user) {
        update(user);
    }
    boolean register(String username, String password, String email, String fullname, String phone);
    boolean register(String username, String password, String email, String fullname, String phone, String code);
    boolean verifyOtp(String email, String otp);
    boolean resendOtp(String email, String newOtp);
    boolean sendForgotPasswordOtp(String email, String otp);
    boolean resetPassword(String email, String otp, String newPassword);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);
}

