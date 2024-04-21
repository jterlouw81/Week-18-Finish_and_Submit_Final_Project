package art.valuation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import art.valuation.entity.MediaTypeCategory;

public interface MediaTypeCategoriesDao extends JpaRepository<MediaTypeCategory, Long> {

}
