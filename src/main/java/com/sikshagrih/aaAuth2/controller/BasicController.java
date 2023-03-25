package com.sikshagrih.aaAuth2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/basic")
public class BasicController {
	
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String getAllProducts() {
		return "All";
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public String getProductsById(@PathVariable(value = "id") String id) {
		return "getProductsById";
	}
	
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome to the siksha grih";
	}
}
