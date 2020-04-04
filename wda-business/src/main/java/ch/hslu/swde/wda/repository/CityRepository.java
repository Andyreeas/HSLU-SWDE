package ch.hslu.swde.wda.repository;

import ch.hslu.swde.wda.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findCityById(Long id);
}
