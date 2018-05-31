package nl.hu.sie.bep.friendspammer;

import com.mongodb.MongoCredential;

public class MongoConnection {
    MongoCredential credential ;
    String database = "friendspammer";

    public MongoConnection(String userName, String password) {
        credential = MongoCredential.createCredential(userName, database, password.toCharArray());
    }
}
