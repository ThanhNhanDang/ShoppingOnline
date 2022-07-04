package com.java.entity;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long 	id;
	private String 	name;
	private String 	email;
	private String 	password;
	private Instant	created_at;
	private String	login_token;
	private String	type;
	private String	address;
	private boolean	is_email_verfied;
	private String	mobile;
	private String 	image_url;
	private int 	role_id;
	private String 	gender;
	private long 	deliveryAddressId;

	
	@ManyToOne
	@JoinColumn(name = "role_id", insertable = false, updatable = false)
	private Role 	role;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<AddToCart> carts;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<CheckOutCart> checkOutCarts;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<WishList> wishList;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<DeliveryAddress> deliveryAddress;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Review> reviews;
	
	public User() {}
	
	
	
	public User(long id, String name, String email, String password, Instant created_at, String login_token, String type,
			String address, boolean is_email_verfied, String mobile, String image_url, int role_id, long deliveryAddressId) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.created_at = created_at;
		this.login_token = login_token;
		this.type = type;
		this.address = address;
		this.is_email_verfied = is_email_verfied;
		this.mobile = mobile;
		this.image_url = image_url;
		this.role_id = role_id;
		this.deliveryAddressId = deliveryAddressId;
	}

	public String getImage_url() {
		return image_url;
	}
	
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Instant getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Instant created_at) {
		this.created_at = created_at;
	}
	public String getLogin_token() {
		return login_token;
	}
	public void setLogin_token(String login_token) {
		this.login_token = login_token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean getIs_email_verfied() {
		return is_email_verfied;
	}
	public void setIs_email_verfied(boolean is_email_verfied) {
		this.is_email_verfied = is_email_verfied;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public Role getRole() {
		return role;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getDeliveryAddressId() {
		return deliveryAddressId;
	}

	public void setDeliveryAddressId(long deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}

}
