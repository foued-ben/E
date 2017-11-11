package fr.adaming.managedBean;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import fr.adaming.modele.Produit;
import fr.adaming.service.IProduitService;

@ManagedBean(name="ImageMB")
@ApplicationScoped
public class ImagesManagedBean {

	@ManagedProperty(value="#{produitService}")
	private IProduitService serviceProduit;

	public IProduitService getServiceProduit() {
		return serviceProduit;
	}

	public void setServiceProduit(IProduitService serviceProduit) {
		this.serviceProduit = serviceProduit;
	}

	public StreamedContent getImage() throws IOException{
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE){
			return new DefaultStreamedContent();

		}else{
			// On récupère l'id du produit 
			String id = context.getExternalContext().getRequestParameterMap().get("idProduit");
			System.out.println("Id avant parsage" + id);

			//On récupère le produit associé à l'ID.
			Produit idProduit = new Produit();
			idProduit.setIdProduit(Integer.parseInt(id));
			System.out.println("Id après parsage" + id);
			Produit produit = serviceProduit.rechercherProduitAvecId(idProduit);
			System.out.println(produit);
			//On récupère l'image en byte
			byte[] imageByte = produit.getImageFichier();
			//On renvoie l'image tranformée
			return new DefaultStreamedContent(new ByteArrayInputStream(imageByte));
		}
	}
}
