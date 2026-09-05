package vn.iotstar.service.impl;

import vn.iotstar.dao.UserDao;
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.model.User;
import vn.iotstar.service.UserService;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User user = this.get(username);
        if (user != null && password.equals(user.getPassWord())) {
            return user;
        }
        return null;
    }

    @Override
    public User get(String username) {
        return userDao.get(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean register(String username, String password, String email, String fullname, String phone) {
        return register(username, password, email, fullname, phone, null);
    }

    @Override
    public boolean register(String username, String password, String email, String fullname, String phone, String code) {
        if (userDao.checkExistUsername(username) || userDao.checkExistEmail(email)) {
            return false;
        }
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        // status = 0 (chờ kích hoạt OTP), code = mã OTP
        int status = (code != null && !code.isEmpty()) ? 0 : 1;
        userDao.insert(new User(email, username, fullname, password, null, 5, status, code, phone, date));
        return true;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        if (email == null || otp == null) {
            return false;
        }
        User user = userDao.findByEmail(email.trim());
        if (user != null && user.getCode() != null && user.getCode().trim().equals(otp.trim())) {
            user.setStatus(1);
            user.setCode(null);
            userDao.update(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean resendOtp(String email, String newOtp) {
        if (email == null) {
            return false;
        }
        User user = userDao.findByEmail(email.trim());
        if (user != null) {
            user.setCode(newOtp);
            userDao.update(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean sendForgotPasswordOtp(String email, String otp) {
        if (email == null || otp == null) {
            return false;
        }
        User user = userDao.findByEmail(email.trim());
        if (user != null) {
            user.setCode(otp);
            userDao.update(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean resetPassword(String email, String otp, String newPassword) {
        if (email == null || otp == null || newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }
        User user = userDao.findByEmail(email.trim());
        if (user != null && user.getCode() != null && user.getCode().trim().equals(otp.trim())) {
            user.setPassWord(newPassword.trim());
            user.setCode(null); // Xóa OTP sau khi sử dụng thành công
            user.setStatus(1);  // Đảm bảo tài khoản được kích hoạt
            userDao.update(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userDao.checkExistPhone(phone);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }
}

