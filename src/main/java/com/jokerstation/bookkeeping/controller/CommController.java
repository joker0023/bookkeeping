package com.jokerstation.bookkeeping.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommController {

	@RequestMapping("/hello")
	public String hello() {
		return "success";
	}
}
