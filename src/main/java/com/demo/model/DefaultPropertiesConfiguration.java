package com.demo.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * DefaultPropertiesConfiguration class for setting default values
 * 
 * @author iuliana.bejan
 *
 */
@Component
public class DefaultPropertiesConfiguration {

	@Value("${spring.activemq.broker-url}")
	private String brokerConnection;

	@Value("${spring.activemq.user}")
	private String brokerUsername;

	@Value("${spring.activemq.password}")
	private String brokerPassword;

	@Value("${spring.jms.template.default-destination}")
	private String brokerDestination;

	@Value("${broker.queue}")
	private boolean queueSelected;

	@Value("${broker.topic}")
	private boolean topicSelected;

	public String getBrokerConnection() {
		return brokerConnection;
	}

	public void setBrokerConnection(String brokerConnection) {
		this.brokerConnection = brokerConnection;
	}

	public String getBrokerUsername() {
		return brokerUsername;
	}

	public void setBrokerUsername(String brokerUsername) {
		this.brokerUsername = brokerUsername;
	}

	public String getBrokerPassword() {
		return brokerPassword;
	}

	public void setBrokerPassword(String brokerPassword) {
		this.brokerPassword = brokerPassword;
	}

	public String getBrokerDestination() {
		return brokerDestination;
	}

	public void setBrokerDestination(String brokerDestination) {
		this.brokerDestination = brokerDestination;
	}

	public boolean isQueueSelected() {
		return queueSelected;
	}

	public void setQueueSelected(boolean queueSelected) {
		this.queueSelected = queueSelected;
	}

	public boolean isTopicSelected() {
		return topicSelected;
	}

	public void setTopicSelected(boolean topicSelected) {
		this.topicSelected = topicSelected;
	}

	@Override
	public String toString() {
		return String
				.format("DefaultPropertiesConfiguration{brokerConnection=%s, brokerUsername=%s, brokerPassword=%s, brokerDestination=%s, queueSelected=%b, topicSelected=%b}",
						this.brokerConnection, this.brokerUsername,
						this.brokerPassword, this.brokerDestination,
						this.queueSelected, this.topicSelected);
	}
}
