package vn.iotstar.model;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "User")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email", length = 150)
    @Email(message = "Email không đúng định dạng")
    private String email;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 50, message = "Tên đăng nhập từ 3 đến 50 ký tự")
    private String userName;

    @Column(name = "fullname", length = 150, columnDefinition = "NVARCHAR(150)")
    private String fullName;

    @Column(name = "password", length = 255, nullable = false)
    @NotBlank(message = "Mật khẩu không được để trống")
    private String passWord;

    @Column(name = "avatar", length = 255)
    private String avatar;

    @Column(name = "images", length = 255)
    private String images;

    @Column(name = "roleid")
    private Integer roleid;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "status")
    private Integer status = 0; // 0: Chờ kích hoạt qua OTP, 1: Đã kích hoạt

    @Column(name = "code", length = 10)
    private String code;

    @Column(name = "createddate")
    private Date createdDate;

    public User() {
        super();
    }

    public User(int id, String email, String userName, String fullName, String passWord, String avatar, int roleid,
            String phone, Date createdDate) {
        super();
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.fullName = fullName;
        this.passWord = passWord;
        this.avatar = avatar;
        this.roleid = roleid;
        this.phone = phone;
        this.createdDate = createdDate;
    }

    public User(String email, String userName, String fullName, String passWord, String avatar, int roleid,
            String phone, Date createdDate) {
        super();
        this.email = email;
        this.userName = userName;
        this.fullName = fullName;
        this.passWord = passWord;
        this.avatar = avatar;
        this.roleid = roleid;
        this.phone = phone;
        this.createdDate = createdDate;
    }

    public User(String email, String userName, String fullName, String passWord, String avatar, int roleid,
            int status, String code, String phone, Date createdDate) {
        super();
        this.email = email;
        this.userName = userName;
        this.fullName = fullName;
        this.passWord = passWord;
        this.avatar = avatar;
        this.roleid = roleid;
        this.status = status;
        this.code = code;
        this.phone = phone;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", userName=" + userName + ", fullName=" + fullName
                + ", passWord=" + passWord + ", phone=" + phone + ", images=" + images + ", avatar=" + avatar
                + ", roleid=" + roleid + ", status=" + status + ", code=" + code + ", createdDate=" + createdDate + "]";
    }
}
