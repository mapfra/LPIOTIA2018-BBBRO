package org.eclipse.om2m.persistence.mongodb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.persistence.mongodb.resource.ResourceSerializerDeserializer;
import org.eclipse.om2m.persistence.mongodb.util.DBUtilManagerImpl;
import org.eclipse.om2m.persistence.service.DAOFactory;
import org.eclipse.om2m.persistence.service.DBConstants;
import org.eclipse.om2m.persistence.service.DBService;
import org.eclipse.om2m.persistence.service.DBTransaction;
import org.eclipse.om2m.persistence.service.util.DBUtilManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

public class DBServiceImpl implements DBService {

	private MongoCollection<Document> resourceCollection;
	private MongoCollection<Document> announceCollection;

	private GsonBuilder gsonBuilder;
	private Gson gson;

	private static final Log LOGGER = LogFactory.getLog(DBServiceImpl.class);

	private static final DBServiceImpl INSTANCE = new DBServiceImpl();

	public static DBServiceImpl getInstance() {
		return INSTANCE;
	}

	private DBServiceImpl() {
	}

	protected void init() {
		LOGGER.info("mongoDB URL=" + System.getProperty("org.eclipse.om2m.dbUrl", "jdbc:mongo://cloud1/default"));
		MongoClient mongoClient = new MongoClient(System.getProperty("org.eclipse.om2m.dbUrl", "jdbc:mongo://cloud1/default"));
		LOGGER.info("mongoDB client=" + mongoClient);

		MongoDatabase database = mongoClient.getDatabase("mydb_" + Constants.CSE_NAME);
		LOGGER.info("mongoDB database=" + database);

		// resource collection
		resourceCollection = database.getCollection("om2m_resource");
		LOGGER.info("mongoDB collection=" + resourceCollection);

		// announce collection
		announceCollection = database.getCollection("om2m_announce");

		if (DBConstants.DB_RESET) {

			LOGGER.info("delete all");
			resourceCollection.deleteMany(new Document());
			resourceCollection.dropIndexes();
			try {
				Document doc = new Document();
				doc.put("HierarchicalURI", 100);
				// resourceCollection.createIndex(Indexes.ascending("ResourceID",
				// "HierarchicalURI"),
				// new IndexOptions().unique(true));
				resourceCollection.createIndex(Indexes.ascending(
						"ResourceID"/* , "HierarchicalURI" */)/*
																 * , new
																 * IndexOptions(
																 * ).unique(
																 * true)
																 */);
				resourceCollection.createIndex(Indexes.ascending(/* "ResourceID", */ "HierarchicalURI"),
						new IndexOptions().unique(true)/*.weights(doc)*/);
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("Can't delete all ", e); //dgo
			}

			announceCollection.deleteMany(new Document());
			announceCollection.dropIndexes();
			announceCollection.createIndex(Indexes.ascending(DBEntities.LOCAL_RESOURCE_ID, DBEntities.ANNOUNCE_CSE_ID,
					DBEntities.REMOTE_RESOURCE_ID), new IndexOptions().unique(true));
		}

		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(CSEBaseEntity.class, new ResourceSerializerDeserializer<CSEBaseEntity>());
		gsonBuilder.registerTypeAdapter(AccessControlPolicyEntity.class,
				new ResourceSerializerDeserializer<AccessControlPolicyEntity>());
		gsonBuilder.registerTypeAdapter(AeEntity.class, new ResourceSerializerDeserializer<AeEntity>());
		gsonBuilder.registerTypeAdapter(FlexContainerEntity.class,
				new ResourceSerializerDeserializer<FlexContainerEntity>());
		gsonBuilder.registerTypeAdapter(ContainerEntity.class, new ResourceSerializerDeserializer<ContainerEntity>());
		gsonBuilder.registerTypeAdapter(SubscriptionEntity.class,
				new ResourceSerializerDeserializer<SubscriptionEntity>());
		gsonBuilder.registerTypeAdapter(RemoteCSEEntity.class, new ResourceSerializerDeserializer<RemoteCSEEntity>());
		gsonBuilder.registerTypeAdapter(AeAnncEntity.class, new ResourceSerializerDeserializer<AeAnncEntity>());
		gsonBuilder.registerTypeAdapter(FlexContainerAnncEntity.class,
				new ResourceSerializerDeserializer<FlexContainerAnncEntity>());
		gsonBuilder.registerTypeAdapter(DynamicAuthorizationConsultationEntity.class,
				new ResourceSerializerDeserializer<DynamicAuthorizationConsultationEntity>());
		gson = gsonBuilder.create();
	}

	@Override
	public DBTransaction getDbTransaction() {
		return new DBTransactionImpl();
	}

	@Override
	public DAOFactory getDAOFactory() {
		return new DAOFactoryImpl();
	}

	@Override
	public DBUtilManager getDBUtilManager() {
		return new DBUtilManagerImpl();
	}

	public MongoCollection<Document> getResourceCollection() {
		return resourceCollection;
	}

	public MongoCollection<Document> getAnnounceCollection() {
		return announceCollection;
	}

	public Gson getGson() {
		return gson;
	}

}
