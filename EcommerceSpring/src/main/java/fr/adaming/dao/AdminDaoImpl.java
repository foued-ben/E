package fr.adaming.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.modele.Administrateur;
@Repository
public class AdminDaoImpl implements IAdminDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	//Setters de l'injection
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



	@Override
	public Administrateur connexionAdmin(Administrateur administrateur) {
		//Instanciation de la session
		Session session = sessionFactory.getCurrentSession();
		//Requ�te HQL
		String req ="FROM Administrateur admin WHERE admin.identifiant=:pIdentifiant AND admin.mdp=:pMdP";
		Query query = session.createQuery(req);
		//Passage des param�tres.
		query.setParameter("pIdentifiant", administrateur.getIdentifiant());
		query.setParameter("p:MdP", administrateur.getMdp());
		//Ex�cution de la requ�te
		Administrateur administrateurRecup = (Administrateur) query.uniqueResult();
		
		return administrateurRecup;
	}

}
