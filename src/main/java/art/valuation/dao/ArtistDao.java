package art.valuation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import art.valuation.entity.Artist;

public interface ArtistDao extends JpaRepository<Artist, Long> {

}
