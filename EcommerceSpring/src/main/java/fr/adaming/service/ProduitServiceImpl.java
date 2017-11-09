package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IProduitDao;
import fr.adaming.modele.Produit;

@Service("produitService")
@Transactional
public class ProduitServiceImpl implements IProduitService{

	
	@Autowired
	private IProduitDao daoProduit;
	//Setters pour l'injection	
	public void setDaoProduit(IProduitDao daoProduit) {
		this.daoProduit = daoProduit;
	}


	@Override
	public List<Produit> listerProduits() {
		return daoProduit.listerProduits();
	}

	
}