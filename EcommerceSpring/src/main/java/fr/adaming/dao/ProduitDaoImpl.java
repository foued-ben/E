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

}
