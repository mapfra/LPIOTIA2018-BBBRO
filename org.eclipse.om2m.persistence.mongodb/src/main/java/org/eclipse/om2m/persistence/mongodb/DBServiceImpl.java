/*******************************************************************************
 * Copyright (c) 2014 - 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    BAREAU Cyrille <cyrille.bareau@orange.com>, 
 *    BONNARDEL Gregory <gbonnardel.ext@orange.com>, 
 *******************************************************************************/
package org.eclipse.om2m.persistence.mongodb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.AreaNwkDeviceInfoAnncEntity;
import org.eclipse.om2m.commons.entities.AreaNwkDeviceInfoEntity;
import org.eclipse.om2m.commons.entities.AreaNwkInfoAnncEntity;
import org.eclipse.om2m.commons.entities.AreaNwkInfoEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.commons.entities.DeviceInfoAnncEntity;
import org.eclipse.om2m.commons.entities.DeviceInfoEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.MgmtObjAnncEntity;
import org.eclipse.om2m.commons.entities.MgmtObjEntity;
import org.eclipse.om2m.commons.entities.NodeAnncEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
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

public class DBServiceImpl implements DBService, org.eclipse.om2m.persistence.mongodb.Constants {

	private static final Log LOGGER = LogFactory.getLog(DBServiceImpl.class);

	private static final DBServiceImpl INSTANCE = new DBServiceImpl();

	private MongoCollection<Document> resourceCollection;
	private MongoCollection<Document> announceCollection;

	private GsonBuilder gsonBuilder;
	private Gson gson;

	public static DBServiceImpl getInstance() {
		return INSTANCE;
	}

	private DBServiceImpl() {
	}

	protected void init() {
		MongoClient mongoClient = new MongoClient(DBConstants.DB_URL);
//			System.getProperty("org.eclipse.om2m.dbUrl", "jdbc:mongo://cloud1/default"));
		LOGGER.info("mongoDB client=" + mongoClient);

		MongoDatabase database = mongoClient.getDatabase("mydb_" + Constants.CSE_NAME);
		// resource collection
		resourceCollection = database.getCollection("om2m_resource");
		// announce collection
		announceCollection = database.getCollection("om2m_announce");

		if (DBConstants.DB_RESET) {
			LOGGER.info("delete all");
			resourceCollection.deleteMany(new Document());
			resourceCollection.dropIndexes();
			try {
				Document doc = new Document();
				doc.put(HIERARCHICAL_URI, 100);
				// resourceCollection.createIndex(Indexes.ascending(RES_ID, "HierarchicalURI"),
				// 		new IndexOptions().unique(true));
				resourceCollection.createIndex(Indexes.ascending(RES_ID));
				resourceCollection.createIndex(Indexes.ascending(HIERARCHICAL_URI),
						new IndexOptions().unique(true));
			} catch (Exception e) {
				LOGGER.error("Can't delete all ", e); //dgo
			}

			announceCollection.deleteMany(new Document());
			announceCollection.dropIndexes();
			announceCollection.createIndex(Indexes.ascending(DBEntities.LOCAL_RESOURCE_ID, 
						DBEntities.ANNOUNCE_CSE_ID,
						DBEntities.REMOTE_RESOURCE_ID), 
					new IndexOptions().unique(true));
		}

		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(CSEBaseEntity.class, 
				new ResourceSerializerDeserializer<CSEBaseEntity>());
		gsonBuilder.registerTypeAdapter(AccessControlPolicyEntity.class,
				new ResourceSerializerDeserializer<AccessControlPolicyEntity>());
		gsonBuilder.registerTypeAdapter(AeEntity.class, 
				new ResourceSerializerDeserializer<AeEntity>());
		gsonBuilder.registerTypeAdapter(FlexContainerEntity.class,
				new ResourceSerializerDeserializer<FlexContainerEntity>());
		gsonBuilder.registerTypeAdapter(ContainerEntity.class, 
				new ResourceSerializerDeserializer<ContainerEntity>());
		gsonBuilder.registerTypeAdapter(ContentInstanceEntity.class, 
				new ResourceSerializerDeserializer<ContentInstanceEntity>());
		gsonBuilder.registerTypeAdapter(SubscriptionEntity.class,
				new ResourceSerializerDeserializer<SubscriptionEntity>());
		gsonBuilder.registerTypeAdapter(RemoteCSEEntity.class, 
				new ResourceSerializerDeserializer<RemoteCSEEntity>());
		gsonBuilder.registerTypeAdapter(AeAnncEntity.class, 
				new ResourceSerializerDeserializer<AeAnncEntity>());
		gsonBuilder.registerTypeAdapter(FlexContainerAnncEntity.class,
				new ResourceSerializerDeserializer<FlexContainerAnncEntity>());
		gsonBuilder.registerTypeAdapter(DynamicAuthorizationConsultationEntity.class,
				new ResourceSerializerDeserializer<DynamicAuthorizationConsultationEntity>());
		gsonBuilder.registerTypeAdapter(NodeEntity.class, 
				new ResourceSerializerDeserializer<NodeEntity>());
		gsonBuilder.registerTypeAdapter(NodeAnncEntity.class, 
				new ResourceSerializerDeserializer<NodeAnncEntity>());
		gsonBuilder.registerTypeAdapter(MgmtObjEntity.class, 
				new ResourceSerializerDeserializer<MgmtObjEntity>());
		gsonBuilder.registerTypeAdapter(MgmtObjAnncEntity.class, 
				new ResourceSerializerDeserializer<MgmtObjAnncEntity>());
		gsonBuilder.registerTypeAdapter(DeviceInfoEntity.class, 
				new ResourceSerializerDeserializer<DeviceInfoEntity>());
		gsonBuilder.registerTypeAdapter(DeviceInfoAnncEntity.class, 
				new ResourceSerializerDeserializer<DeviceInfoAnncEntity>());
		gsonBuilder.registerTypeAdapter(AreaNwkDeviceInfoEntity.class, 
				new ResourceSerializerDeserializer<AreaNwkDeviceInfoEntity>());
		gsonBuilder.registerTypeAdapter(AreaNwkDeviceInfoAnncEntity.class, 
				new ResourceSerializerDeserializer<AreaNwkDeviceInfoAnncEntity>());
		gsonBuilder.registerTypeAdapter(AreaNwkInfoEntity.class, 
				new ResourceSerializerDeserializer<AreaNwkInfoEntity>());
		gsonBuilder.registerTypeAdapter(AreaNwkInfoAnncEntity.class, 
				new ResourceSerializerDeserializer<AreaNwkInfoAnncEntity>());
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
