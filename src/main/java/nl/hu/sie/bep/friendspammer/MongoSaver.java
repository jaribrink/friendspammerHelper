package nl.hu.sie.bep.friendspammer;

import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoSaver {
	private static Logger logger = Logger.getLogger(EmailSender.class.getName());

	public static boolean saveEmail(String to, String from, String subject, String text, Boolean html, String userName, String password) {
		String database = "friendspammer";

		MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());

		boolean success = true;

		try (MongoClient mongoClient = new MongoClient(new ServerAddress("YOUR HOST", 27939), credential, MongoClientOptions.builder().build()) ) {

			MongoDatabase db = mongoClient.getDatabase( database );

			MongoCollection<Document> c = db.getCollection("email");

			Document  doc = new Document ("to", to)
					.append("from", from)
					.append("subject", subject)
					.append("text", text)
					.append("asHtml", html);
			c.insertOne(doc);
		} catch (MongoException mongoException) {
			logger.config("XXXXXXXXXXXXXXXXXX ERROR WHILE SAVING TO MONGO XXXXXXXXXXXXXXXXXXXXXXXXXX");
			logger.config(mongoException.toString());
			success = false;
		}

		return success;

	}

	public static void main(String ...args) {
		logger.config("test");
	}
}
