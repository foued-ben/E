package fr.adaming.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.util.Base64Utils;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.modele.Administrateur;
import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;
import fr.adaming.service.IProduitService;
import fr.adaming.service.IServiceAdmin;
import fr.adaming.service.IServiceCategorie;

@ManagedBean(name="adminMB")
@RequestScoped
public class AdminManagedBean {

	@ManagedProperty(value="#{serviceAdmin}")
	private IServiceAdmin serviceAdmin;
	@ManagedProperty(value="#{produitService}")
	private IProduitService serviceProduit;
	@ManagedProperty(value="#{categorieService}")
	private IServiceCategorie serviceCategorie;
	
	private Administrateur administrateur;
	private List<Produit> listeProduit;
	private List<Categorie> listeCategorie;

	public AdminManagedBean() {
		this.administrateur = new Administrateur();
		this.listeProduit = new ArrayList<>();
		this.listeCategorie = new ArrayList<>();
	}

	//Getters/Setters pour Injections
	public IServiceAdmin getServiceAdmin() {
		return serviceAdmin;
	}

	public void setServiceAdmin(IServiceAdmin serviceAdmin) {
		this.serviceAdmin = serviceAdmin;
	}
	
	public void setServiceProduit(IProduitService serviceProduit) {
		this.serviceProduit = serviceProduit;
	}
	
	public void setServiceCategorie(IServiceCategorie serviceCategorie) {
		this.serviceCategorie = serviceCategorie;
	}

//Getters /Setters pour les attributs
	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}
	
	public List<Produit> getListeProduit() {
		return listeProduit;
	}

	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}

	public List<Categorie> getListeCategorie() {
		return listeCategorie;
	}

	public void setListeCategorie(List<Categorie> listeCategorie) {
		this.listeCategorie = listeCategorie;
	}

	
	//Méthodes du managedBean
	



	public String connexion(){
		// On appelle la méthode de service
		Administrateur administrateurConnexion =  serviceAdmin.connexionAdmin(this.administrateur);
		if(administrateurConnexion!=null){
			//On ajoute l'agent à la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sessionAgent", administrateurConnexion);
			//On liste l'ensemble des produits et des catégories
			listeProduit = serviceProduit.listerProduits();
			List<Categorie> listeCategorie = serviceCategorie.listerCategorie();


			//On ajoute la liste des produits et la liste des catégories à la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeProduits", listeProduit);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeCategories", listeCategorie);
			return "accueil";
		}else {
			//On affiche un message pour indiquer le problème
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Connexion impossible. Le mot de passe et/ou l'identifiant est(sont) erroné(s)"));
			return "login";
		}
	}
	
	public String deconnexion(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "login";
		
	}
	
	public void ecrirePDF(){
		Document docPDF = new Document();
	}
	
	
}
