package vn.iotstar;

import java.util.List;
import org.junit.Test;
import jakarta.persistence.EntityManager;
import vn.iotstar.config.JPAConfig;
import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

public class CheckUserDbTest {
    @Test
    public void testListAllUsers() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            List<User> list = em.createQuery("SELECT u FROM User u", User.class).getResultList();
            System.out.println("====== DANH SACH USER TRONG DATABASE ======");
            System.out.println("Tong so user: " + list.size());
            for (User u : list) {
                System.out.println("ID: " + u.getId() 
                        + " | Username: [" + u.getUserName() + "]"
                        + " | Email: [" + u.getEmail() + "]"
                        + " | FullName: [" + u.getFullName() + "]"
                        + " | Status: " + u.getStatus());
            }
            System.out.println("==========================================");

            UserService service = new UserServiceImpl();
            if (!list.isEmpty()) {
                String testEmail = list.get(0).getEmail();
                System.out.println("Thu test findByEmail voi email: [" + testEmail + "]");
                User found = service.findByEmail(testEmail);
                System.out.println("Ket qua tim: " + (found != null ? "TIM THAY" : "KHONG TIM THAY"));

                if (testEmail != null) {
                    // Test voi chu hoa
                    User foundUpper = service.findByEmail(testEmail.toUpperCase());
                    System.out.println("Ket qua tim voi chu HOA (" + testEmail.toUpperCase() + "): " 
                            + (foundUpper != null ? "TIM THAY" : "KHONG TIM THAY"));
                }
            }
        } finally {
            em.close();
        }
    }
}

