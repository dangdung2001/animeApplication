package com.app.animeApplication.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the movies database table.
 * 
 */
@Entity
@Data
@Table(name="movies")
public class Movies implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long movieid;

	@NotNull
	private float avgrating;

	@NotNull
	private String description;

	
	private String duration;

	@NotNull
	private int following;

	@NotNull
	private String image;

	@NotNull
	private String name;

	
	private String quality;

	@Temporal(TemporalType.DATE)
	private Date releasedate;

	@NotNull
	private int runningtime;

	@NotNull
	private String showtimes;

	@NotNull
	private String state;

	@NotNull
	private String website;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="movies")
	private List<Comment> comments;

	//bi-directional many-to-one association to Episode
	@OneToMany(mappedBy="movies")
	private List<Episode> episodes;

	//bi-directional many-to-one association to History
	@OneToMany(mappedBy="movies")
	private List<History> histories;

	//bi-directional many-to-many association to Actor
	@ManyToMany(cascade = { CascadeType.MERGE})
	@JoinTable(
			name="movie_actors"
			, joinColumns={
				@JoinColumn(name="MOVIEID")
				}
			, inverseJoinColumns={
				@JoinColumn(name="ACTORSID")
				}
			)
	private List<Actor> actors;


	//bi-directional many-to-many association to Country
	@ManyToMany(cascade = { CascadeType.MERGE})
	@JoinTable(
			name="movie_country"
			, joinColumns={
				@JoinColumn(name="MOVIEID")
				}
			, inverseJoinColumns={
				@JoinColumn(name="COUNTRYID")
				}
			)
	private List<Country> countries;

	//bi-directional many-to-many association to Genre
	@ManyToMany(cascade = { CascadeType.MERGE})
	@JoinTable(
			name="movie_genre"
			, joinColumns={
				@JoinColumn(name="MOVIEID")
				}
			, inverseJoinColumns={
				@JoinColumn(name="GENREID")
				}
			)
	private List<Genre> genres;

	//bi-directional many-to-one association to MovieStudio
	@ManyToMany(cascade = { CascadeType.MERGE})
	@JoinTable(
			name="movie_studio"
			, joinColumns={
				@JoinColumn(name="MOVIEID")
				}
			, inverseJoinColumns={
				@JoinColumn(name="STUDIOID")
				}
			)
	private List<Studio> studios;

	//bi-directional many-to-many association to Subtitle
	@ManyToMany(cascade = { CascadeType.MERGE})
	@JoinTable(
			name="movie_subtitles"
			, joinColumns={
				@JoinColumn(name="MOVIEID")
				}
			, inverseJoinColumns={
				@JoinColumn(name="SUBTITLESID")
				}
			)
	private List<Subtitle> subtitles;

	@ManyToOne
	@JoinColumn(name="MOVIE_TYPE_ID")
	private MovieType movieType;

	@OneToMany(mappedBy="movies")
	private List<Rating> ratings;

	

}