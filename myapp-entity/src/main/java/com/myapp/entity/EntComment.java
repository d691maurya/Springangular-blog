package com.myapp.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 
@Entity
@Table(name = "comments")
public class EntComment {
	
    private Integer id;
    
	private String body;

    private Date createdAt;
	
    private Integer postId;
    
    private EntUser createdByUser;
 
    public EntComment() {
 
    }
 
    public EntComment(Integer id, String body, Integer postId) {
        this.id = id;
        this.body = body;
        this.postId = postId;
        
        if(null == this.id || 0 == this.id)
        	this.createdAt = new Date();
    }

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Column(name="post_id")
	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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