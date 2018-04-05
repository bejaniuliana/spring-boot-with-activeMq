package com.demo.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * The consumer.receiver class, also known as the message driven POJO
 * 
 * @author iuliana.bejan
 *
 */
@Component
public class JMSConsumer {

	@Autowired
	JmsTemplate jmsTemplate;

	@Value("${spring.jms.template.default-destination}")
	String destinationQueue;

	/**
	 * Method used to receive the message
	 * 
	 * @return {@link String} 
	 * 			a string representation of the messaged received
	 */
	public String receive() {
		return (String) jmsTemplate.receiveAndConvert(destinationQueue);
	}

	/**
	 * Gets the jmsTemplate
	 * 
	 * @return {@link JmsTemplate}
	 */
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	/**
	 * Sets the jmsTemplate
	 * @param jmsTemplate 
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
	 * Gets the destination
	 * @return {@link String}
	 * 			String representation 
	 */
	public String getDestinationQueue() {
		return destinationQueue;
	}

	/**
	 * Sets the destination
	 * @param destinationQueue
	 */
	public void setDestinationQueue(String destinationQueue) {
		this.destinationQueue = destinationQueue;
	}
}
