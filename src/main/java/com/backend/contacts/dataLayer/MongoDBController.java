package com.backend.contacts.dataLayer;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.backend.contacts.entities.*;
import com.mongodb.MongoClient;

public class MongoDBController {

	private static MongoDBController instance = null;
	
	public static MongoDBController getInstance() {
		
		if(instance == null) {
	         instance = new MongoDBController();
	    }
		
	    return instance;
	}
	
	protected MongoDBController(){
		// Exists only to defeat instantiation.
	}
	
	public Boolean insertContact(ContactDTO contact){
		
		MongoOperations mongoOps;
		try {
			
			mongoOps = new MongoTemplate(new MongoClient(), "test");
			System.out.println("Apunto de insertar");
			
			mongoOps.insert(contact);
			System.out.println("Inserto el contacto");
			
			return true;

		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			
			return false;
		}
	}
	
	public List<ContactDTO> getAllContacts(){
		
		MongoOperations mongoOps;
		try {
			
			mongoOps = new MongoTemplate(new MongoClient(), "test");
			
			List<ContactDTO> listOfContacts =  mongoOps.findAll(ContactDTO.class);
			System.out.println("Results: " + listOfContacts);
			
			return listOfContacts;

		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			
			return null;
		}		
	}
}
