package org.eclipse.om2m.binding.mqtt.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public final class QueueSender {
	
	private static final Log LOGGER = LogFactory.getLog(QueueSender.class);
	private static ExecutorService threadPool;

	static {
		int queueSize = MqttConstants.MQTT_QUEUE_SENDER_SIZE <= 2 ? 2
				: MqttConstants.MQTT_QUEUE_SENDER_SIZE;
		threadPool = new ThreadPoolExecutor(2, queueSize, 1, TimeUnit.MINUTES,
				new SynchronousQueue<Runnable>());
	}

	public static void queue(MqttClient mqttClient, String topic, byte[] payload){
		LOGGER.debug("Sending MQTT message to " + mqttClient.getServerURI() + " topic: " + topic);
		threadPool.execute(new MqttSender(mqttClient, topic, payload));
	}
	
	private static class MqttSender implements Runnable {

		private MqttClient mqttClient;
		private String topic;
		private byte[] payload;

		public MqttSender(MqttClient mqttClient, String topic, byte[] payload) {
			super();
			this.mqttClient = mqttClient;
			this.topic = topic;
			this.payload = payload;
		}

		@Override
		public void run() {
			try {
				this.mqttClient.publish(topic, payload, 1, false);
			} catch (MqttException e) {
				LOGGER.warn("Error publishing on topic: " + this.topic
						+ " of broker " + this.mqttClient.getServerURI()
						+ ". Error: " + e.getMessage());
			}
		}

	}
	
	private QueueSender(){
		// Empty and private constructor to avoid class creation
	}

}
