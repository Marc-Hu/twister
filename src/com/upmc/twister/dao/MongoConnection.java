package com.upmc.twister.dao;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.apache.log4j.Logger;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {

	private static Logger logger = Logger.getLogger(MongoConnection.class);

	private static MongoClient mongoClient = null;
	static {
		getInstance();
	}

	private MongoConnection() {
		logger.debug("Bootstraping");
		if (mongoClient == null) {
			logger.debug("Starting Mongo");
			CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
					fromProviders(PojoCodecProvider.builder().automatic(true).build()));
			mongoClient = new MongoClient("localhost",
					MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

			logger.info("About to connect to MongoDB @ loalhost");

		}

	}
	

	public static void close() {
		logger.info("Closing MongoDB connection");
		if (mongoClient != null) {
			try {
				mongoClient.close();
				logger.debug("Nulling the connection dependency objects");
				mongoClient = null;

			} catch (Exception e) {
				logger.error(
						String.format("An error occurred when closing the MongoDB connection\n%s", e.getMessage()));
			}
		} else {
			logger.warn("mongo object was null, wouldn't close connection");
		}
	}

	public static MongoDatabase getDatabase(String name) {
		return mongoClient.getDatabase(name);
	}
	public static MongoClient getInstance() {
		if (mongoClient == null)
			new MongoConnection();

		return mongoClient;
	}
}