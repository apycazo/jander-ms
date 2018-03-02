package es.jander.ms.derby;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDomain
{
    @Id
    @GeneratedValue
    private Long id;
    private String fullName;
    private String userName;
}
