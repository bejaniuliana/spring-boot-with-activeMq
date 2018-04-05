package com.demo.jms.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class JmsUserDefinedConfig {

	/**
	 * Method used to get connectionFactory by passing userDefined configuration
	 * details
	 * 
	 * @param brokerConnection
	 *            the user defined brokerConnection value
	 * @param brokerUsername
	 *            the user defined brokerUsername
	 * @param brokerPassword
	 *            the user defined brokerPassword
	 * @return {@link ActiveMQConnectionFactory}
	 */
	public static ActiveMQConnectionFactory connectionFactory(
			String brokerConnection, String brokerUsername,
			String brokerPassword) {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerConnection);
		connectionFactory.setPassword(brokerUsername);
		connectionFactory.setUserName(brokerPassword);
		return connectionFactory;
	}

}
