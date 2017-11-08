package fr.adaming.managedBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import fr.adaming.modele.Administrateur;
import fr.adaming.service.IServiceAdmin;

@ManagedBean(name="adminMB")
@RequestScoped
public class AdminManagedBean {

	@ManagedProperty(value="#{serviceAdmin}")
	private IServiceAdmin serviceAdmin;
	
	private Administrateur administrateur;

	public AdminManagedBean() {
		this.administrateur = new Administrateur();
	}

	public IServiceAdmin getServiceAdmin() {
		return serviceAdmin;
	}

	public void setServiceAdmin(IServiceAdmin serviceAdmin) {
		this.serviceAdmin = serviceAdmin;
	}

	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}
	
	
	//Méthodes du managedBean
	
	public void connexion(){
		// On appelle la méthode de service
		Administrateur administrateurConnexion =  serviceAdmin.connexionAdmin(this.administrateur);
		if(administrateurConnexion!=null){
			System.out.println("Connexion");
		}else {
			System.out.println("Connexion impossible. Le mot de passe et/ou l'identifiant est(sont) erroné(s)");
		}
	}
	
}
