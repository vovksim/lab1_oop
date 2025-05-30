package dao;

import model.User;
import java.util.Optional;
import java.util.List;

public interface UserDAO {
    Optional<User> findById(int id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    void save(User user);
    void update(User user);
    void delete(int id);
}
