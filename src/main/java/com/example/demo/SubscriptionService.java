package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import org.springframework.stereotype.Service;

@Service
public class SubscriptionService implements ISubscriptionService {
	
	@Autowired
	JmsTemplate jsmTemplate;
	
	private static final String STANDARD_QUEUE = "standard_queue";
	private static final String PREMIUM_QUEUE = "premium_queue";
	private static final String UPDATE_QUEUE = "update_queue";

	@Override
	public void newSubscription(Subscription sub, HttpSession httpSession) throws Exception {
		if (sub.getPrice()<=0.0) {
			throw new Exception("Price should be greater than 0");
		} else {
			System.out.println("Getting you subscribed ...");
			
			if (sub.getPrice()<=500) {
				jsmTemplate.convertAndSend(STANDARD_QUEUE, sub);
			} else {
				jsmTemplate.convertAndSend(PREMIUM_QUEUE, sub);
			}
			@SuppressWarnings("unchecked")
			List<Subscription> subscriptions = (List<Subscription>) httpSession.getAttribute("CURRENT_SUBSCRIPTION");
			if (subscriptions == null) {
				subscriptions =  new ArrayList<Subscription>();
				httpSession.setAttribute("CURRENT_SUBSCRIPTION", subscriptions);
			} else {
				subscriptions.add(sub);
				httpSession.setAttribute("CURRENT_SUBSCRIPTION", subscriptions);
			}
		}
	}

	@Override
	public String getSubscriptions(HttpSession httpSession) {
		if (httpSession.getAttribute("CURRENT_SUBSCRIPTION") != null) {
			@SuppressWarnings("unchecked")
			List<Subscription> subscriptions = (List<Subscription>) httpSession.getAttribute("CURRENT_SUBSCRIPTION");
			String subscriptionText = "You have these subscriptions";
			for (Subscription subscription : subscriptions) {
				subscriptionText += " ";
				subscriptionText += subscription.getSubscriptionFor();
				subscriptionText += " ";
				subscriptionText += subscription.getPrice();
			}
			return subscriptionText;
		}
		return "No Subscriptions found";
	}

	@Override
	public String updateSubscription(Subscription sub, HttpSession httpSession) {
		if (httpSession.getAttribute("CURRENT_SUBSCRIPTION") != null) {
			@SuppressWarnings("unchecked")
			List<Subscription> subscriptions = (List<Subscription>) httpSession.getAttribute("CURRENT_SUBSCRIPTION");
			boolean subExist = false;
			int index = 0;
			for (Subscription subscription : subscriptions) {
				if (subscription.getSubscriptionFor().equalsIgnoreCase(sub.getSubscriptionFor())) {
					subscriptions.remove(index);
					subscription.setPrice(sub.getPrice());
					subscriptions.add(subscription);
					subExist = true;
				}
				index++;
			}
			if (!subExist) {
				return "No subscription found for "+ sub.getSubscriptionFor();
			}
			jsmTemplate.convertAndSend(UPDATE_QUEUE,sub);
			return "Updated "+ sub.getSubscriptionFor();
		}
		return "No Subscription found";
	}

}
