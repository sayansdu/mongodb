package kz.sayan;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class App
{
    public static void main( String[] args )
    {
        System.out.println("Program is starting!");
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        System.out.println("is admin DB exits: " + (isDbExist(mongoClient, "local")));

        MongoDatabase database = mongoClient.getDatabase("local");
        MongoCollection<Document> table = getTable(database, "temp");
        createObject(table);
        Document query = new Document();
        query.put("name", "sayan");
        updateObject(table, query);

        Document search = findDoc(table);
        System.out.println((search != null ? search.toJson() : "cannot find anything"));
    }

    private static boolean isDbExist(MongoClient client, String dbName) {
        for (String s : client.listDatabaseNames()) {
            if(s.equals(dbName))
                return true;
        }
        return false;
    }

    private static MongoCollection<Document> getTable(MongoDatabase database, String table) {
        for (String s : database.listCollectionNames()) {
            if(s.equals(table))
                return database.getCollection(table);
        }
        database.createCollection(table);
        return database.getCollection(table);
    }

    private static void createObject(MongoCollection<Document> mongoCollection) {
        Document object = new Document();
        object.put("name", "sayan");
        object.put("age", "27");
        object.put("gender", "M");
        mongoCollection.insertOne(object);
    }

    private static void updateObject(MongoCollection<Document> collection, Document query) {
        Document updated = new Document();
        updated.put("girl", "almira");
        collection.updateOne(query, new Document("$set", updated));
    }

    private static Document findDoc(MongoCollection<Document> collection) {
        Document query = new Document();
        query.put("name", "sayan");
        FindIterable<Document> iterable = collection.find(query);
        for (Document document : iterable) {
            return document;
        }
        return null;
    }
}
