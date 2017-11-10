package fr.adaming.managedBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;
import fr.adaming.service.IProduitService;

@ManagedBean(name="produitMB")
@RequestScoped
public class ProduitManagedBean {

//Attribut;
	private int idCategorie;
	private Produit produit;
	
	// Injection dépendences
	@ManagedProperty(value="#{produitService}")
	private IProduitService serviceProduit;

	public ProduitManagedBean() {
		super();
		this.produit = new Produit();
	}

	public int getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public IProduitService getServiceProduit() {
		return serviceProduit;
	}

	public void setServiceProduit(IProduitService serviceProduit) {
		this.serviceProduit = serviceProduit;
	}
	
	//Méthodes propres.
	
	public String rechercheProduitParId(){
		//Appel de la méthode
		Produit produitCherche = serviceProduit.rechercherProduitAvecId(this.produit);
		if(produitCherche!=null){
			this.produit = produitCherche;
		}
		return "accueil";
	}
	
	public String ajouterProduit(){
		Categorie catTemp = new Categorie();
		catTemp.setIdCategorie(this.idCategorie);
		this.produit.setCategorie(catTemp);
		//Appel de la méthode
		Produit produitAjout = serviceProduit.ajouterProduit(this.produit);
		if(produitAjout!=null){
			// On actualise la liste et on l'ajoute à la session
			List<Produit> listeProduit = serviceProduit.listerProduits();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeProduits", listeProduit);
		}
		return "accueil";
	}
	
	public String supprimerProduit(){
		//Appel de la méthode
		serviceProduit.supprimerProduit(this.produit);
		// On actualise la liste et on l'ajoute à la session
		List<Produit> listeProduit = serviceProduit.listerProduits();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeProduits", listeProduit);
		return "accueil";
	}
	
	public String modifierProduit(){
		Categorie categorieTemp = new Categorie();
		categorieTemp.setIdCategorie(this.idCategorie);
		this.produit.setCategorie(categorieTemp);
		//Appel de la méthode
		Produit produitModif = serviceProduit.modifierProduit(this.produit);
		if(produitModif!=null){
			// On actualise la liste et on l'ajoute à la session
			List<Produit> listeProduit = serviceProduit.listerProduits();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeProduits", listeProduit);
		}else {
			System.out.println("Modif Impossible");
		}
		
		return "accueil";
	}
	
}
