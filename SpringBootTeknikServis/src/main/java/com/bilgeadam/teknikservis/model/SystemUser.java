package com.bilgeadam.teknikservis.model;

public class SystemUser
{
	private long id;
	private String username;
	private String email;
	private String password;

//	private List<Role> auth= new ArrayList<>();

	public Role role;

	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = role.getAuthority();

	}

	public SystemUser()
	{
		
	}
	

	public SystemUser(long id, String username, String email, String password)
	{
		
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;

	}



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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



	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", role="
				+ "]";
	}
	
	
	
	
}
