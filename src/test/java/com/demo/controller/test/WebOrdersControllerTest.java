package com.demo.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willReturn;

import javax.jms.JMSException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.controller.WebOrdersController;
import com.demo.jms.JMSConsumer;
import com.demo.jms.client.JmsClientImpl;
import com.demo.model.OrderInformation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebOrdersControllerTest {

	@Autowired
	private WebOrdersController webController;

	private JmsTemplate jmsTemplateTest;

	@Before
	public void setUp() throws JMSException {

		jmsTemplateTest = Mockito.mock(JmsTemplate.class);

		((JmsClientImpl) webController.getJmsClient()).getJmsPublisher()
				.setJmsTemplate(jmsTemplateTest);
		((JmsClientImpl) webController.getJmsClient()).getJmsConsumer()
				.setJmsTemplate(jmsTemplateTest);
	}

	@Test
	public void contexLoads() throws Exception {
		assertThat(webController).isNotNull();
	}

	@Test
	public void testDefaultConfiguration() {
		// before we update the configuration we check that the default config
		// is set
		assertThat(
				((JmsClientImpl) webController.getJmsClient())
						.getJmsPublisher().getDestinationQueue()).isEqualTo(
				"interview-1");
	}

	@Test
	public void testUpdateJMSConfig() {

		webController.updateJMSConfig(createOrderInformation());
		// after the update check the new value
		assertThat(
				((JmsClientImpl) webController.getJmsClient())
						.getJmsPublisher().getDestinationQueue()).isEqualTo(
				"test_destination");
	}

	@Test
	public void testReceive() {

		// mocking the response
		JMSConsumer jmsConsumer = Mockito.mock(JMSConsumer.class);
		((JmsClientImpl) webController.getJmsClient())
				.setJmsConsumer(jmsConsumer);

		willReturn("Test message").given(jmsConsumer).receive();

		webController.updateJMSConfig(createOrderInformation());
		webController.getJmsClient().send("Test message");

		assertThat(webController.getJmsClient().receive()).isEqualTo(
				"Test message");
	}

	private OrderInformation createOrderInformation() {
		OrderInformation orderInfo = new OrderInformation();
		orderInfo.setBrokerDestination("test_destination");
		orderInfo.setBrokerUsername("test");
		orderInfo.setBrokerPassword("test");
		return orderInfo;

	}
}
