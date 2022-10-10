package ru.neoflex.VacationCalculator.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.VacationCalculator.model.ModelRequest;

import javax.validation.Valid;

@RestController
@Validated
public class MainController {

	@GetMapping("/calculacte")
	public ResponseEntity<String> mainController(@Valid ModelRequest modelRequest) {
		return ResponseEntity.ok(modelRequest.calculateVacationPayAndGetTotalString());
	}



}
