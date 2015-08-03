package com.myapp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
 
@Entity
@Table(name = "posts")
public class EntPost {
	
    private Integer id;
    
	private String title;
    
    private String body;
    
    private Integer views;
    
    private EntUser createdByUser;
 
    public EntPost() {
 
    }
    
    public EntPost(Integer id, String title, String body, Integer views) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.views = views;
    }
 
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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

	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "created_by")
	public EntUser getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(EntUser createdByUser) {
		this.createdByUser = createdByUser;
	}
     
}