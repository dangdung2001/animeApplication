package com.app.animeApplication.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.animeApplication.entity.Country;
import com.app.animeApplication.payloads.CountryDTO;
import com.app.animeApplication.payloads.JwtResponse;
import com.app.animeApplication.payloads.SimpleMovieDTO;

@Component
public class CountryMapper {

	@Autowired
	private MovieMapper movieMapper;

	public Country toCountryEntity(CountryDTO countryDTO) {

		Country country = new Country();

		country.setCountryName(countryDTO.getCountryName());

		return country;
	}

	public CountryDTO toCountryDTO(Country country) {

		CountryDTO countryDTO = new CountryDTO();

		List<SimpleMovieDTO> simpleMovieDTOs = country.getMovies().stream()
				.map(movie -> this.movieMapper.toMovieSimpleDTO(movie)).collect(Collectors.toList());

		countryDTO.setCountryId(country.getCountryId());
		countryDTO.setCountryName(country.getCountryName());
		countryDTO.setSimpleMovieDTOs(simpleMovieDTOs);
		
		return countryDTO;
	}
}
