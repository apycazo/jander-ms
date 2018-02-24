package es.jander.ms.derby;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class UserDomain
{
    @Id
    @GeneratedValue
    private Long id;
    private String fullName;
    private String userName;
}
