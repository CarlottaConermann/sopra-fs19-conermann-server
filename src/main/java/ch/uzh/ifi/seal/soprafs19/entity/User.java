package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class User implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private LocalDate birthday;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String token;

	@Column(nullable = false)
	private UserStatus status;

	@Column(nullable = false)
	private LocalDate creationDate;

	public void setCreationDate( ){
		this.creationDate = LocalDate.now();
	}

	public LocalDate getCreationDate(){
		return this.creationDate;
	}

	public void setBirthday(LocalDate date){
		this.birthday = date;
	}

	public void setBirthday(String date){
		DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.birthday = LocalDate.parse(date, DATEFORMATTER);
		//germanFormat: "DD.MM.YYYY"
		//DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.GERMAN);
		//this.birthday = LocalDate.parse(date, germanFormatter);
	}

	public LocalDate getBirthday(){
		return this.birthday;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) { this.password = password; }

	public String getPassword() { return this.password; }

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public UserStatus getStatus() {
		return status;
	}

	public User hidePassword(){
		this.password = "";
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof User)) {
			return false;
		}
		User user = (User) o;
		return this.getId().equals(user.getId());
	}
}

/**package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Entity
public class User implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false) 
	private String name;
	
	@Column(nullable = false, unique = true) 
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private LocalDate birthday;
	
	@Column(nullable = false, unique = true) 
	private String token;

	@Column(nullable = false)
	private UserStatus status;

	@Column(nullable = false)
	private LocalDate creationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() { return this.password; }

	public void setPassword(String password) {this.password = password;}

	public User hidePassword(){this.password = ""; return this;}

	public LocalDate getBirthday(){ return this.birthday; }

	public void setBirthday(String date){
		//germanFormat: "DD.MM.YYYY"
		DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedDate(
				FormatStyle.MEDIUM).withLocale(Locale.GERMAN);
		this.birthday = LocalDate.parse(date, germanFormatter);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public void setCreationDate( ){
		this.creationDate = LocalDate.now();
	}

	public LocalDate getcreationDate(){
		return this.creationDate;
	}


	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof User)) {
			return false;
		}
		User user = (User) o;
		return this.getId().equals(user.getId());
	}
}
**/