package vn.iotstar.dao.impl;

import java.util.Collections;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.config.JPAConfig;
import vn.iotstar.dao.ProductDao;
import vn.iotstar.model.Product;

public class ProductDaoImpl implements ProductDao {

    @Override
    public void insert(Product product) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(product);
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
    public void edit(Product product) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(product);
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
    public void delete(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Product product = em.find(Product.class, id);
            if (product != null) {
                em.remove(product);
            }
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
    public Product get(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT p FROM Product p JOIN FETCH p.category WHERE p.id = :id";
            TypedQuery<Product> query = em.createQuery(jpql, Product.class);
            query.setParameter("id", id);
            List<Product> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> getAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT p FROM Product p JOIN FETCH p.category ORDER BY p.id DESC";
            TypedQuery<Product> query = em.createQuery(jpql, Product.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> search(String keyword) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT p FROM Product p JOIN FETCH p.category WHERE p.name LIKE :keyword ORDER BY p.id DESC";
            TypedQuery<Product> query = em.createQuery(jpql, Product.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> getByCategoryId(int cateId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT p FROM Product p JOIN FETCH p.category WHERE p.category.id = :cateId ORDER BY p.id DESC";
            TypedQuery<Product> query = em.createQuery(jpql, Product.class);
            query.setParameter("cateId", cateId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> getTop10Newest() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT p FROM Product p JOIN FETCH p.category ORDER BY p.id DESC";
            TypedQuery<Product> query = em.createQuery(jpql, Product.class);
            query.setMaxResults(10);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    @Override
    public int countAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT COUNT(p) FROM Product p";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> getProductsByPage(int page, int pageSize) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT p FROM Product p JOIN FETCH p.category ORDER BY p.id DESC";
            TypedQuery<Product> query = em.createQuery(jpql, Product.class);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    @Override
    public int countByFilter(String keyword, Integer cateId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT COUNT(p) FROM Product p WHERE 1=1");
            if (keyword != null && !keyword.trim().isEmpty()) {
                jpql.append(" AND p.name LIKE :keyword");
            }
            if (cateId != null && cateId > 0) {
                jpql.append(" AND p.category.id = :cateId");
            }
            TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);
            if (keyword != null && !keyword.trim().isEmpty()) {
                query.setParameter("keyword", "%" + keyword.trim() + "%");
            }
            if (cateId != null && cateId > 0) {
                query.setParameter("cateId", cateId);
            }
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> getProductsByFilter(String keyword, Integer cateId, int page, int pageSize) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT p FROM Product p JOIN FETCH p.category WHERE 1=1");
            if (keyword != null && !keyword.trim().isEmpty()) {
                jpql.append(" AND p.name LIKE :keyword");
            }
            if (cateId != null && cateId > 0) {
                jpql.append(" AND p.category.id = :cateId");
            }
            jpql.append(" ORDER BY p.id DESC");
            TypedQuery<Product> query = em.createQuery(jpql.toString(), Product.class);
            if (keyword != null && !keyword.trim().isEmpty()) {
                query.setParameter("keyword", "%" + keyword.trim() + "%");
            }
            if (cateId != null && cateId > 0) {
                query.setParameter("cateId", cateId);
            }
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }
}

