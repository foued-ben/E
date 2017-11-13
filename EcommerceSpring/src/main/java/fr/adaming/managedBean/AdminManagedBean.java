package fr.adaming.managedBean;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.util.Base64Utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableFooter;
import com.itextpdf.text.pdf.PdfPTableHeader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.fonts.otf.TableHeader;

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
			//On ajoute la liste des images 
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
		//On récupère les informations sur les produits depuis la session.
		listeProduit = (List<Produit>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listeProduits");
		listeCategorie = (List<Categorie>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listeCategories");
		Document document = new Document();
		try {
			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\inti0236\\Desktop\\RécapitulatifInventaire.pdf"));
			document.open();

//Lister les produits dans le PDF
			Paragraph header = new Paragraph();
			header.add("Les produits proposés par le magasin sont : ");
			document.add(header);
			document.add(new Paragraph("\n"));

			// Entête du tableau
			String[] enteteTableau ={"Id","Produit","Description","Quantité","Prix","Catégorie"};
			PdfPTable table = new PdfPTable(enteteTableau.length);
			//Création de l'entete du tableau

			for(String caseEntete : enteteTableau){
				Paragraph celluleEnteteTemp = new Paragraph();
				celluleEnteteTemp.add(caseEntete);
				table.addCell(celluleEnteteTemp);
			}
			
			//On ajoute les produits dans la liste
			for(Produit prod :listeProduit){
				//Pour chaque produit on stocke les infos dans un tableau d'objet
				String categorieTemp = String.valueOf(prod.getCategorie().getIdCategorie()) +"/" + prod.getDescription();
				Object[] tableauAEntrer  ={prod.getIdProduit(),prod.getDesignation(),prod.getDescription(),prod.getQuantite(),prod.getPrix(),categorieTemp};
				//On ajoute l'info de chaque objet du tableau dans la cellule après toString
				for(Object objet: tableauAEntrer){
					Paragraph paraTemp = new Paragraph();
					paraTemp.add(objet.toString());
					table.addCell(paraTemp);
				}
			}
			document.add(table);

			header.clear();
			//Ajouter les catégories dans le PDF
			header.add("Les catégories regroupant les produits proposés sont :");
			document.add(header);
			document.add(new Paragraph("\n"));
			String[] enteteTableauCategorie ={"ID","Désignation","Description","Nombre de Produits"};
			PdfPTable tableCategorie = new PdfPTable(enteteTableauCategorie.length);

			//Création de l'entete du tableau
			for(String entete:enteteTableauCategorie){
				Paragraph celluleEnteteTemp = new Paragraph();
				celluleEnteteTemp.add(entete);
				tableCategorie.addCell(celluleEnteteTemp);
			}
			
			for(Categorie categorie : listeCategorie){
				String nombreProduit = String.valueOf(categorie.getListeProduits().size());
				Object[] tableauAEntrer = {categorie.getIdCategorie(),categorie.getNomCategorie(),categorie.getDescription(),categorie.getDescription(),nombreProduit};
				for(Object objet:tableauAEntrer){
					Paragraph paraTemp = new Paragraph();
					paraTemp.add(objet.toString());
					tableCategorie.addCell(paraTemp);
				}
			}
			
			document.add(tableCategorie);
			document.close();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le PDF contenant l'inventaire a bien été créé."));

		} catch (FileNotFoundException | DocumentException e) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le PDF n'a pas pu être créé."));

			e.printStackTrace();
		}
	}
	
	
	
}
