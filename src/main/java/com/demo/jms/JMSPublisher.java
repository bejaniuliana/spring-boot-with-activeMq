package com.demo.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * The JMSPublisher class, responsible with sending the messages
 * 
 * @author iuliana.bejan
 *
 */
@Component
public class JMSPublisher {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${spring.jms.template.default-destination}")
	String destinationQueue;

	/**
	 * Sends the given messages to the specified destinationQueue
	 * 
	 * @param message
	 */
	public void send(String message) {
		jmsTemplate.convertAndSend(destinationQueue, message);
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
	 * 
	 * @param jmsTemplate
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
	 * Gets the destination
	 * 
	 * @return {@link String}
	 */
	public String getDestinationQueue() {
		return destinationQueue;
	}

	/**
	 * Sets the destination
	 * 
	 * @param destination
	 */
	public void setDestinationQueue(String destinationQueue) {
		this.destinationQueue = destinationQueue;
	}

	/**
	 * Method used to override default configuration of the connection factory
	 * 
	 * @param activeMQConnectionFactory
	 *            {@link ActiveMQConnectionFactory}
	 */
	public void setConfiguration(
			ActiveMQConnectionFactory activeMQConnectionFactory) {
		this.jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
	}
}
