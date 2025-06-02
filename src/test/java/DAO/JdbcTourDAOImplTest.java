package DAO;

import dao.TourDAO;
import dao.impl.JdbcTourDAOImpl;
import model.Tour;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTourDAOImplTest {

    private TourDAO tourDAO;

    @BeforeAll
    static void setupSchema() throws Exception {
        try (Connection conn = db.DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS tours (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    description TEXT,
                    image_url VARCHAR(255),
                    location VARCHAR(255),
                    duration INT,
                    price DOUBLE,
                    slots INT
                )
            """);
        }
    }

    @BeforeEach
    void setup() throws Exception {
        tourDAO = new JdbcTourDAOImpl();

        try (Connection conn = db.DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE tours");
        }
    }

    @Test
    void testSaveAndFindById() {
        Tour tour = new Tour();
        tour.setName("Test Tour");
        tour.setDescription("Amazing test tour");
        tour.setImageUrl("http://example.com/image.jpg");
        tour.setLocation("Test City");
        tour.setDuration(5);
        tour.setPrice(299.99);
        tour.setSlots(10);

        tourDAO.save(tour);
        assertTrue(tour.getId() > 0, "Saved tour should have generated ID");

        Optional<Tour> fetched = tourDAO.findById(tour.getId());
        assertTrue(fetched.isPresent());
        assertEquals("Test Tour", fetched.get().getName());
    }

    @Test
    void testFindAll() {
        // Insert two tours
        Tour tour1 = new Tour();
        tour1.setName("Tour 1");
        tour1.setDescription("Desc 1");
        tour1.setImageUrl("");
        tour1.setLocation("Loc 1");
        tour1.setDuration(3);
        tour1.setPrice(150.0);
        tour1.setSlots(5);

        Tour tour2 = new Tour();
        tour2.setName("Tour 2");
        tour2.setDescription("Desc 2");
        tour2.setImageUrl("");
        tour2.setLocation("Loc 2");
        tour2.setDuration(7);
        tour2.setPrice(350.0);
        tour2.setSlots(15);

        tourDAO.save(tour1);
        tourDAO.save(tour2);

        List<Tour> tours = tourDAO.findAll();
        assertEquals(2, tours.size());
    }

    @Test
    void testUpdate() {
        Tour tour = new Tour();
        tour.setName("Old Name");
        tour.setDescription("Old desc");
        tour.setImageUrl("");
        tour.setLocation("Old loc");
        tour.setDuration(1);
        tour.setPrice(100.0);
        tour.setSlots(2);

        tourDAO.save(tour);

        // Update
        tour.setName("New Name");
        tour.setDescription("New desc");
        tour.setLocation("New loc");
        tour.setDuration(10);
        tour.setPrice(999.99);
        tour.setSlots(20);

        tourDAO.update(tour);

        Optional<Tour> updated = tourDAO.findById(tour.getId());
        assertTrue(updated.isPresent());
        assertEquals("New Name", updated.get().getName());
        assertEquals(10, updated.get().getDuration());
        assertEquals(999.99, updated.get().getPrice());
        assertEquals(20, updated.get().getSlots());
    }

    @Test
    void testDelete() {
        Tour tour = new Tour();
        tour.setName("To Delete");
        tour.setDescription("");
        tour.setImageUrl("");
        tour.setLocation("");
        tour.setDuration(1);
        tour.setPrice(10.0);
        tour.setSlots(1);

        tourDAO.save(tour);

        int id = tour.getId();
        tourDAO.delete(id);

        Optional<Tour> deleted = tourDAO.findById(id);
        assertFalse(deleted.isPresent());
    }
}
