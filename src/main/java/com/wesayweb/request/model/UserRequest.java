package com.wesayweb.request.model;

import java.io.Serializable;
import java.util.Date;

public class UserRequest implements Serializable {

	public UserRequest() {
		super();
	}

	public UserRequest(Long id) {
		super();
		this.id = id;
	}

	private Long id;
	private String emailaddress;
	private String password;
	private String countrycode;
	private String mobilenumber;
	private int isregisteredbymobile;
	private String dateofbirth;
	private String gender;
	private Date creationdate = new Date();
	private int isactive;
	private String title;
	private String firstname;
	private String middlename;
	private String lastname;

	public UserRequest(String countrycode, String mobilenumber, int isregisteredbymobile, String dateofbirth,
			String gender) {
		super();
		this.countrycode = countrycode;
		this.mobilenumber = mobilenumber;
		this.isregisteredbymobile = isregisteredbymobile;
		this.dateofbirth = dateofbirth;
		this.gender = gender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", emailaddress=" + emailaddress + ", password=" + password + ", countrycode="
				+ countrycode + ", mobilenumber=" + mobilenumber + ", isregisteredbymobile=" + isregisteredbymobile
				+ ", dateofbirth=" + dateofbirth + ", gender=" + gender + ", creationdate=" + creationdate
				+ ", isactive=" + isactive + ", title=" + title + ", firstname=" + firstname + ", middlename="
				+ middlename + ", lastname=" + lastname + "]";
	}

	public String getEmailaddress() {
		return emailaddress.trim().toLowerCase();
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress.trim().toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public int getIsregisteredbymobile() {
		return isregisteredbymobile;
	}

	public void setIsregisteredbymobile(int isregisteredbymobile) {
		this.isregisteredbymobile = isregisteredbymobile;
	}

	public String getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public int getIsactive() {
		return isactive;
	}

	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

}