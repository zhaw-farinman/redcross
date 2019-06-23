package ama.redcross.Delegates;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import ama.redcross.Helpers.FirbaseDbHelper;
import ama.redcross.processdata.User;

//Folgende Klasse dient um die Kundendaten zu ermitteln

@Named("getUserDataAdapter")
public class GetUserData implements JavaDelegate{
	@Autowired
	private FirbaseDbHelper firebase;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String userId = (String) execution.getVariable("userId");
		System.out.println("UserId:"+userId);
		//to-do
		//delete userId
		//userId = "uZ4mNrKQ3dWyYlUfP554STn1n9h1";
		User user = firebase.getUser(userId);
		System.out.println(user.getFirstName());
		execution.setVariable("user", user);
	
	}
	
}
