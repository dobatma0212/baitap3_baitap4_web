package vn.iotstar.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Số điện thoại Việt Nam 10 chữ số (bắt đầu bằng 03, 05, 07, 08, 09)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(0[3|5|7|8|9])[0-9]{8}$");

    // Tên đăng nhập: 3-50 ký tự, gồm chữ cái, chữ số và gạch dưới
    private static final Pattern USERNAME_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_]{3,50}$");

    // Mã OTP: 6 chữ số
    private static final Pattern OTP_PATTERN = Pattern.compile(
            "^[0-9]{6}$");

    private static final Set<String> ALLOWED_IMAGE_EXTENSIONS = new HashSet<>(
            Arrays.asList(".jpg", ".jpeg", ".png", ".webp", ".gif")
    );

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username.trim()).matches();
    }

    public static boolean isValidOtp(String otp) {
        if (otp == null || otp.trim().isEmpty()) {
            return false;
        }
        return OTP_PATTERN.matcher(otp.trim()).matches();
    }

    public static boolean isValidImageFile(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return false;
        }
        int index = filename.lastIndexOf(".");
        if (index == -1) {
            return false;
        }
        String ext = filename.substring(index).toLowerCase();
        return ALLOWED_IMAGE_EXTENSIONS.contains(ext);
    }

    public static <T> String validateEntity(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            return violations.iterator().next().getMessage();
        }
        return null;
    }
}

