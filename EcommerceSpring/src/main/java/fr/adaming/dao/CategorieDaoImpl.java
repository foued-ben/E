package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.modele.Categorie;
@Repository
public class CategorieDaoImpl implements ICategorieDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public List<Categorie> listerCategorie() {
		Session session =sessionFactory.getCurrentSession();
		String req = "FROM Categorie";
		Query query =session.createQuery(req);
		//Execution de la requête
		@SuppressWarnings("unchecked")
		List<Categorie> listeCategorie = query.list();
		return listeCategorie;
	}

}
