package vn.iotstar.service.impl;

import java.io.File;
import java.util.List;

import vn.iotstar.dao.CategoryDao;
import vn.iotstar.dao.impl.CategoryDaoImpl;
import vn.iotstar.model.Category;
import vn.iotstar.service.CategoryService;
import vn.iotstar.util.Constant;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public void insert(Category category) {
        categoryDao.insert(category);
    }

    @Override
    public void edit(Category newCategory) {
        Category oldCategory = categoryDao.get(newCategory.getId());
        if (oldCategory != null) {
            oldCategory.setName(newCategory.getName());
            if (newCategory.getIcon() != null) {
                // Xóa ảnh cũ nếu có
                String oldFileName = oldCategory.getIcon();
                if (oldFileName != null) {
                    File oldFile = new File(Constant.DIR + "/" + oldFileName);
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }
                oldCategory.setIcon(newCategory.getIcon());
            }
            categoryDao.edit(oldCategory);
        }
    }

    @Override
    public void delete(int id) {
        Category oldCategory = categoryDao.get(id);
        if (oldCategory != null && oldCategory.getIcon() != null) {
            File oldFile = new File(Constant.DIR + "/" + oldCategory.getIcon());
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }
        categoryDao.delete(id);
    }

    @Override
    public Category get(int id) {
        return categoryDao.get(id);
    }

    @Override
    public Category get(String name) {
        return categoryDao.get(name);
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    @Override
    public List<Category> search(String keyword) {
        return categoryDao.search(keyword);
    }
}

