package art.valuation.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import art.valuation.controller.model.ArtistData;
import art.valuation.controller.model.ArtistData.ArtistArtwork;
import art.valuation.controller.model.ArtistData.ArtistMediaType;
import art.valuation.dao.ArtistDao;
import art.valuation.dao.ArtworkDao;
import art.valuation.dao.MediaTypeCategoriesDao;
import art.valuation.entity.Artist;
import art.valuation.entity.Artwork;
import art.valuation.entity.MediaTypeCategory;

@Service
public class ArtValuationService {
	
	@Autowired
	private ArtistDao artistDao;
	@Autowired
	private ArtworkDao artworkDao;
	@Autowired
	private MediaTypeCategoriesDao mediaTypeCategoriesDao;

	@Transactional(readOnly = false)
	public ArtistData saveArtist(ArtistData artistData) {
		Long artistId = artistData.getArtistId();
		Artist artist = findOrCreateArtist(artistId);
		
		copyArtistFields(artist, artistData);
		return new ArtistData(artistDao.save(artist));
	}
	
	private void copyArtistFields(Artist artist, ArtistData artistData) {
		artist.setArtistName(artistData.getArtistName());
		artist.setDateOfBirth(artistData.getDateOfBirth());
		artist.setDateOfDeath(artistData.getDateOfDeath());
	}
	private Artist findOrCreateArtist(Long artistId) {

		if(Objects.isNull(artistId)) {
			return new Artist();
		}
		else {
			return findArtistById(artistId);
		}
	}

	private Artist findArtistById(Long artistId) {

		return artistDao.findById(artistId)
				.orElseThrow(() -> new NoSuchElementException(
						"Artist with ID = " + artistId + " was not found."));
	}

	private void copyArtworkFields(Artwork artwork, ArtistArtwork artistArtwork) {
		artwork.setTitle(artistArtwork.getTitle());
		artwork.setDescription(artistArtwork.getDescription());
		artwork.setCreatedYear(artistArtwork.getCreatedYear());
		artwork.setCurrentValue(artistArtwork.getCurrentValue());
		artwork.setPurchasePrice(artistArtwork.getPurchasePrice());
		artwork.setSalePrice(artistArtwork.getSalePrice());
	}
	private void copyMediaTypeCategoryFields(MediaTypeCategory mediaTypeCategory,
			ArtistMediaType artistMediaType) {
		mediaTypeCategory.setMediaTypeId(artistMediaType.getMediaTypeId());
		mediaTypeCategory.setMediaType(artistMediaType.getMediaType());
	}
	private Artwork findOrCreateArtwork(Long artistId, Long artworkId) {
		
		if(Objects.isNull(artworkId)) {
			return new Artwork();
		}
		return findArtworkById(artistId, artworkId);
}

	private Artwork findArtworkById(Long artistId, Long artworkId) {
		Artwork artwork = artworkDao.findById(artworkId)
				.orElseThrow(() -> new NoSuchElementException(
						"Artwork with ID = " + artworkId + " was not found."));
		
		if(artwork.getArtist().getArtistId() != artistId) {
			throw new IllegalArgumentException("The artwork with ID = " + artworkId
					+ " is not associated with this Artist ID = " + artistId + ".");
		}
		return artwork;
	}
	@Transactional(readOnly = false)
	public ArtistArtwork saveArtwork(Long artistId, ArtistArtwork artistArtwork) {
		Artist artist = findArtistById(artistId);
		Long artworkId = artistArtwork.getArtworkId();
		Artwork artwork = findOrCreateArtwork(artistId, artworkId);
		
		copyArtworkFields(artwork, artistArtwork);
		
		artwork.setArtist(artist);
		artist.getArtworks().add(artwork);
		
		Artwork dbArtwork = artworkDao.save(artwork);
		
		return new ArtistArtwork(dbArtwork);
	}
	@Transactional
	public ArtistMediaType saveMediaType(ArtistMediaType artistMediaType) {

		Long mediaTypeId = artistMediaType.getMediaTypeId();
		MediaTypeCategory mediaTypeCategory = findOrCreateMediaTypeCategory(mediaTypeId);
		
		copyMediaTypeCategoryFields(mediaTypeCategory, artistMediaType);
		return new ArtistMediaType(mediaTypeCategoriesDao.save(mediaTypeCategory));
	}	
	private MediaTypeCategory findOrCreateMediaTypeCategory(Long mediaTypeId) {
			if(Objects.isNull(mediaTypeId)) {
				return new MediaTypeCategory();
			}
			return findMediaTypeById(mediaTypeId);
		}
	private MediaTypeCategory findMediaTypeById(Long mediaTypeId) {
		return mediaTypeCategoriesDao.findById(mediaTypeId)
				.orElseThrow(() -> new NoSuchElementException(
				"Media Type with ID = " + mediaTypeId + " was not found."));
	}
	
	@Transactional(readOnly = true)
	public List<ArtistData> retrieveAllArtists() {
		List<Artist> artists = artistDao.findAll();
		
		List<ArtistData> result = new LinkedList<>();
		
		for(Artist artist : artists) {
			ArtistData ad = new ArtistData(artist);
			
			ad.getArtworks().clear();
			ad.getMediaTypeCategories().clear();
			
			result.add(ad);
		}
		return result;
	}
	@Transactional(readOnly = true)
	public ArtistData retrieveArtistById(Long artistId) {
		return new ArtistData(findArtistById(artistId));
	}
	@Transactional(readOnly = false)
	public void deleteArtistById(Long artistId) {
		Artist artist = findArtistById(artistId);
		artistDao.delete(artist);
	}
	@Transactional(readOnly = false)
	public Artist assignMediaTypeToArtist(Long artistId, Long mediaTypeId) {
		Set<MediaTypeCategory> mediaTypesSet = null;
		Artist artist = artistDao.findById(artistId).get();
		MediaTypeCategory mediaTypeCategory = mediaTypeCategoriesDao.findById(mediaTypeId).get();
		mediaTypesSet = artist.getMediaTypeCategories();
		mediaTypesSet.add(mediaTypeCategory);
		artist.setMediaTypeCategories(mediaTypesSet);
		return artistDao.save(artist);

	}
}
