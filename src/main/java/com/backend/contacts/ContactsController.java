package com.backend.contacts;

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

		//Boolean success = MongoDBController.getInstance().insertContact(obj); 
		ResponseDTO response = returnResponse(true);
		
				
		return response;
	}
	
	@RequestMapping(value = "/updateContact", method = RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE},produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseDTO restUpdateContact(@RequestBody UpdateDTO obj) {
		
		ResponseDTO response = returnResponse(true);
		response.setMessage("New: " + obj.getEmail() + " and old:"+ obj.getOldEmail());
		return response;
	}
	
	@RequestMapping(value = "/deleteContact", method = RequestMethod.DELETE, consumes={MediaType.APPLICATION_JSON_VALUE},produces={MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseDTO restDeleteContact(@RequestBody String email) {
				
		ResponseDTO response = returnResponse(true);
		response.setMessage(response.getMessage() + " Deleting: " + email);
		return response;
	}
	
	@RequestMapping(value = "/getContacts", method = RequestMethod.GET)
	public @ResponseBody ResponseDTO restGetContact(@RequestBody ContactDTO obj) {
				
		/*List<ObjPerson> list1 = mongoOps.findAll(ObjPerson.class);
			System.out.println("Results: " + list1);
			*/
		ResponseDTO response = returnResponse(true);
		
		return response;
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
