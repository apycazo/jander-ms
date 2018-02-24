package es.jander.ms.derby;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class UserController
{
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void init ()
    {
        UserDomain johnDoe = new UserDomain();
        johnDoe.setFullName("John Doe");
        johnDoe.setUserName("jdoe");

        log.info("Saving john doe");
        userRepository.save(johnDoe);

        UserDomain marySue = new UserDomain();
        marySue.setFullName("Mary Doe");
        marySue.setUserName("msue");

        log.info("Saving mary sue");
        userRepository.save(marySue);

        log.info("Finding users");
        userRepository.findAll().forEach(user -> log.info("Found user ({}): {}", user.getId(), user.getFullName()));

        log.info("Done");
    }

    @GetMapping("/users")
    public Iterable<UserDomain> getUsers ()
    {
        return userRepository.findAll();
    }
}
