package dao;

import model.AuthToken;
import java.util.Optional;

public interface AuthTokenDAO {
    Optional<AuthToken> findByToken(String token);
    void save(AuthToken token);
    void deleteByToken(String token);
    void deleteExpiredTokens(); // optional cleanup
}
