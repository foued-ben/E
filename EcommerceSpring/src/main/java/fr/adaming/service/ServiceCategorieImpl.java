package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.modele.Categorie;
@Service("categorieService")
@Transactional
public class ServiceCategorieImpl implements IServiceCategorie{

	@Autowired
	private ICategorieDao daoCategorie;
	
	public void setDaoCategorie(ICategorieDao daoCategorie) {
		this.daoCategorie = daoCategorie;
	}


	@Override
	public List<Categorie> listerCategorie() {

		return daoCategorie.listerCategorie();
				
	}

	
	
}
