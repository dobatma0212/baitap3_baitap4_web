package vn.iotstar.service;

import java.util.List;
import vn.iotstar.model.Product;

public interface ProductService {
    void insert(Product product);
    void edit(Product product);
    void delete(int id);
    Product get(int id);
    List<Product> getAll();
    List<Product> search(String keyword);
    List<Product> getByCategoryId(int cateId);
    List<Product> getTop10Newest();
    int countAll();
    List<Product> getProductsByPage(int page, int pageSize);
    int countByFilter(String keyword, Integer cateId);
    List<Product> getProductsByFilter(String keyword, Integer cateId, int page, int pageSize);
}

