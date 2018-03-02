package es.jander.ms.redis.svcs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserRestController
{
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public void generateDefaults ()
    {
        userRepository.save(User.builder().id(1).name("one").build());
        userRepository.save(User.builder().id(2).name("two").build());
    }

    @GetMapping
    public Iterable<User> getUsers ()
    {
        return userRepository.findAll();
    }
}
