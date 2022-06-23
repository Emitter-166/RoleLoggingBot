package org.example;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.NoSuchElementException;

public class Database extends ListenerAdapter {
    public static MongoCollection collection;
    @Override
    public void onReady(ReadyEvent e){
        String uri = System.getenv("uri");
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient client = new MongoClient(clientURI);
        MongoDatabase database = client.getDatabase("serverConfig");
        collection = database.getCollection("serverConfig");

    }



    public static void set(String Id, String Key, String value, boolean isAdd){
        updateDB(Id,"serverId", Key, value, isAdd);
    }

    public static void setUser(String Id, String Key, String value, boolean isAdd){
        updateDB(Id, "userId",Key, value, isAdd );
    }


    public static Document get(String Id){
        return (Document) collection.find(new Document("serverId", Id)).cursor().next();
    }

    public static Document getUser(String Id){
        return (Document) collection.find(new Document("userId", Id)).cursor().next();
    }



    private static void createDB(String Id){
        //server config
        Document document = new Document("serverId", Id)
                .append("countingChannel", "none")
                .append("hasRewards", false)
                .append("beforeReward", 0)
                .append("admins", "0")
                .append("action", "0-message")
                .append("emoji", ":sparkles:");

        collection.insertOne(document);

    }


    private static void createUserDB(String userId){
        //user config
        Document document = new Document("userId", userId)
                .append("counted", 0);
        collection.insertOne(document);
    }


    private static void updateDB(String Id, String field,  String key, String value, boolean isAdd){
        //for server
        Document document = null;
        try{
            document = (Document) collection.find(new Document(field, Id)).cursor().next();
        }catch (NoSuchElementException exception){
            if(field.equalsIgnoreCase("serverId")){
                createDB(Id);
            }else{
                createUserDB(Id);
            }
        }

        if(!isAdd){
            Document Updatedocument = new Document(key, value);
            Bson updateKey = new Document("$set", Updatedocument);
            collection.updateOne(document, updateKey);
        }else{
            Document Updatedocument = new Document(key, document.get("key") + value);
            Bson updateKey = new Document("$set", Updatedocument);
            collection.updateOne(document, updateKey);
        }

    }

}
