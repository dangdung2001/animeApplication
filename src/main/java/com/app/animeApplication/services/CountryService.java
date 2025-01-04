package com.app.animeApplication.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.animeApplication.entity.Country;
import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.mapper.CountryMapper;
import com.app.animeApplication.payloads.CountryDTO;
import com.app.animeApplication.reposiroty.CountryRepository;

@Service
public class CountryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private CountryMapper countryMapper;
	

	public CountryDTO getCountryById(Long id) {
		
		Country country = this.countryRepository.findById(id).orElseGet(null);
		
		CountryDTO countryDTO = this.countryMapper.toCountryDTO(country);
		
		return countryDTO;
	}

	public boolean addGenre(CountryDTO countryDTO) {
		
		Country country = this.countryMapper.toCountryEntity(countryDTO);
		
		return this.countryRepository.save(country) != null;
		
	}

	public Movies updateGenre(Long id, Movies updatedMovie) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteGenreByID(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
