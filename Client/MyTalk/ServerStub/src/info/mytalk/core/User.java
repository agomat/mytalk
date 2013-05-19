package info.mytalk.core;

import java.util.ArrayList;
import java.security.*;

public class User {
	
	public static class Contact {
		private Integer id;
		private String username = null;
		private String name = null;
		private String surname = null;
		private String md5 = null;
		private String ip = null;
		private Boolean online = false;
		public Contact(Integer _id, String _name, String _surname, String _md5, String _ip, Boolean _online) {
			id = _id;
			name = _name;
			surname = _surname;
			md5 = _md5;
			ip = _ip;
			online = _online;
		}
	}
	
	private static Integer counter = 0;
	//////////////////////////////////
	private Integer id;
	
	private String username = null;
	private String password = null;
	private String name = null;
	private String surname = null;
	private String email = null;
	private String md5 = null;
	private String ip = null;
	private Boolean online = false;
	private ArrayList<List> list = new ArrayList<List>();
	private WCall wcall;
	
	// guest user
	public User(String ip) { 
		this.ip = ip;
	}
	
	// persistent user
	public User( String username, String name, String surname, String email, String password, String ip ) {
		this.setUsername(username);
		this.setPassword(password);
		this.setName(name);
		this.setSurname(surname);
		this.setEmail(email);
		this.setId(++counter);
		this.ip = ip;
		//Md5
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			md.update(email.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(Integer.toHexString((int) (b & 0xff)));
			}
			this.setMd5( sb.toString() );
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}
	
	public Boolean addUserToList(String listName, User user) {
		for (List l : list) {	// cerco la lista con quel nome
			if (l.getName().equals(listName)) {
				l.addUserToList(user);
				return true;
			}
		}
		return false;
	}
	
	public Boolean removeUserFromList(String listName, User user) {
		for (List l : list) {	// cerco la lista con quel nome
			if (l.getName().equals(listName)) {
				l.removeUserFromList(user);
				return true;
			}
		}
		return false;
	}
	
	public Boolean isGuest() {
		if (username == null)
			return true;
		return false;
	}
	
	public void newList(String name) {
		// non controllo se esiste una con lo stesso nome
		List l = new List(name);
		list.add(l);
	}
	
	public void delList(String name) {
		List listToRemove = null;
		for (List l : list) {	// cerco la lista con quel nome
			if (l.getName().equals(name)) {
				listToRemove = l;
			}
		}
		list.remove(listToRemove);
	}
	
	// getters e getters
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getIP() {
		return ip;
	}
	
	public void setIP(String ip) {
		this.ip = ip;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
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

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public WCall getWcall() {
		return wcall;
	}

	public void setWcall(WCall wcall) {
		this.wcall = wcall;
	}
	
	public String toString() {
		String output = "";
		output += "ID: "+id+" username: "+username+" name:"+name+" surname:"+surname+" email:"+email+" ip:"+ip+" online:"+online+"\n";
		return output;
	}

	public Integer getId() {
		return id;
	}

	private void setId(Integer id) {
		this.id = id;
	}
	
	public ArrayList<List> getLists() {
		return list;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
