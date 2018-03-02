package es.jander.ms.derby;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class JdbcUserService
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private AtomicLong idGenerator = new AtomicLong(100_000);

    public class UserDomainMapper implements RowMapper<UserDomain>
    {
        @Override
        public UserDomain mapRow(ResultSet resultSet, int i) throws SQLException
        {
            Long id = resultSet.getLong("id");
            String fullName = resultSet.getString("full_name");
            String userName = resultSet.getString("user_name");

            return new UserDomain(id, fullName, userName);
        }
    }

    public UserDomain saveUser()
    {
        UserDomain userDomain = new UserDomain();
        userDomain.setUserName("jmcclane");
        userDomain.setFullName("John McClane");
        return saveUser(userDomain);
    }

    public UserDomain saveUser (UserDomain user)
    {
        long id = idGenerator.getAndIncrement();
        String values = String.format("(%d, '%s', '%s')", id, user.getFullName(), user.getUserName());
        jdbcTemplate.execute("INSERT INTO user_domain (id, full_name, user_name) VALUES " + values);
        user.setId(id);
        return user;
    }

    public UserDomain findUserWithId100k()
    {
        List<UserDomain> users = (jdbcTemplate.query(
                "SELECT * FROM user_domain WHERE id = ? ",
                new Object[] { 100_000 },
                (res, row) -> new UserDomain(res.getLong("id"), res.getString("full_name"), res.getString("user_name"))));

        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            log.info("No user found!");
            return null;
        }
    }

    public UserDomain findUserWithId100kUsingExternalMapper()
    {
        UserDomainMapper mapper = new UserDomainMapper();
        List<UserDomain> users = (jdbcTemplate.query(
                "SELECT * FROM user_domain WHERE id = ? ",
                new Object[] { 100_000 },
                mapper));

        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            log.info("No user found!");
            return null;
        }
    }
}
