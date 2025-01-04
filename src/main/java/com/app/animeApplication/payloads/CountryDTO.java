package com.app.animeApplication.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {

	private Long countryId;

	private String countryName;

	private List<SimpleMovieDTO> SimpleMovieDTOs;
}
