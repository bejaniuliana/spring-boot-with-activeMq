package com.demo.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * OrderInformation class used to wrap data from the controller from
 * 
 * @author iuliana.bejan
 *
 */
public class OrderInformation {

	MultipartFile uploadedXmlFile;

	private String brokerConnection;

	private String brokerUsername;

	private String brokerPassword;

	private String brokerDestination;

	// default queue is selected
	private boolean queueSelected = true;

	private boolean topicSelected = false;

	public String getBrokerConnection() {
		return brokerConnection;
	}

	public MultipartFile getUploadedXmlFile() {
		return uploadedXmlFile;
	}

	public void setUploadedXmlFile(MultipartFile uploadedXmlFile) {
		this.uploadedXmlFile = uploadedXmlFile;
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

	public void setBrokerConnection(String brokerConnection) {
		this.brokerConnection = brokerConnection;
	}

	@Override
	public String toString() {
		return String
				.format("OrderInformation{brokerConnection=%s, brokerUsername=%s, brokerPassword=%s, brokerDestination=%s, queueSelected=%b, topicSelected=%b, uploadedXmlFile=%s}",
						this.brokerConnection, this.brokerUsername,
						this.brokerPassword, this.brokerDestination,
						this.queueSelected, this.topicSelected,
						this.uploadedXmlFile.getOriginalFilename());
	}

}
