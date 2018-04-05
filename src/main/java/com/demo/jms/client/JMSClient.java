package com.demo.jms.client;

/**
 * JMSClient interface
 * 
 * @author iuliana.bejan
 *
 */
public interface JMSClient {

	/**
	 * Method used to send the message in JSON format
	 * 
	 * @param message
	 *            {@link String}
	 */
	public void send(String message);

	/**
	 * Method used to receive the message in JSON format
	 * 
	 * @return
	 */
	public String receive();

}
