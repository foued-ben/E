package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Etudiant;
import fr.adaming.modele.Categorie;
import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;

@Repository
public class ClientDaoImpl implements IClientDao{

	@Autowired
	private SessionFactory sf ; 
	
	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}
	
	@Override
	public List<Categorie> getAllCategories() {
		Session s = sf.getCurrentSession() ; 
		// la requete HQL
		String req ="FROM Categorie";
		
		// recup d'un query
		Query query = s.createQuery(req) ; 
		
		
		// envoi de la req et recup du résultat
		@SuppressWarnings("unchecked")
		List<Categorie> liste = query.list();
		
		return liste;
	}

	@Override
	public List<Produit> getAllProduits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produit> getAllProduitByCategorie(Categorie c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produit> getProduitsSelect(List<Produit> lp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produit> getProduitsByMot(String mot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Produit addProduitPanier(Produit p, int quantite, Panier pan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteProduitPanier(Produit p, Panier pan) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Client enregitrementClient(Client c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Commande enregistrementCommabde(Commande commande) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client recuperClient(Client c) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
