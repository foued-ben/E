package fr.adaming.dao;

import java.util.List;

import org.primefaces.model.UploadedFile;

import fr.adaming.modele.Produit;

public interface IProduitDao {

	public List<Produit> listerProduits();
	
	public Produit rechercherProduitAvecId(Produit produit);
	
	public Produit ajouterProduit(Produit produit);
	
	public void supprimerProduit(Produit produit);
	
	public Produit modifierProduit(Produit produit);
	
	public int assoicierImageProduit(Produit produit);
	
}
