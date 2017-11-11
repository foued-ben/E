package fr.adaming.validateur;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

 @FacesValidator("verifPrix")
public class validateurPrix implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent composant, Object value) throws ValidatorException {

		double saisie = (double) value;		
		if(saisie <=0){
			throw new ValidatorException(new FacesMessage("Nous ne faisons pas dans la charité. Le prix est trop bas."));
		}
	}

}
