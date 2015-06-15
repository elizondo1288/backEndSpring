package com.backend.contacts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.contacts.entities.*;
import com.backend.contacts.dataLayer.MongoDBController;

@RestController
public class ContactsController {

	@RequestMapping("/contactTest")
    public ResponseDTO addContact(@RequestParam(value="name") String name) {
        
		ContactDTO contact = new ContactDTO();
		contact.setName(name);
				
		ResponseDTO response = returnResponse(true);
		response.setMessage(response.getMessage() + " " + contact.getName());
		
		return response;
    }
	
	
	@RequestMapping(value = "/addContact", method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE},produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseDTO restAddContact(@RequestBody ContactDTO obj) {

		ResponseDTO response = MongoDBController.getInstance().insertContact(obj);		
				
		return response;
	}
	
	@RequestMapping(value = "/updateContact", method = RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE},produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseDTO restUpdateContact(@RequestBody UpdateDTO obj) {
		
		System.out.println("New: " + obj.getEmail() + " and old:"+ obj.getOldEmail());
		
		ResponseDTO response = MongoDBController.getInstance().updateContact(obj);
		
		return response;
	}
	
	@RequestMapping(value = "/deleteContact", method = RequestMethod.DELETE, consumes={MediaType.APPLICATION_JSON_VALUE},produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseDTO restDeleteContact(@RequestBody DeleteDTO obj) {
			
		System.out.println("Email to delete: " + obj.getEmail());
		
		
		ResponseDTO response = MongoDBController.getInstance().deleteContact(obj.getEmail());
		response.setMessage(response.getMessage() + " Deleting: " + obj.getEmail());
		return response;
	}
	
	@RequestMapping(value = "/getContacts", method = RequestMethod.GET)
	public List<ContactDTO> restGetContact() {
		
		List<ContactDTO> list = MongoDBController.getInstance().getAllContacts();
		
		/*for(ContactDTO contact : list){
			
			System.out.println(contact.getName() + " -- " + contact.getEmail());
		}*/
		
		return list;
	}
	
	
	private ResponseDTO returnResponse(Boolean success){
		
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
