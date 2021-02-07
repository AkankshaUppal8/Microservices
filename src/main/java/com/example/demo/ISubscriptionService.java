package com.example.demo;

import javax.servlet.http.HttpSession;


public interface ISubscriptionService {
	
	void newSubscription(Subscription sub, HttpSession httpSession) throws Exception;
	
	String getSubscriptions(HttpSession httpSession);
	
	String updateSubscription(Subscription sub, HttpSession httpSession);
}
