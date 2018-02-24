package es.jander.ms.derby;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDomain, Long>
{
}
