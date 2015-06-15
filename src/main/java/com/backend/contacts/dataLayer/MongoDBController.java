package com.backend.contacts.dataLayer;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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
	
	//INSERT = Inserts a contact in the database
	public ResponseDTO insertContact(ContactDTO contact){
		
		MongoOperations mongoOps;
		try {
			
			mongoOps = new MongoTemplate(new MongoClient(), "test");
			System.out.println("Apunto de insertar");
			
			mongoOps.insert(contact);
			System.out.println("Inserto el contacto");
			
			return returnObjResponse(true);

		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			
			return returnObjResponse(false);
		}
	}
	
	//UPDATE = Updates a contact in the database
	public ResponseDTO updateContact(ContactDTO contact){
		

		MongoOperations mongoOps;
		try {
			
			mongoOps = new MongoTemplate(new MongoClient(), "test");
			
			Query query = new Query();
			query.addCriteria(Criteria.where("email").is(contact.getEmail()));
			
			ContactDTO contactFinded = mongoOps.findOne(query, ContactDTO.class);
			System.out.println("userTest3 - " + contactFinded);
			
			Update update = new Update();
			update.set("name", contact.getName());
			update.set("address", contact.getAddress());
			update.set("email", contact.getEmail());
			update.set("phone", contact.getPhone());
			
			mongoOps.updateFirst(query, update, ContactDTO.class);
			
			
			System.out.println("Apunto de Update");
			
			
			
			System.out.println("Termino el update");
			
			
			//returns everything
			Query query1 = new Query();
			query1.addCriteria(Criteria.where("email").is(contact.getEmail()));
	 
			ContactDTO contactFinded2 = mongoOps.findOne(query, ContactDTO.class);
			System.out.println("userTest3 - " + contactFinded2);
			
			return returnObjResponse(true);

		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			
			return returnObjResponse(false);
		}
	}
	
	//DELETE = Deletes a contact from the database
	public ResponseDTO deleteContact(String email){
		
		MongoOperations mongoOps;
		try {
			
			mongoOps = new MongoTemplate(new MongoClient(), "test");
			
			Query query = new Query();
			query.addCriteria(Criteria.where("email").is(email));
			ContactDTO contactFinded = mongoOps.findOne(query, ContactDTO.class);
			System.out.println("userTest3 - " + contactFinded.getId());
			
			
			if(contactFinded != null){
				System.out.println("Apunto de Delete");
				mongoOps.remove(contactFinded);
				System.out.println("Termino el Delete");
				
				return returnObjResponse(true);
				
			}else{
				
				ResponseDTO response = returnObjResponse(false);
				response.setMessage("The Contact Does Not Exists");
				
				return response;
			}

		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			
			return returnObjResponse(false);
		}
	}

	//GET = Return the list of the contacts from the Database
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
		
	private ResponseDTO returnObjResponse(Boolean success){
		
		ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext("beans.xml");
		ResponseDTO response;
		
		if(success){
			response = (ResponseDTO)context.getBean("responseSuccess");
		}else{
			response = (ResponseDTO)context.getBean("responseFailure");
		}
		
		context.close();
		
		

		
		return response;
	}
}