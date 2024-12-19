package tr.edu.ogu.lab2.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.lab2.entity.Country;
import tr.edu.ogu.lab2.model.CountryDTO;
import tr.edu.ogu.lab2.repository.CountryRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryService {

	private CountryRepository countryRepository;
	
	private final RestClient restClient;

	public Page<Country> searchCountries(String searchTxt) {
		log.info("Searching countries with search text: {}", searchTxt);
		return countryRepository.findByNameStartingWith(searchTxt, Pageable.unpaged());
	}

	public List<CountryDTO> syncCountries() {
		// FIXME: https://apitest.sensiballvr.com/panel/countries?name=&page=0&size=10 adresindne ülkeleri çekip veritabanına kaydedin
		Page<Country> countries = restClient.get().uri("https://apitest.sensiballvr.com/panel/countries?name=&page=0&size=10")
				.accept(MediaType.APPLICATION_JSON).retrieve().body(new ParameterizedTypeReference<RestPage<Country>>() {
						});
		List<CountryDTO> dtolist = null;
				for (Country country : countries.getContent()) {
					Country entity= new Country();
					entity.setCode(country.getCode());
					entity.setName(country.getName());
					countryRepository.save(entity);

					CountryDTO dto = new CountryDTO();
					dto.setId(entity.getId());
					dto.setCode(entity.getCode());
					dto.setName(entity.getName());
					dtolist.add(dto);
				}


		return dtolist;
	}
	
	static class RestPage<T> extends PageImpl<T> {

		@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
		public RestPage(@JsonProperty("content") List<T> content, @JsonProperty("number") int page, @JsonProperty("size") int size,
				@JsonProperty("totalElements") long total) {
			super(content, PageRequest.of(page, content.size()), total);
		}

		public RestPage(Page<T> page) {
			super(page.getContent(), page.getPageable(), page.getTotalElements());
		}

	}

}
