package ama.redcross.Delegates;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import ama.redcross.Helpers.FirbaseDbHelper;
import ama.redcross.processdata.User;

//Folgende Klasse dient um die Kundendaten zu speichern

@Named("saveUserDataAdapter")
public class SaveUserData implements JavaDelegate {
	@Autowired
	private FirbaseDbHelper firebase;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		User user = (User) execution.getVariable("user");
		firebase.saveUser(user);
	}

}
