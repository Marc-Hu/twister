package com.upmc.twister.dao;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;

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

            try {
                mongoClient = new MongoClient("localhost", 27017);
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

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

    public static DB getDatabase(String name) {
        return mongoClient.getDB(name);
    }

    public static MongoClient getInstance() {
        if (mongoClient == null)
            new MongoConnection();

        return mongoClient;
    }
}