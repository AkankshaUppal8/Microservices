package com.example.demo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sub")
public class SubscriptionController {
	
	@Autowired
	ISubscriptionService iSubscriptionService;
	
	@PostMapping
	public String newSubscription(@RequestBody Subscription subscription, HttpServletRequest request) {
		System.out.println(subscription);
		
		try {
			iSubscriptionService.newSubscription(subscription,request.getSession());
		} catch (Exception e){
			System.out.println(e.getMessage());
			return e.getMessage();
		}
		return "Success";
	}
	
	@GetMapping
	public String getSubscription(HttpServletRequest request) {
		return iSubscriptionService.getSubscriptions(request.getSession());
	}
	
	@PutMapping
	public String updateSubscription(@RequestBody Subscription subscription, HttpServletRequest request) {
		return iSubscriptionService.updateSubscription(subscription, request.getSession());
	}
}
