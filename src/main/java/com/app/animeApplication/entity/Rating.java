package com.app.animeApplication.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


/**
 * The persistent class for the ratings database table.
 * 
 */
@Entity
@Table(name="ratings")
@Data
public class Rating implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RATING_ID")
	private int ratingId;

	@Column(name="RATING_VALUE")
	private float ratingValue;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	//bi-directional many-to-one association to Movy
	@ManyToOne
	@JoinColumn(name="MOVIEID")
	private Movies movies;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="USERID")
	private User user;

	

}