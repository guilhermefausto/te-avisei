package br.com.guilhermefausto.teavisei.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<?> home() {
		return ResponseEntity.ok("Teste");
	}
}
