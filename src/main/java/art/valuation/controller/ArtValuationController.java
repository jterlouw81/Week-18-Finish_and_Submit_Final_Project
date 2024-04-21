package art.valuation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import art.valuation.controller.model.ArtistData;
import art.valuation.controller.model.ArtistData.ArtistArtwork;
import art.valuation.controller.model.ArtistData.ArtistMediaType;
import art.valuation.entity.Artist;
import art.valuation.service.ArtValuationService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/art_valuation")
@Slf4j

public class ArtValuationController {

	@Autowired
	private ArtValuationService artValuationService;
	
	
	//This post mapping will add a new artist
	@PostMapping("/artist")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ArtistData insertArtist(@RequestBody ArtistData artistData) {
		log.info("Creating artist {}", artistData);
		return artValuationService.saveArtist(artistData);
	}
	//This post mapping will add a new media type category
	@PostMapping("/mediaTypeCategory")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ArtistMediaType insertMediaTypeCategory(@RequestBody ArtistMediaType artistMediaType) {
		log.info("Creating media type {}", artistMediaType);
		return artValuationService.saveMediaType(artistMediaType);
	}
	//This post mapping adds a new artwork piece assigned to an artist
	@PostMapping("/artist/{artistId}/artwork")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ArtistArtwork addArtworkToArtist(@PathVariable Long artistId,
			@RequestBody ArtistArtwork artistArtwork){
		log.info("Adding artwork {} to artist with ID = {}", artistArtwork, artistId);
		
		return artValuationService.saveArtwork(artistId, artistArtwork);
	}
	//This put mapping links an artist and media type category - many to many relationship
	@PutMapping("/artist/{artistId}/mediaTypeCategory/{mediaTypeId}")
	public Artist assignMediaTypeToArtist(@PathVariable Long artistId, @PathVariable Long mediaTypeId) {
		log.info("Adding media type {} to artist with ID = {}", mediaTypeId, artistId);
		
		return artValuationService.assignMediaTypeToArtist(artistId, mediaTypeId);
	}
	//This put mapping updates an artist by artistId
	@PutMapping("/artist/{artistId}")
	public ArtistData updateArtist(@PathVariable Long artistId, 
			@RequestBody ArtistData artistData) {
		artistData.setArtistId(artistId);
		log.info("Updating artist {}", artistData);
		return artValuationService.saveArtist(artistData);
	}
	//This get mapping retrieves all artists
	@GetMapping("/artist")
	public List<ArtistData> retrieveAllArtists() {
		log.info("Retrieving all artists.");
		return artValuationService.retrieveAllArtists();
	}
	//This get mapping retrieves a single artist by artist Id, plus all children
	@GetMapping("/artist/{artistId}")
	public ArtistData retrieveArtistById(@PathVariable Long artistId) {
		log.info("Retrieving artist with ID = {}", artistId);
		return artValuationService.retrieveArtistById(artistId);
	}
	//This delete mapping will delete an artist by artist Id
	@DeleteMapping("/artist/{artistId}")
	public Map<String, String> deleteArtistById(@PathVariable Long artistId){
		log.info("Deleting artist with ID = {}", artistId);
		
		artValuationService.deleteArtistById(artistId);
		
		return Map.of("message", "Artist with ID = " + artistId + " deleted successfully");
	}
	//This delete mapping will throw an error if all artists are attempted to be deleted.
	@DeleteMapping("/artist")
	public void deleteAllArtists() {
		log.info("Attempting to delete all artists");
		throw new UnsupportedOperationException("Deleting all artists is not allowed.");
	}
}
