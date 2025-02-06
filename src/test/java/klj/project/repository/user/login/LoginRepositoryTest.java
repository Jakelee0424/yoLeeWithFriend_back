package klj.project.repository.user.login;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import klj.project.repository.user.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class LoginRepositoryTest {

    @Autowired
    UserRepository userRepository;


    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("네이버 AccessToken 받아오기")
    @Rollback(false)
    void getAccessToken() {


    }



}
