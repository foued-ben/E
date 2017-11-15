package fr.adaming.managedBean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.text.pdf.PdfWriter;

import fr.adaming.modele.Administrateur;
import fr.adaming.modele.Categorie;
import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
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
	
	public String afficherCategories() {

		List<Categorie> listeOut = clientService.getAllCategories();

		if (listeOut != null) {
			this.listeCategories = listeOut;

			maSession.setAttribute("categoriesListe", listeCategories);

			return "categorie";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Une erreur s'est produite"));
			return "accueilclient";
		}
	}

	public String afficherProduits() {

		List<Produit> listeOut = clientService.getAllProduits();

		if (listeOut != null) {
			this.listeProduits = listeOut;

			maSession.setAttribute("produitsListe", listeProduits);

			return "produit";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Une erreur s'est produite"));
			return "accueilclient";
		}
	}
	
	public String rechProdByName() {
		List<Produit> listeChercher = clientService.getProduitsByMot(this.mot);
		System.out.println(listeChercher);

		maSession.setAttribute("listeProduitsByMot", listeChercher);
		return "rechProdByMot";
	}
	
	public String afficherProduitsByCat() {

		List<Produit> listeOut = clientService.getAllProduitByCategorie(this.categorie);

		System.out.println(listeOut);
		if (listeOut != null) {
			// maSession.setAttribute("categoriesListe", listeCategories);
			this.listeProduits = listeOut;
			maSession.setAttribute("listeProduits", listeProduits);
			System.out.println("La liste des produits est" + listeProduits);

			return "produitclient";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Une erreur s'est produite"));
			return "accueilclient";
		}
	}
	
	
	public String ajouterPanier() {
		// On récupère le panier de la session
		Panier panier = (Panier) maSession.getAttribute("panierSession");
		// On récupère la liste des commandes déjà effectuée
		List<LigneCommande> listeCommande = panier.getListeLignesCommande();
		// On récupère le produit demandé.
		Produit produitCherche = produitService.rechercherProduitAvecId(this.produitDemande);

		// On initialise la ligne de commande qui devra être supprimer.
		LigneCommande ligneCommandeSupp = null;

		// On vérifie si le produit a été commandé avant
		for (LigneCommande commande : listeCommande) {
			Produit produitTemp = commande.getProduit();
			int idProduitTemp = produitTemp.getIdProduit();
			System.out.println(idProduitTemp);
			if (idProduitTemp == produitDemande.getIdProduit()) {
				// On ajoute la quantité déjà commandé à la quantité demandé.
				int nombreCommandeActualise = this.nombreCommande + commande.getQuantite();
				this.nombreCommande = this.nombreCommande + commande.getQuantite();
				System.out.println("Nombre total commandé " + nombreCommandeActualise);
				// On supprime la ligne de commande de la liste
				ligneCommandeSupp = commande;
			}
		}
		// On vérifie que la commande est possible avec le nouveau nombre
		// demandé.
		if (nombreCommande <= produitCherche.getQuantite()) {
			listeCommande.remove(ligneCommandeSupp);
		} 

		// On vérifie qu'une quantité suffisante de produit est en stock
		if (nombreCommande <= produitCherche.getQuantite() && nombreCommande > 0) {
			System.out.println("Stock suffisant");
			// On ajoute le produit à la ligne de commande
			this.ligneCommande.setProduit(produitCherche);
			// On ajoute le nombre d'objet commande à la ligne de commande
			this.ligneCommande.setQuantite(nombreCommande);
			// On calcule le prix.
			double prixLigne = nombreCommande * produitCherche.getPrix();
			// On ajoute le prix dans la ligne de commande.
			ligneCommande.setPrix(prixLigne);

			// On ajoute la ligne de commande à la liste des commandes.
			listeCommande.add(ligneCommande);
			System.out.println(listeCommande);

			// On ajoute la liste dans le panier.
			panier.setListeLignesCommande(listeCommande);
			// On ajoute le panier à la session
			maSession.setAttribute("panierSession", panier);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Le produit a bien été ajouté au panier"));

		} else if (nombreCommande <= 0) {
			System.out.println("Veuiller commander un nombre positif d'objets ");
		} else {
			System.out.println("Stock insuffisant");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Cher client nos stocks sont insuffisants"));

		}

		return "produitclient";
	}
	
	public String supprProdPanier() {
		// On récupère le panier de la session
		Panier panier = (Panier) maSession.getAttribute("panierSession");
		System.out.println(panier);
		// On récupère la liste des commandes déjà effectuée
		List<LigneCommande> listeCommande = panier.getListeLignesCommande();

		// On supprime la ligne de commande à la liste des commandes.
		listeCommande.remove(this.ligneCommande);
		System.out.println(listeCommande);

		// On ajoute la liste dans le panier.
		panier.setListeLignesCommande(listeCommande);
		// On ajoute le panier à la session
		maSession.setAttribute("panierSession", panier);
		return "panierclient";
	}
	
	public String enregistrement() {
		// On génère un code personnel que le client pourra utilisé pour les
		// commandes ultérieures
		String caracteres = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder code = new StringBuilder();

		int max = caracteres.length() - 1;

		for (int i = 1; i < 10; i++) {
			// System.out.println(i);
			int emplacementCarac = (int) Math.floor(max * Math.random());
			char caracTemp = caracteres.charAt(emplacementCarac);
			// System.out.println("Nombre"+i+"Caractere"+caracteres.charAt(emplacementCarac));
			code.append(caracTemp);
		}
		// On ajoute le code au client
		System.out.println(code);
		this.client.setCodePerso(code.toString());

		System.out.println(this.client.getCodePerso());
		Client cli_enr = clientService.enregitrementClient(this.client);

		this.client = cli_enr;
		// On récupère la liste des commandes
		Panier panier = (Panier) maSession.getAttribute("panierSession");
		List<LigneCommande> listeCommande = panier.getListeLignesCommande();

		double prix = 0;

		for (LigneCommande commande : listeCommande) {
			System.out.println(commande.getPrix());
			prix = prix + commande.getPrix();
		}

		this.prixTot = prix;

		maSession.setAttribute("panierSession", panier);
		System.out.println(panier);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le client a bien été enregistré"));

		// Ajout de la commmande à la base de données. La commande est composée
		// de la liste des commandes (identique à celle du panier)
		// d'une date et d'un client.
		Date dateCommande = new Date();
		Commande commandeTemp = new Commande(dateCommande);

		// On ajoute le client et la liste des commandes à commandeTemp.
		commandeTemp.setListeLigneCommande(listeCommande);
		commandeTemp.setClient(cli_enr);
		// On enregistre la commande dans la base
		clientService.enregistrementCommande(commandeTemp);
		System.out.println(commandeTemp);

		// Gestion du stock.
		for (LigneCommande ligne : listeCommande) {
			// on récupère le produit commandé
			Produit produitTemp = ligne.getProduit();
			System.out.println(produitTemp);
			// On change la quantité de chaque produit
			int nouveauStock = produitTemp.getQuantite() - ligne.getQuantite();
			produitTemp.setQuantite(nouveauStock);
			// Le produit avec le nouveau stocke est utilisé pour modifié la
			// valeur dans la table.
			produitService.modifierProduit(produitTemp);
			System.out.println(produitTemp);
		}
		return "enregistrementclient";
	}
	
	public String nouvelleCommande() {

		// On récupère le client portant l'id donné
		Client clientTemp = clientService.recuperClient(this.client);
		this.client = clientTemp;
		// On récupère la liste des commandes
		Panier panier = (Panier) maSession.getAttribute("panierSession");
		List<LigneCommande> listeCommande = panier.getListeLignesCommande();

		// Calcul du prix
		double prix = 0;

		for (LigneCommande commande : listeCommande) {
			System.out.println(commande.getPrix());
			prix = prix + commande.getPrix();
		}

		this.prixTot = prix;

		// On créer la commande avec une date
		Date dateCommande = new Date();
		Commande commandeTemp = new Commande(dateCommande);

		// On lui ajoute le contenu du panier et le client
		// On ajoute le client et la liste des commandes à commandeTemp.
		commandeTemp.setListeLigneCommande(listeCommande);
		commandeTemp.setClient(this.client);
		// On enregistre la commande dans la base
		clientService.enregistrementCommande(commandeTemp);
		//On ajoute le client à la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Client", this.client);
		

		// Gestion du stock.
		for (LigneCommande ligne : listeCommande) {
			// on récupère le produit commandé
			Produit produitTemp = ligne.getProduit();
			System.out.println(produitTemp);
			// On change la quantité de chaque produit
			int nouveauStock = produitTemp.getQuantite() - ligne.getQuantite();
			produitTemp.setQuantite(nouveauStock);
			// Le produit avec le nouveau stocke est utilisé pour modifié la
			// valeur dans la table.
			produitService.modifierProduit(produitTemp);
			System.out.println(produitTemp);
		}
		return "enregistrementclient";
	}
	
	public void validerPass(FacesContext context, UIComponent composant, Object value)
			throws ValidatorException {		
		String saisie= (String) value ; 
		boolean valide = false ; 
		for (int i = 0; i < saisie.length(); i++) {
			if(saisie.charAt(i) == '@'){
				valide = true ; 
			}
		}
		
		if(!valide){
			throw new ValidatorException(new FacesMessage("Le mail doit contenir un @"));

		}
	
	}
	
	public void validerTel(FacesContext context, UIComponent composant, Object value)
			throws ValidatorException {		
		String saisie= (String) value ; 
		boolean valide = false ; 
		
		
		if( saisie.length()== 10){
			valide = true ; 
		}
		
		
		for (int i = 0; i < saisie.length(); i++) {
			if(saisie.charAt(i) != '0' && saisie.charAt(i) != '1' && saisie.charAt(i) != '2' && saisie.charAt(i) != '3' && saisie.charAt(i) != '4' && saisie.charAt(i) != '5' && saisie.charAt(i) != '6' && saisie.charAt(i) != '7' && saisie.charAt(i) != '8' && saisie.charAt(i) != '9'){
				valide = false ; 
			}
		}
		
		
		if(!valide){
			throw new ValidatorException(new FacesMessage("Le numéro de téléphone doit comporter 10 chiffres"));

		}
	
	}
	
	
	public void ecrirePDF(){
		//On récupère les informations sur les produits depuis la session.
		// On récupère le client portant l'id donné
		Client clientTemp = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Client");
		this.client = clientTemp;
		System.out.println(clientTemp);
		// On récupère la liste des commandes
		Panier panier = (Panier) maSession.getAttribute("panierSession");
		List<LigneCommande> listeCommande = panier.getListeLignesCommande();
		System.out.println(listeCommande);
		// Calcul du prix
		double prix = 0;

		for (LigneCommande commande : listeCommande) {
			System.out.println(commande.getPrix());
			prix = prix + commande.getPrix();
		}
		
		// déclaration d'un document de type Document
		Document document = new Document();
		try {
			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\inti0241\\Documents\\Commandes\\Test.pdf"));
			document.open();

			PdfPTable table = new PdfPTable(5);
			for(int i=0; i<5;i++){
				
			}
			
			Paragraph head = new Paragraph();
			head.add("Récapitulatif de votre commande : ");
			document.add(head);
			head.clear();
			head.add("M./Mme." + clientTemp.getNomClient());
			document.add(head);
			head.clear();
			head.add("Adresse : " + clientTemp.getAdresse());
			document.add(head);
			head.clear();
			head.add("EMail : " + clientTemp.getEmail());
			document.add(head);
			head.clear();
			head.add("Téléphone : " + clientTemp.getTel());
			document.add(head);
			head.clear();

			
			Paragraph header = new Paragraph();
			header.add("Les produits sélectionnés sont : ");
			document.add(header);
			header.clear();
			header.add("Nom Produit" +" | " + "Quantité"+"Prix"+" | "+" | "+"Catégorie");
			//document.add(table);
			for (LigneCommande com :listeCommande){
				Double prixprod = com.getProduit().getPrix();
				String prixCarac = prixprod.toString();
				Paragraph paragraphe = new Paragraph();
				paragraphe.add(com.getProduit().getDesignation() + " | ");
				paragraphe.add(com.getProduit().getQuantite()+ " | ");
				paragraphe.add(prixCarac+" €"+" | ");
				paragraphe.add(com.getProduit().getCategorie().getNomCategorie()+ " | ");

				document.add(paragraphe);
			}
			
			head.add("Prix Total : " + prix + " €");
			document.add(head);
			head.clear();
			document.close();
		} catch (FileNotFoundException | DocumentException e) {
			System.out.println("Erreur lors de la création du PDF");
			e.printStackTrace();
		}
	}
	
}
