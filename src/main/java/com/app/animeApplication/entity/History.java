package com.app.animeApplication.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;


/**
 * The persistent class for the history database table.
 * 
 */
@Entity
@Data
public class History implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HISTORY_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int historyId;

	@NotNull
	private float progress;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TIME_UPDATE_AT")
	@NotNull
	private Date timeUpdateAt;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="USERID")
	private User user;

	//bi-directional many-to-one association to Movy
	@ManyToOne
	@JoinColumn(name="MOVIEID")
	private Movies movies;

	

}