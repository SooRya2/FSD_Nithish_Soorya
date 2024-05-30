package com.page.controller;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/land")
public class PageController {
	

	@GetMapping("/page")
	public String getPage()
	{
		System.out.println("Page Controller");
		return "Secured Page";
	}

}
