package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.config.JPAConfig;
import vn.iotstar.dao.UserDao;
import vn.iotstar.model.User;

public class UserDaoImpl implements UserDao {

    @Override
    public User get(String username) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.userName = :username";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("username", username);
            List<User> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) return null;
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE LOWER(TRIM(u.email)) = LOWER(TRIM(:email))";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("email", email.trim());
            List<User> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception e) {
            System.err.println("Lỗi truy vấn User findByEmail: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(User user) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(user);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(User user) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean checkExistEmail(String email) {
        if (email == null) return false;
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT COUNT(u) FROM User u WHERE LOWER(TRIM(u.email)) = LOWER(TRIM(:email))";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("email", email.trim());
            Long count = query.getSingleResult();
            return count != null && count > 0;
        } catch (Exception e) {
            System.err.println("Lỗi truy vấn User checkExistEmail: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean checkExistUsername(String username) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT COUNT(u) FROM User u WHERE u.userName = :username";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("username", username);
            Long count = query.getSingleResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean checkExistPhone(String phone) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT COUNT(u) FROM User u WHERE u.phone = :phone";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("phone", phone);
            Long count = query.getSingleResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
