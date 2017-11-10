package fr.adaming.managedBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import fr.adaming.modele.Categorie;
import fr.adaming.service.IServiceCategorie;

@ManagedBean(name = "categorieMB")
public class CategorieManagedBean {

	private Categorie categorie;

	@ManagedProperty(value = "#{categorieService}")
	private IServiceCategorie serviceCategorie;

	public CategorieManagedBean() {
		this.categorie = new Categorie();
	}
	

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	// Pour l'injection
	public IServiceCategorie getServiceCategorie() {
		return serviceCategorie;
	}

	public void setServiceCategorie(IServiceCategorie serviceCategorie) {
		this.serviceCategorie = serviceCategorie;
	}

	// M�thodes propres

	public String ajouterCategorie() {
		// Appel de la m�thode
		Categorie categorieAjout = serviceCategorie.ajouterCategorie(this.categorie);
		if (categorieAjout != null) {
			// On r�cup�re la liste des cat�gories
			List<Categorie> listeCategorie = serviceCategorie.listerCategorie();
			// On ajoute la liste dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeCategories",
					listeCategorie);
		}
		return "accueil";
	}

	public String rechercheCategorieParId() {
		// Appelle de la m�thode de recherche
		Categorie categorieCherche = serviceCategorie.rechercherCategorieParId(this.categorie);
		if (categorieCherche != null) {
			this.categorie = categorieCherche;
		}
		return "accueil";
	}

	public String supprimerCategorie() {
		// Appel de la m�thode
		serviceCategorie.supprimerCategorie(categorie);
		// On r�cup�re la liste des cat�gories
		List<Categorie> listeCategorie = serviceCategorie.listerCategorie();
		// On ajoute la liste dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeCategories",
				listeCategorie);

		return "accueil";
	}
	
	public String modifierCategorie(){
		Categorie catModif = serviceCategorie.modifierCategorie(this.categorie);
		if(catModif!=null){
			// On r�cup�re la liste des cat�gories
			List<Categorie> listeCategorie = serviceCategorie.listerCategorie();
			// On ajoute la liste dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeCategories",
					listeCategorie);
		}
		return "accueil";
	}

}
