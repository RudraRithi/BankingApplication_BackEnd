package com.Banking._Application.model;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.mail.internet.InternetAddress;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Component

public class Customer {
	
	@Id
	@SequenceGenerator(initialValue = 121401101, allocationSize = 1, sequenceName ="cust_id", name ="cust_id" )
	@GeneratedValue(generator="cust_id")
	int cust_id;
	String name;
	String email;
	String password;
	long mobile;
	Date dob;
	int age;
	boolean status;
	int Otp;
	
	@OneToMany(cascade = CascadeType.ALL)
	List<BankAccount> account;

	

	public int getCust_id() {
		return cust_id;
	}

	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
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

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getOtp() {
		return Otp;
	}

	public void setOtp(int otp) {
		Otp = otp;
	}

	public List<BankAccount> getAccount() {
		return account;
	}

	public void setAccount(List<BankAccount> account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Customer [cust_id=" + cust_id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", mobile=" + mobile + ", dob=" + dob + ", age=" + age + ", status=" + status + ", Otp=" + Otp
				+ ", account=" + account + "]";
	}

	public Customer() {
		super();
	}

	


}
