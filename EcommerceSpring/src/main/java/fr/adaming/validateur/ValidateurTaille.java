package fr.adaming.validateur;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
@FacesValidator("Taillemini")
public class ValidateurTaille implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent composant, Object value) throws ValidatorException {
		
		if(value==null){
			throw new ValidatorException(new FacesMessage("Objet nul."));

		}
	}

	
}
