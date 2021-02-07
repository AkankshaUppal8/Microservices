package com.example.demo;

import java.io.Serializable;

enum TYPE{
	SUBSCRIBED, CANCELLED
}

public class Subscription implements Serializable {
	
	private static final long serialVersionUID = 45789L;
	
	int id;
	String subscriptionFor;
	float price;
	TYPE type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubscriptionFor() {
		return subscriptionFor;
	}
	public void setSubscriptionFor(String subscriptionFor) {
		this.subscriptionFor = subscriptionFor;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public TYPE getState() {
		return type;
	}
	public void setState(TYPE type) {
		this.type = type;
	}	
	
}