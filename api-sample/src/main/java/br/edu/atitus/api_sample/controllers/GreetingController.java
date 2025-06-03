package br.edu.atitus.api_sample.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mundo")

public class GreetingController {
	
	@GetMapping
	public String getGreeting() {
		return "Hello world!";
	};
}
