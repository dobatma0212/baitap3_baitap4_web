package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig {
    private static EntityManagerFactory factory;

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (factory == null || !factory.isOpen()) {
            try {
                // Khởi tạo EntityManagerFactory theo tên persistence-unit trong persistence.xml
                factory = Persistence.createEntityManagerFactory("baitap1");
            } catch (Exception ex) {
                System.err.println("Khởi tạo EntityManagerFactory thất bại: " + ex.getMessage());
                ex.printStackTrace();
                throw new RuntimeException("Không thể kết nối JPA: " + ex.getMessage(), ex);
            }
        }
        return factory;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static synchronized void shutdown() {
        if (factory != null && factory.isOpen()) {
            factory.close();
            factory = null;
        }
    }

    public static void main(String[] args) {
        try {
            EntityManager em = JPAConfig.getEntityManager();
            System.out.println(">>> Kết nối JPA + Hibernate tới CSDL thành công!");
            System.out.println("EntityManager: " + em);
            em.close();
            JPAConfig.shutdown();
        } catch (Exception e) {
            System.err.println(">>> Kết nối JPA + Hibernate thất bại: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
