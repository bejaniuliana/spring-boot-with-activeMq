package com.demo.jms.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.jms.JMSConsumer;
import com.demo.jms.JMSPublisher;

/**
 * JMSClient implementation
 * 
 * @author iuliana.bejan
 *
 */
@Component
public class JmsClientImpl implements JMSClient {

	@Autowired
	JMSPublisher jmsPublisher;

	@Autowired
	JMSConsumer jmsConsumer;

	@Override
	public void send(String message) {
		jmsPublisher.send(message);
	}

	@Override
	public String receive() {
		return jmsConsumer.receive();
	}

	/**
	 * Gets the jmsPublisher object
	 * 
	 * @return {@link JMSPublisher}
	 */
	public JMSPublisher getJmsPublisher() {
		return jmsPublisher;
	}

	/**
	 * Sets the jmsPublisher object
	 * @param jmsPublisher
	 */
	public void setJmsPublisher(JMSPublisher jmsPublisher) {
		this.jmsPublisher = jmsPublisher;
	}

	/**
	 * Gets the jmsConsumer object
	 * 
	 * @return {@link JMSConsumer}
	 */
	public JMSConsumer getJmsConsumer() {
		return jmsConsumer;
	}

	/**
	 * Sets the jmsConsumer object
	 * 
	 * @param jmsConsumer
	 *            {@link JMSConsumer}
	 */
	public void setJmsConsumer(JMSConsumer jmsConsumer) {
		this.jmsConsumer = jmsConsumer;
	}
}
