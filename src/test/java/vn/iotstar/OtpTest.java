package vn.iotstar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import vn.iotstar.util.EmailUtil;

public class OtpTest {

    @Test
    public void testGenerateOtp() {
        for (int i = 0; i < 50; i++) {
            String otp = EmailUtil.generateOtp();
            assertNotNull(otp);
            assertEquals("OTP phải gồm đúng 6 chữ số", 6, otp.length());
            assertTrue("OTP phải là các chữ số", otp.matches("\\d{6}"));
            int val = Integer.parseInt(otp);
            assertTrue("OTP phải nằm trong khoảng 100000 - 999999", val >= 100000 && val <= 999999);
        }
    }
}

