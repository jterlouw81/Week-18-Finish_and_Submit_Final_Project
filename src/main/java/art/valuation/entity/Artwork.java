package art.valuation.entity;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data

public class Artwork {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long artworkId;
	
	private String title;
	@Column(length = 2048)
	private String description;
	private String createdYear;
	private BigDecimal purchasePrice;
	private BigDecimal currentValue;
	private BigDecimal salePrice;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "artist_id", nullable = false)
	private Artist artist;
	
/*	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "media_type_id", nullable = false)
	private MediaTypeCategory mediaTypeCategories;
*/
}
