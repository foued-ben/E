package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.modele.Produit;

@Repository
public class ProduitDaoImpl implements IProduitDao{

	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public List<Produit> listerProduits() {
		Session session = sessionFactory.getCurrentSession();
		//Requête SQL 
		String req = "FROM Produit";
		Query query =session.createQuery(req);
		//Execution de la requête
		List<Produit> listeProduits = query.list();
		return listeProduits;
	}


	@Override
	public Produit rechercherProduitAvecId(Produit produit) {
		Session session = sessionFactory.getCurrentSession();
		//Requête SQL 
		String req = "FROM Produit prod WHERE prod.idProduit=:pIdProduit";
		Query query = session.createQuery(req);
		//Passage du paramètre
		query.setParameter("pIdProduit", produit.getIdProduit());
		Produit produitCherche = (Produit) query.uniqueResult();
		return produitCherche;
	}


	@Override
	public Produit ajouterProduit(Produit produit) {
		Session session = sessionFactory.getCurrentSession();
		session.save(produit);
		
		return produit;
	}

	
	public void supprimerProduit(Produit produit){
		Session session = sessionFactory.getCurrentSession();
		//Requête SQL 
		String req = "FROM Produit prod WHERE prod.idProduit=:pIdProduit";
		Query query = session.createQuery(req);
		//Passage du paramètre
		query.setParameter("pIdProduit", produit.getIdProduit());
		Produit produitCherche = (Produit) query.uniqueResult();

		session.delete(produitCherche);
	}


	@Override
	public Produit modifierProduit(Produit produit) {
		Session session = sessionFactory.getCurrentSession();
		//Requête SQL 
		String req = "FROM Produit prod WHERE prod.idProduit=:pIdProduit";
		Query query = session.createQuery(req);
		//Passage du paramètre
		query.setParameter("pIdProduit", produit.getIdProduit());
		Produit produitCherche = (Produit) query.uniqueResult();

		
		//Remplacement des attributs: 
		produitCherche.setDescription(produit.getDescription());
		produitCherche.setDesignation(produit.getDesignation());
		produitCherche.setIdProduit(produit.getIdProduit());
		produitCherche.setPrix(produit.getPrix());
		produitCherche.setQuantite(produit.getQuantite());
		produitCherche.setImage(produit.getImage());
		produitCherche.setCategorie(produit.getCategorie());
		
		session.saveOrUpdate(produitCherche);
		
		return produitCherche;
	}
}
