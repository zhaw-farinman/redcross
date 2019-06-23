package ama.redcross.Helpers;
import static java.lang.Math.toIntExact;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Component;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import ama.redcross.processdata.User;



@Component
public class FirbaseDbHelper {
	private FileInputStream serviceAccount;
	private FirebaseOptions options;
	private Firestore db;
	
	//Hiermit wird die Verbindung zur Firestore DB gestartet
	public void startConnection() throws IOException, InterruptedException, ExecutionException {
	    
		this.serviceAccount = new FileInputStream("./ServiceAccountKey.json");
		this.options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(this.serviceAccount))
				.build();

		FirebaseApp.initializeApp(this.options);

		
		this.db = com.google.firebase.cloud.FirestoreClient.getFirestore();
		
		
	}
	
	
	//Hier werden die Kundendatenermittelt
	public User getUser(String userId) throws InterruptedException, ExecutionException, IOException {
		this.startConnection();
		
		DocumentReference docRef = this.db.collection("Users").document(userId);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		
		User user = new User();
		user.setFirstName(document.getString("firstName"));
		user.setLastName(document.getString("lastName"));
		user.setStreet(document.getString("street"));
		user.setPlace(document.getString("place"));
		user.setPostcode(toIntExact(document.getLong("postcode")));
		user.setEmail(document.getString("email"));
		user.setPrivatePhone(document.getString("privatePhone"));
		user.setMobilePhone(document.getString("mobilePhone"));
		user.setUid(document.getId());
		
		
		FirebaseApp.getInstance().delete();
			
		return user;
	}
	
	//Hier werden die neuen Kundendaten gespeichert
	public void saveUser(User user) throws IOException, InterruptedException, ExecutionException {
		this.startConnection();
		DocumentReference docRef = this.db.collection("Users").document(user.getUid());
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		docRef.update("firstName", user.getFirstName());
		docRef.update("lastName", user.getLastName());
		docRef.update("place", user.getPlace());
		docRef.update("street", user.getStreet());
		docRef.update("postcode", user.getPostcode());
		docRef.update("email", user.getEmail());
		docRef.update("privatePhone", user.getPrivatePhone());
		docRef.update("mobilePhone", user.getMobilePhone());
		docRef.update("checked", user.getChecked());
		System.out.println(user.getChecked());
		FirebaseApp.getInstance().delete();
		//to-do save data
	}
	
}
