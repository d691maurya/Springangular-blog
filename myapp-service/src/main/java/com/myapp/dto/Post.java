package com.myapp.dto;

public class Post {
	
    private Integer id;
    
	private String title;
    
    private String body;
    
    private Integer views;
    
    private Integer createdBy;
    
    private String username;
 
    public Post() {
 
    }
 
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Post(Integer id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
     
}