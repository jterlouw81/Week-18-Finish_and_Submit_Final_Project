package art.valuation.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data

public class Artist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long artistId;
	
	private String artistName;
	private String dateOfBirth;
	private String dateOfDeath;
	

	@OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Artwork> artworks = new HashSet<>();
	

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "artist_media_type",
		joinColumns = @JoinColumn(name = "artist_id"),
		inverseJoinColumns = @JoinColumn(name = "media_type_id"))
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<MediaTypeCategory> mediaTypeCategories = new HashSet<>();
	
		
	}

