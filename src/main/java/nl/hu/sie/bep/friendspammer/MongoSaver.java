package nl.hu.sie.bep.friendspammer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoSaver {
	private static Logger logger = Logger.getLogger(EmailSender.class.getName());
	private static Properties prop = new Properties();
	InputStream input = null;

	private MongoSaver() throws FileNotFoundException {
		input = new FileInputStream("config.properties");
	}

	public static boolean saveEmail(String to, String from, String subject, String text, Boolean html) {

		MongoConnection mongoConnection = new MongoConnection(prop.getProperty("mongoUserName"), prop.getProperty("mongoPassword"));

		boolean success = true;

		try (MongoClient mongoClient = new MongoClient(new ServerAddress("YOUR HOST", 27939), mongoConnection.credential, MongoClientOptions.builder().build()) ) {

			MongoDatabase db = mongoClient.getDatabase( mongoConnection.database );

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
