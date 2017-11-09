package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.dao.IEtudiantDao;
import fr.adaming.modele.Categorie;
import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;


@Service("cService")
@Transactional
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDao clientDao ; 
	
	
	
	@Override
	public List<Categorie> getAllCategories() {
		return clientDao.getAllCategories();
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
