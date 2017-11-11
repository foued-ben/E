package fr.adaming.managedBean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.model.UploadedFile;
import org.springframework.util.Base64Utils;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;
import fr.adaming.service.IProduitService;

@ManagedBean(name="produitMB")
@RequestScoped
public class ProduitManagedBean {

//Attribut;
	private int idCategorie;
	private Produit produit;
	private UploadedFile imageFichier;
	// Injection d�pendences
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
	
	
	public UploadedFile getImageFichier() {
		return imageFichier;
	}

	public void setImageFichier(UploadedFile imageFichier) {
		this.imageFichier = imageFichier;
	}

	
	//M�thodes propres.
	

	public String rechercheProduitParId(){
		//Appel de la m�thode
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
		System.out.println(produit.getCategorie());
		this.produit.setImageFichier(imageFichier.getContents());

		//Appel de la m�thode
		Produit produitAjout = serviceProduit.ajouterProduit(this.produit);
		if(produitAjout!=null){
			// On actualise la liste et on l'ajoute � la session
			List<Produit> listeProduit = serviceProduit.listerProduits();

			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeProduits", listeProduit);
		}
		return "accueil";
	}
	
	public String supprimerProduit(){
		//Appel de la m�thode
		serviceProduit.supprimerProduit(this.produit);
		// On actualise la liste et on l'ajoute � la session
		List<Produit> listeProduit = serviceProduit.listerProduits();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeProduits", listeProduit);
		return "accueil";
	}
	
	public String modifierProduit(){
		Categorie categorieTemp = new Categorie();
		categorieTemp.setIdCategorie(this.idCategorie);
		this.produit.setCategorie(categorieTemp);
		//Appel de la m�thode
		Produit produitModif = serviceProduit.modifierProduit(this.produit);
		if(produitModif!=null){
			// On actualise la liste et on l'ajoute � la session
			List<Produit> listeProduit = serviceProduit.listerProduits();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeProduits", listeProduit);
		}else {
			System.out.println("Modif Impossible");
		}
		
		return "accueil";
	}
	
	public String associationImageProduit(){
		//On appelle la m�thode
		this.produit.setImageFichier(imageFichier.getContents());
		int verif =serviceProduit.assoicierImageProduit(this.produit);
		if(verif==1){
			System.out.println("Ca a march�");
		}else{
			System.out.println("Erreur d'ajout de l'image");
		}
		return "accueil";
	}
	
	public void verifCategorie(FacesContext context, UIComponent composant, Object value) throws ValidatorException{
		// On r�cup�re la liste des cat�gories
		 int verif =0;
		List<Categorie> listeCategorie = (List<Categorie>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listeCategories");
		for(Categorie categorie:listeCategorie){
			if(categorie.getIdCategorie()==idCategorie){
				verif =verif+1;
			}
		}
		if(verif ==0){
			
			//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage());
			throw new ValidatorException(new FacesMessage("Cat�gorie non existante. Veuillez choisir une cat�gorie qui existe."));

		}
	}
	
	public void validationStock(FacesContext context, UIComponent composant, Object value) throws ValidatorException{
		int saisie = (int) value;
		if(saisie==0){
			throw new ValidatorException(new FacesMessage("Un produit doit avoir au moins un exemplaire en stock."));
		}
	}
}
