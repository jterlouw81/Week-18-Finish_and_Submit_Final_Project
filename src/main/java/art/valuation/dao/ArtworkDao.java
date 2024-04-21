package art.valuation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import art.valuation.entity.Artwork;

public interface ArtworkDao extends JpaRepository<Artwork, Long> {

}
