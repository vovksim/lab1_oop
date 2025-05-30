package dao;

import model.Tour;
import java.util.List;
import java.util.Optional;

public interface TourDAO {
    Optional<Tour> findById(int id);
    List<Tour> findAll();
    void save(Tour tour);
    void update(Tour tour);
    void delete(int id);
}
