package dao;

import model.Purchase;
import java.util.List;
import java.util.Optional;

public interface PurchaseDAO {
    Optional<Purchase> findById(int id);
    List<Purchase> findByUserId(int userId);
    void save(Purchase purchase);
    void delete(int id);
}
