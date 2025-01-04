package com.app.animeApplication.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.animeApplication.entity.Actor;
import com.app.animeApplication.entity.Country;
import com.app.animeApplication.entity.Episode;
import com.app.animeApplication.entity.Genre;
import com.app.animeApplication.entity.MovieType;
import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.entity.Studio;
import com.app.animeApplication.entity.Subtitle;
import com.app.animeApplication.payloads.ActorDTO;
import com.app.animeApplication.payloads.CommentDTO;
import com.app.animeApplication.payloads.CountryDTO;
import com.app.animeApplication.payloads.EpisodeDTO;
import com.app.animeApplication.payloads.GenreDTO;
import com.app.animeApplication.payloads.MovieDTO;
import com.app.animeApplication.payloads.MovieTypeDTO;
import com.app.animeApplication.payloads.SimpleMovieDTO;
import com.app.animeApplication.payloads.StudioDTO;
import com.app.animeApplication.payloads.SubtitleDTO;

@Component
public class MovieMapper {

	@Autowired
	private UserMapper userMapper;

	public MovieDTO toMovieDetailDTO(Movies movie) {
		if (movie == null) {
			return null;
		}

		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setMovieid(movie.getMovieid());
		movieDTO.setAvgrating(movie.getAvgrating());
		movieDTO.setDescription(movie.getDescription());
		movieDTO.setDuration(movie.getDuration());
		movieDTO.setFollowing(movie.getFollowing());
		movieDTO.setImage(movie.getImage());
		movieDTO.setName(movie.getName());
		movieDTO.setQuality(movie.getQuality());
		movieDTO.setReleasedate(movie.getReleasedate());
		movieDTO.setRunningtime(movie.getRunningtime());
		movieDTO.setShowtimes(movie.getShowtimes());
		movieDTO.setState(movie.getState());
		movieDTO.setWebsite(movie.getWebsite());

		movieDTO.setCommentDTOs(movie.getComments().stream()
				.map(comment -> new CommentDTO(comment.getCommentId(), comment.getCommentContent(),
						toMovieSimpleDTO(comment.getMovies()), userMapper.toUserDTO(comment.getUser())))
				.collect(Collectors.toList()));
		
		movieDTO.setEpisodeDTOs(movie.getEpisodes().stream()
				.map(Episode -> new EpisodeDTO(Episode.getEpisodeId(), Episode.getDescriptionEpisode(),
						Episode.getDurationEpisode(), Episode.getEpisodeNumber(), Episode.getReleaseDateEpisode(),
						Episode.getTitleEpisode(), Episode.getUrlEpisode(), null))
				.collect(Collectors.toList()));
		
		movieDTO.setActorDTOs(movie.getActors().stream()
				.map(Actor -> new ActorDTO(Actor.getActorsId(), Actor.getActorsName() , new ArrayList<SimpleMovieDTO>()))
				.collect(Collectors.toList()));

		
		movieDTO.setCountrieDTOs(movie.getCountries().stream()
				.map(Country -> new CountryDTO(Country.getCountryId(), Country.getCountryName(),
						new ArrayList<SimpleMovieDTO>()))
				.collect(Collectors.toList()));
		
		movieDTO.setGenreDTOs(movie.getGenres().stream()
				.map(Genre -> new GenreDTO(Genre.getGenreId(), Genre.getGenreName(),
						new ArrayList<SimpleMovieDTO>()))
				.collect(Collectors.toList()));
		
		movieDTO.setStudioDTOs(movie.getStudios().stream()
				.map(Studio -> new StudioDTO(Studio.getStudioId(), Studio.getStudioName(),
						new ArrayList<SimpleMovieDTO>()))
				.collect(Collectors.toList()));
		
		movieDTO.setSubtitleDTOs(movie.getSubtitles().stream()
				.map(Subtitle -> new SubtitleDTO(Subtitle.getSubtitlesId(), Subtitle.getSubtitlesName(),
						new ArrayList<SimpleMovieDTO>()))
				.collect(Collectors.toList()));
		
		MovieTypeDTO movieTypeDTO = new MovieTypeDTO();
		movieTypeDTO.setTypeId(movie.getMovieType().getTypeId());
		movieTypeDTO.setTypeName(movie.getMovieType().getTypeName());
		movieDTO.setMovieType(movieTypeDTO);
		
		return movieDTO;
	}

	public SimpleMovieDTO toMovieSimpleDTO(Movies movie) {

		SimpleMovieDTO simpleMovieDTO = new SimpleMovieDTO();
		simpleMovieDTO.setMovieId(movie.getMovieid());
		simpleMovieDTO.setMovieTitle(movie.getName());
		simpleMovieDTO.setMoviePoster(movie.getImage());
		simpleMovieDTO.setAvgrating(movie.getAvgrating());
		simpleMovieDTO.setFollowing(movie.getFollowing());
		simpleMovieDTO.setDescription(movie.getDescription());

		return simpleMovieDTO;
	}
	
	public Movies ToMovieEntity(SimpleMovieDTO simpleMovieDTO) {

		Movies movie = new Movies();
		
		movie.setMovieid(simpleMovieDTO.getMovieId());
		movie.setName(simpleMovieDTO.getMovieTitle());
		movie.setImage(simpleMovieDTO.getMoviePoster());
		movie.setDescription(simpleMovieDTO.getDescription());
		movie.setFollowing(simpleMovieDTO.getFollowing());
		movie.setAvgrating(simpleMovieDTO.getAvgrating());
		
		return movie;
	}

	public Movies  toMovie(MovieDTO movieDTO) {

		if (movieDTO == null) {
            return null;
        }
		
		 Movies movie = new Movies();
	        movie.setAvgrating(movieDTO.getAvgrating());
	        movie.setDescription(movieDTO.getDescription());
	        movie.setDuration(movieDTO.getDuration());
	        movie.setFollowing(movieDTO.getFollowing());
	        movie.setImage(movieDTO.getImage());
	        movie.setName(movieDTO.getName());
	        movie.setQuality(movieDTO.getQuality());
	        movie.setReleasedate(movieDTO.getReleasedate());
	        movie.setRunningtime(movieDTO.getRunningtime());
	        movie.setShowtimes(movieDTO.getShowtimes());
	        movie.setState(movieDTO.getState());
	        movie.setWebsite(movieDTO.getWebsite());
	        
	        movie.setEpisodes(toEpisodeEntities(movie,movieDTO.getEpisodeDTOs()));
	        movie.setActors(toActorEntities(movieDTO.getActorDTOs()));
	        movie.setCountries(toCountryEntities(movieDTO.getCountrieDTOs()));
	        movie.setGenres(toGenreEntities(movieDTO.getGenreDTOs()));
	        movie.setStudios(toStudioEntities(movieDTO.getStudioDTOs()));
	        movie.setSubtitles(toSubtitleEntities(movieDTO.getSubtitleDTOs()));

	        if (movieDTO.getMovieType() != null) {
	            movie.setMovieType(toMovieTypeEntity(movieDTO.getMovieType()));
	        }

	        return movie;
	}
	
	
	

    private static List<Episode> toEpisodeEntities(Movies movie, List<EpisodeDTO> episodeDTOs) {
        if (episodeDTOs == null) {
            return null;
        }
        return episodeDTOs.stream()
                .map(episodeDTO -> new Episode(null, episodeDTO.getDescriptionEpisode()
                		, episodeDTO.getDurationEpisode(), episodeDTO.getEpisodeNumber()
                		, episodeDTO.getReleaseDateEpisode(), episodeDTO.getTitleEpisode(), episodeDTO.getUrlEpisode()
                		, movie
                		)) 
                .collect(Collectors.toList());
    }

    private static List<Actor> toActorEntities(List<ActorDTO> actorDTOs) {
        if (actorDTOs == null) {
            return null;
        }
        return actorDTOs.stream()
                .map(actorDTO -> new Actor(actorDTO.getActorsId(), actorDTO.getActorsName() , null)) 
                .collect(Collectors.toList());
    }

    
    private static List<Country> toCountryEntities(List<CountryDTO> countryDTOs) {
        if (countryDTOs == null) {
            return null;
        }
        return countryDTOs.stream()
                .map(countryDTO -> new Country(countryDTO.getCountryId(), countryDTO.getCountryName(), null))
                .collect(Collectors.toList());
    }

    private static List<Genre> toGenreEntities(List<GenreDTO> genreDTOs) {
        if (genreDTOs == null) {
            return null;
        }
        return genreDTOs.stream()
                .map(genreDTO -> new Genre(genreDTO.getGenreId(), genreDTO.getGenreName() , null)) 
                .collect(Collectors.toList());
    }

    private static List<Studio> toStudioEntities(List<StudioDTO> studioDTOs) {
        if (studioDTOs == null) {
            return null;
        }
        return studioDTOs.stream()
                .map(studioDTO -> new Studio(studioDTO.getStudioId(), studioDTO.getStudioName(), null)) // map StudioDTO to Studio
                .collect(Collectors.toList());
    }

    private static List<Subtitle> toSubtitleEntities(List<SubtitleDTO> subtitleDTOs) {
        if (subtitleDTOs == null) {
            return null;
        }
        return subtitleDTOs.stream()
                .map(subtitleDTO -> new Subtitle(subtitleDTO.getSubtitlesId(), subtitleDTO.getSubtitlesName(), null)) // map SubtitleDTO to Subtitle
                .collect(Collectors.toList());
    }

    private static MovieType toMovieTypeEntity(MovieTypeDTO movieTypeDTO) {
        if (movieTypeDTO == null) {
            return null;
        }
        return new MovieType(movieTypeDTO.getTypeId(), movieTypeDTO.getTypeName(), null); // map MovieTypeDTO to MovieType
    }

}
