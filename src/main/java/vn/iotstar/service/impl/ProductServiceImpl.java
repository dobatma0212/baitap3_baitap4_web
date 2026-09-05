package vn.iotstar.service.impl;

import java.io.File;
import java.util.List;

import vn.iotstar.dao.ProductDao;
import vn.iotstar.dao.impl.ProductDaoImpl;
import vn.iotstar.model.Product;
import vn.iotstar.service.ProductService;
import vn.iotstar.util.Constant;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao = new ProductDaoImpl();

    @Override
    public void insert(Product product) {
        productDao.insert(product);
    }

    @Override
    public void edit(Product newProduct) {
        Product oldProduct = productDao.get(newProduct.getId());
        if (oldProduct != null) {
            oldProduct.setName(newProduct.getName());
            oldProduct.setDescription(newProduct.getDescription());
            oldProduct.setPrice(newProduct.getPrice());
            oldProduct.setQuantity(newProduct.getQuantity());
            oldProduct.setStatus(newProduct.getStatus());
            oldProduct.setCategory(newProduct.getCategory());
            if (newProduct.getImages() != null) {
                // Xóa file ảnh cũ trên ổ đĩa nếu có
                String oldFileName = oldProduct.getImages();
                if (oldFileName != null) {
                    File oldFile = new File(Constant.DIR + "/" + oldFileName);
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }
                oldProduct.setImages(newProduct.getImages());
            }
            productDao.edit(oldProduct);
        }
    }

    @Override
    public void delete(int id) {
        Product oldProduct = productDao.get(id);
        if (oldProduct != null && oldProduct.getImages() != null) {
            File oldFile = new File(Constant.DIR + "/" + oldProduct.getImages());
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }
        productDao.delete(id);
    }

    @Override
    public Product get(int id) {
        return productDao.get(id);
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public List<Product> search(String keyword) {
        return productDao.search(keyword);
    }

    @Override
    public List<Product> getByCategoryId(int cateId) {
        return productDao.getByCategoryId(cateId);
    }

    @Override
    public List<Product> getTop10Newest() {
        return productDao.getTop10Newest();
    }

    @Override
    public int countAll() {
        return productDao.countAll();
    }

    @Override
    public List<Product> getProductsByPage(int page, int pageSize) {
        return productDao.getProductsByPage(page, pageSize);
    }

    @Override
    public int countByFilter(String keyword, Integer cateId) {
        return productDao.countByFilter(keyword, cateId);
    }

    @Override
    public List<Product> getProductsByFilter(String keyword, Integer cateId, int page, int pageSize) {
        return productDao.getProductsByFilter(keyword, cateId, page, pageSize);
    }
}

