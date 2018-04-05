package com.demo.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo.jms.client.JMSClient;
import com.demo.jms.client.JmsClientImpl;
import com.demo.jms.config.JmsUserDefinedConfig;
import com.demo.model.DefaultPropertiesConfiguration;
import com.demo.model.OrderInformation;
import com.demo.util.WebOrdersConstants;

/**
 * Controller class
 * 
 * @author iuliana.bejan
 *
 */
@Controller
@EnableAutoConfiguration
public class WebOrdersController {

	private static final Logger LOGGER = Logger
			.getLogger(WebOrdersController.class);

	@Autowired
	JMSClient jmsClient;

	@Autowired
	private DefaultPropertiesConfiguration defaultPropertiesConfiguration;

	@RequestMapping(value = { "/", "/upload", "/home" }, method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	@ResponseBody
	public ModelAndView upload() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(WebOrdersConstants.UPLOAD);

		OrderInformation orderInfo = new OrderInformation();
		orderInfo.setBrokerConnection(defaultPropertiesConfiguration
				.getBrokerConnection());
		orderInfo.setBrokerUsername(defaultPropertiesConfiguration
				.getBrokerUsername());
		orderInfo.setBrokerPassword(defaultPropertiesConfiguration
				.getBrokerPassword());
		orderInfo.setBrokerDestination(defaultPropertiesConfiguration
				.getBrokerDestination());
		orderInfo.setQueueSelected(defaultPropertiesConfiguration
				.isQueueSelected());
		orderInfo.setTopicSelected(defaultPropertiesConfiguration
				.isTopicSelected());

		modelAndView.addObject("orderInformation", orderInfo);

		LOGGER.debug("TestOrdersController: uploadData using: "
				+ defaultPropertiesConfiguration);

		return modelAndView;
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String submitData(@ModelAttribute OrderInformation orderInformation)
			throws IOException {

		String messageAsJson = "";

		if (orderInformation.getUploadedXmlFile() != null) {
			String contentOfFile = new String(orderInformation
					.getUploadedXmlFile().getBytes(), "UTF-8");

			try {
				JSONObject xmlJSONObj = XML.toJSONObject(contentOfFile);

				messageAsJson = xmlJSONObj.toString();

				// before sending the message check if the user provided/entered
				// specific configuration for brokerUsername, brokerPass, etc
				updateJMSConfig(orderInformation);

				jmsClient.send(messageAsJson);

			} catch (JSONException e) {
				e.printStackTrace();
				messageAsJson = e.getMessage();
			}
		} else {
			LOGGER.error("TestOrdersController error: submit data: "
					+ "Please select a file");
		}

		LOGGER.debug(String.format("TestOrdersController: submit data: %s ",
				orderInformation));

		return WebOrdersConstants.SENT_SUCCESSFUL
				.concat(orderInformation.getBrokerDestination())
				.concat(WebOrdersConstants.NEW_LINE).concat(messageAsJson);
	}

	@RequestMapping(value = "/consumeMessage", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ModelAndView consumeMessage(
			@RequestParam(value = "destinationQueue", required = false, defaultValue = "") String destinationQueue) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(WebOrdersConstants.UPLOAD_RESULT);

		overrideDestinationQueue(destinationQueue);

		String messageConsumed = jmsClient.receive();

		LOGGER.debug(String
				.format("TestOrdersController: consumed message: %s from destination: %s ",
						messageConsumed, destinationQueue));

		modelAndView.addObject("messageConsumed", messageConsumed);

		return modelAndView;
	}

	/**
	 * Method used to update the destinationQueue value in case the user
	 * provided a value, otherwise the default value will be used
	 * 
	 * @param destinationQueue
	 */
	public void overrideDestinationQueue(String destinationQueue) {

		if (!WebOrdersConstants.EMPTY_string.equals(destinationQueue)) {
			// the user entered different information so we have to override the
			// default properties
			((JmsClientImpl) jmsClient).getJmsPublisher().setDestinationQueue(
					destinationQueue);
			((JmsClientImpl) jmsClient).getJmsConsumer().setDestinationQueue(
					destinationQueue);
		} else {
			// setting the default values
			((JmsClientImpl) jmsClient).getJmsPublisher().setDestinationQueue(
					defaultPropertiesConfiguration.getBrokerDestination());
			((JmsClientImpl) jmsClient).getJmsConsumer().setDestinationQueue(
					defaultPropertiesConfiguration.getBrokerDestination());
		}

	}

	/**
	 * Method used to update the jmsConfiguration in case the user provided
	 * values in the form or use the default values if the user didn't provide a
	 * value
	 * 
	 * @param orderInformation
	 *            {@link OrderInformation}
	 */
	public void updateJMSConfig(OrderInformation orderInformation) {
		String brokerConnection = (orderInformation.getBrokerConnection() != null & !WebOrdersConstants.EMPTY_string
				.equals(orderInformation.getBrokerConnection())) ? orderInformation
				.getBrokerConnection() : defaultPropertiesConfiguration
				.getBrokerConnection();

		String brokerUsername = (orderInformation.getBrokerUsername() != null & !WebOrdersConstants.EMPTY_string
				.equals(orderInformation.getBrokerUsername())) ? orderInformation
				.getBrokerUsername() : defaultPropertiesConfiguration
				.getBrokerUsername();

		String brokerPassword = (orderInformation.getBrokerPassword() != null & !WebOrdersConstants.EMPTY_string
				.equals(orderInformation.getBrokerPassword())) ? orderInformation
				.getBrokerPassword() : defaultPropertiesConfiguration
				.getBrokerPassword();

		String brokerDestination = (orderInformation.getBrokerDestination() != null & !WebOrdersConstants.EMPTY_string
				.equals(orderInformation.getBrokerDestination())) ? orderInformation
				.getBrokerDestination() : defaultPropertiesConfiguration
				.getBrokerDestination();

		// in case the broker details entered by the user are empty we need to
		// set them back to the default values in the orderInfo object
		orderInformation.setBrokerConnection(brokerConnection);
		orderInformation.setBrokerUsername(brokerUsername);
		orderInformation.setBrokerPassword(brokerPassword);
		orderInformation.setBrokerDestination(brokerDestination);

		overrideDestinationQueue(brokerDestination);

		((JmsClientImpl) jmsClient).getJmsPublisher().setConfiguration(
				JmsUserDefinedConfig.connectionFactory(brokerConnection,
						brokerUsername, brokerPassword));
	}

	public DefaultPropertiesConfiguration getDefaultPropertiesConfiguration() {
		return defaultPropertiesConfiguration;
	}

	public void setDefaultPropertiesConfiguration(
			DefaultPropertiesConfiguration defaultPropertiesConfiguration) {
		this.defaultPropertiesConfiguration = defaultPropertiesConfiguration;
	}

	public JMSClient getJmsClient() {
		return jmsClient;
	}

	public void setJmsClient(JMSClient jmsClient) {
		this.jmsClient = jmsClient;
	}

}
