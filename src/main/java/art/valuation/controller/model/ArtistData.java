package art.valuation.controller.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import art.valuation.entity.Artist;
import art.valuation.entity.Artwork;
import art.valuation.entity.MediaTypeCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtistData {
	private Long artistId;
	private String artistName;
	private String dateOfBirth;
	private String dateOfDeath;
	//many to many relationship - media type
	private Set<ArtistMediaType> mediaTypeCategories = new HashSet<>();
	//One to many relationship - artwork
	private Set<ArtistArtwork> artworks = new HashSet<>();
	
	public ArtistData(Artist artist) {
		artistId = artist.getArtistId();
		artistName = artist.getArtistName();
		dateOfBirth = artist.getDateOfBirth();
		dateOfDeath = artist.getDateOfDeath();
	
		//Many to many relationship
		for(MediaTypeCategory mediaTypeCategory : artist.getMediaTypeCategories()) {
			mediaTypeCategories.add(new ArtistMediaType(mediaTypeCategory));
		}
		//one to many relationship
		for(Artwork artwork : artist.getArtworks()) {
			artworks.add(new ArtistArtwork(artwork));

		}
	}

	@Data
	@NoArgsConstructor
	public static class ArtistArtwork {
		private Long artworkId;
		private String title;
		private String description;
		private String createdYear;
		private BigDecimal purchasePrice;
		private BigDecimal currentValue;
		private BigDecimal salePrice;
		
		public ArtistArtwork(Artwork artwork){
			artworkId = artwork.getArtworkId();
			title = artwork.getTitle();
			description = artwork.getDescription();
			createdYear = artwork.getCreatedYear();
			purchasePrice = artwork.getPurchasePrice();
			currentValue = artwork.getCurrentValue();
			salePrice = artwork.getSalePrice();	

			}	
		}
	@Data
	@NoArgsConstructor
	public static class ArtistMediaType {
		private Long mediaTypeId;
		private String mediaType;
		
		public ArtistMediaType(MediaTypeCategory mediaTypeCategory) {
			mediaTypeId = mediaTypeCategory.getMediaTypeId();
			mediaType = mediaTypeCategory.getMediaType();
		}
	}
}

