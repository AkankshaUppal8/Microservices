package com.ram.receiver;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.example.demo.Subscription;


@Component
public class MessageReceiver
{

	private static final String STANDARD_QUEUE = "standard_queue";
	private static final String PREMIUM_QUEUE = "premium_queue";
	
	@JmsListener(destination = STANDARD_QUEUE)
	public void receiveStandardMessage(Subscription sub)
	{
		System.out.println("Recieved request " + sub);
	}
	
	@JmsListener(destination = PREMIUM_QUEUE)
	public void receivePremiumMessage(Subscription sub)
	{
		System.out.println("Received request" + sub);
	}
}
