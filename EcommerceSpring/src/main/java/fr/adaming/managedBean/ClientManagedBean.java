package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.modele.Administrateur;
import fr.adaming.modele.Categorie;
import fr.adaming.modele.Client;
import fr.adaming.modele.LigneCommande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;
import fr.adaming.service.IClientService;
import fr.adaming.service.IProduitService;
import fr.adaming.service.IServiceCategorie;


@ManagedBean(name = "cliMB")
@RequestScoped
public class ClientManagedBean implements Serializable {

	@ManagedProperty(value = "#{cService}")
	private IClientService clientService;
	
	@ManagedProperty(value = "#{categorieService}")
	private IServiceCategorie categorieService;


	@ManagedProperty(value = "#{produitService}")
	private IProduitService produitService;
	
	
	private Client client;
	private Categorie categorie;
	private List<Categorie> listeCategories;
	private List<Produit> listeProduits;
	private List<Produit> listeProduitsByMot;

	private Produit produitDemande;
	private int nombreCommande;
	private double prixTot;
	private String mot;
	private Administrateur admin;
	private HttpSession maSession;
	private LigneCommande ligneCommande;
	private Panier panier;
	private int id;

	
	
	
	public ClientManagedBean() {
		// instancier le client pour éviter l'exception target unreachble
		this.client = new Client();
		this.categorie = new Categorie();
		this.produitDemande = new Produit();
		this.listeCategories = new ArrayList<Categorie>();
		this.listeProduits = new ArrayList<Produit>();
		this.listeProduitsByMot = new ArrayList<Produit>();

		this.ligneCommande = new LigneCommande();
	}

	@PostConstruct
	public void init() {
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		// récup context
		//FacesContext context = FacesContext.getCurrentInstance();
		// récup session
		//this.maSession = (HttpSession) context.getExternalContext().getSession(false);
		System.out.println("La session est" +maSession);
		// recuperation e l'agent à partir de la session
		this.admin = (Administrateur) maSession.getAttribute("adminSession");

		this.listeCategories = clientService.getAllCategories();
		this.listeProduits = clientService.getAllProduits();
		// this.listeProduits = new ArrayList<>();
	}
	
	

	public void setCategorieService(IServiceCategorie categorieService) {
		this.categorieService = categorieService;
	}


	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}


	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}


	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}


	public Categorie getCategorie() {
		return categorie;
	}


	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}


	public List<Categorie> getListeCategories() {
		return listeCategories;
	}


	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}


	public List<Produit> getListeProduits() {
		return listeProduits;
	}


	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}


	public List<Produit> getListeProduitsByMot() {
		return listeProduitsByMot;
	}


	public void setListeProduitsByMot(List<Produit> listeProduitsByMot) {
		this.listeProduitsByMot = listeProduitsByMot;
	}


	public Produit getProduitDemande() {
		return produitDemande;
	}


	public void setProduitDemande(Produit produitDemande) {
		this.produitDemande = produitDemande;
	}


	public int getNombreCommande() {
		return nombreCommande;
	}


	public void setNombreCommande(int nombreCommande) {
		this.nombreCommande = nombreCommande;
	}


	public double getPrixTot() {
		return prixTot;
	}


	public void setPrixTot(double prixTot) {
		this.prixTot = prixTot;
	}


	public String getMot() {
		return mot;
	}


	public void setMot(String mot) {
		this.mot = mot;
	}


	public Administrateur getAdmin() {
		return admin;
	}


	public void setAdmin(Administrateur admin) {
		this.admin = admin;
	}


	public HttpSession getMaSession() {
		return maSession;
	}


	public void setMaSession(HttpSession maSession) {
		this.maSession = maSession;
	}


	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}


	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
	}


	public Panier getPanier() {
		return panier;
	}


	public void setPanier(Panier panier) {
		this.panier = panier;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	// les methodes
	public String instancierPanierDansSession() {
		// On créer un panier
		Panier panierSession = new Panier();
		// On créer une liste de ligne de commande
		System.out.println("Création de la liste");
		List<LigneCommande> listeCommande = new ArrayList<>();
		System.out.println(listeCommande);
		System.out.println("Ajout de la liste dans le panier");
		panierSession.setListeLignesCommande(listeCommande);
		System.out.println("Liste ajoutée");
		// Ajouter le panier à la session
		System.out.println(maSession);
		System.out.println(this.admin);
		//maSession.setAttribute("panierSession", panierSession);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("panierSession", panierSession);
		System.out.println("Panier ajouté");

		// On créer la liste de produit contenant tous les produits.
		List<Produit> listeTousProduits = produitService.listerProduits();
		// On ajoute la liste à la session
		this.listeProduits = listeTousProduits;
		
		maSession.setAttribute("listeProduits", listeProduits);
		System.out.println("La liste des produits est" + listeProduits);

		return "accueilClient";
	}
	
}
