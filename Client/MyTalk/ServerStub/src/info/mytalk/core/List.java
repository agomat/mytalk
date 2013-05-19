package info.mytalk.core;

import java.util.ArrayList;

public class List {
	
	public static class ListSideload {
		private Integer id;
		private String name;
		private ArrayList<Integer> list = new ArrayList<Integer>();
		
		public ListSideload(Integer _id, String _name) {
			id = _id;
			name = _name;
		}
		
		public void pushContactId(Integer id) {
			list.add( id );
		}
	}
	
	
	private static Integer counter = 0;
	//////////////////////////////////
	private Integer id;
	
	private String name;
	private ArrayList<User> list = new ArrayList<User>();
	
	public List(String name) {
		this.name = name;
		this.setId(++counter);
	}
	
	public void addUserToList( User u ) {
		list.add(u);
	}
	
	public void removeUserFromList( User u ) {
		list.remove(u);
	}
	
	// getters
	
	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	private void setId(Integer id) {
		this.id = id;
	}
	
	public ArrayList<User> getList() {
		return list;
	}
}
