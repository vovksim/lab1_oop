package DAO;

import dao.UserDAO;
import model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JdbcUserDAOImplTest {

    private UserDAO userDAO;

    @BeforeAll
    static void setupSchema() throws Exception {
        try (Connection conn = db.DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(255) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL
                )
            """);
        }
    }

    @BeforeEach
    void setup() throws Exception {
        userDAO = new JdbcUserDAOImpl();

        try (Connection conn = db.DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE users");
        }
    }

    @Test
    void testSaveAndFindById() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("secret");

        userDAO.save(user);

        // findById requires the user ID; since save doesn't set ID in your DAO,
        // let's retrieve by username to get the id first
        Optional<User> savedUser = userDAO.findByUsername("testuser");
        assertTrue(savedUser.isPresent());

        int id = savedUser.get().getId();

        Optional<User> foundById = userDAO.findById(id);
        assertTrue(foundById.isPresent());
        assertEquals("testuser", foundById.get().getUsername());
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("user123");
        user.setPassword("pwd");

        userDAO.save(user);

        Optional<User> found = userDAO.findByUsername("user123");
        assertTrue(found.isPresent());
        assertEquals("user123", found.get().getUsername());
    }

    @Test
    void testFindAll() {
        User u1 = new User();
        u1.setUsername("user1");
        u1.setPassword("pwd1");

        User u2 = new User();
        u2.setUsername("user2");
        u2.setPassword("pwd2");

        userDAO.save(u1);
        userDAO.save(u2);

        List<User> users = userDAO.findAll();
        assertEquals(2, users.size());
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setUsername("updateUser");
        user.setPassword("oldPass");
        userDAO.save(user);

        // Retrieve to get ID
        Optional<User> saved = userDAO.findByUsername("updateUser");
        assertTrue(saved.isPresent());

        User toUpdate = saved.get();
        toUpdate.setPassword("newPass");

        userDAO.update(toUpdate);

        Optional<User> updated = userDAO.findById(toUpdate.getId());
        assertTrue(updated.isPresent());
        assertEquals("newPass", updated.get().getPassword());
    }

    @Test
    void testDelete() {
        User user = new User();
        user.setUsername("deleteUser");
        user.setPassword("pwd");
        userDAO.save(user);

        Optional<User> saved = userDAO.findByUsername("deleteUser");
        assertTrue(saved.isPresent());

        int id = saved.get().getId();
        userDAO.delete(id);

        Optional<User> deleted = userDAO.findById(id);
        assertFalse(deleted.isPresent());
    }
}
