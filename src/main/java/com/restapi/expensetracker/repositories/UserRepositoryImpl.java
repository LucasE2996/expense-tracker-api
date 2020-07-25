package com.restapi.expensetracker.repositories;

import com.restapi.expensetracker.domain.User;
import com.restapi.expensetracker.exceptions.EtAuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class UserRepositoryImpl  implements UserRepository {

    private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(NEXTVAL('ET_USERS_SEQ'), ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " + "FROM ET_USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD FROM ET_USERS WHERE EMAIL = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws EtAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, hashedPassword);
                return preparedStatement;
            }, keyHolder);

            return (Integer) Optional.ofNullable(keyHolder.getKeys())
                    .map(map -> map.get("USER_ID"))
                    .orElseThrow(Exception::new);

        } catch (Exception e) {
            throw new EtAuthException("Invalid user information. Failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EtAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            if (!BCrypt.checkpw(password, user.getPassword()))
                throw new EtAuthException("Invalid email/password");
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new EtAuthException("Invalid email/password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
    }

    /**
     * Here we are implementing a lambda expression to match the RowMapper Interface for User model,
     * so it is basically a inline interface implementation with lambda expression.
     */
    private final RowMapper<User> userRowMapper = ((rs, rowNum) -> new User(
            rs.getInt("USER_ID"),
            rs.getString("FIRST_NAME"),
            rs.getString("LAST_NAME"),
            rs.getString("EMAIL"),
            rs.getString("PASSWORD")
    ));
}
