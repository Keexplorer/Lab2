package tr.edu.ogu.lab2.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.lab2.model.CountryDTO;
import tr.edu.ogu.lab2.service.CountryService;

@RestController
@RequestMapping("/v1/countries")
@RequiredArgsConstructor
public class CountryController {

	private final CountryService countryService;

	@GetMapping("/{id}")
	public ResponseEntity<Page<CountryDTO>> getCountryList(@RequestParam String name) {
		Page<CountryDTO> countryList = countryService.searchCountries(name).map(p -> CountryDTO.builder()
				.id(p.getId())
				.code(p.getCode())
				.name(p.getName())
				.build());
		return ResponseEntity.ok(countryList);
	}
	
	@PostMapping("sync")
	public ResponseEntity<List<CountryDTO>> syncCountries() {
		List<CountryDTO> list = countryService.syncCountries();
		return ResponseEntity.ok(list);
	}

}
