package art.valuation.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class MediaTypeCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mediaTypeId;
	private String mediaType;
	
/*	one to many relationship - future development 
 *  @EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "mediaTypeCategory", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Artwork> artwork = new HashSet<>();
*/	

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	@ManyToMany(mappedBy = "mediaTypeCategories", cascade = CascadeType.PERSIST)
	private Set<Artist> artists = new HashSet<>();

	}

