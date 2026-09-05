package vn.iotstar.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "Product")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;

    @Column(name = "product_name", length = 255, nullable = false, columnDefinition = "NVARCHAR(255)")
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    @PositiveOrZero(message = "Giá sản phẩm phải lớn hơn hoặc bằng 0")
    private double price;

    @Column(name = "images", length = 255)
    private String images;

    @Column(name = "quantity")
    private int quantity = 0;

    @Column(name = "status")
    private int status = 1; // 1: Đang kinh doanh, 0: Ngừng kinh doanh

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate_id", nullable = false)
    private Category category;

    public Product() {
        super();
    }

    public Product(int id, String name, String description, double price, String images, int quantity, int status,
            Category category) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.images = images;
        this.quantity = quantity;
        this.status = status;
        this.category = category;
    }

    public Product(String name, String description, double price, String images, int quantity, int status,
            Category category) {
        super();
        this.name = name;
        this.description = description;
        this.price = price;
        this.images = images;
        this.quantity = quantity;
        this.status = status;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", status="
                + status + ", category=" + (category != null ? category.getName() : null) + "]";
    }
}

