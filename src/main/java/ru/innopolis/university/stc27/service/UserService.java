package ru.innopolis.university.stc27.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.innopolis.university.stc27.domain.Role;
import ru.innopolis.university.stc27.domain.User;
import ru.innopolis.university.stc27.helpers.Sum;
import ru.innopolis.university.stc27.repositories.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;


    @Autowired
    private DataSource dataSource;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return null;
    }

    public boolean addUser(String username, String login, String email, String password) {
        User userFromDB =  userRepository.findUserBylogin(login);
        if (userFromDB != null) {
            return false;
        }

        User user = new User(username, login, password, email);

        user.setRoles(Collections.singleton(Role.USER));
        user.setCode(UUID.randomUUID().toString());
        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getMail())) {
            String message = String.format("Hello, %s!\n" + "Welcome to Awesome application! " +
                    "Please, visit next link http://localhost:8080/activate/%s", username, user.getCode());
            mailSender.send(email, "Activation code", message);
        }

        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findUserByCode(code);

        if (user == null) {
            return false;
        }

        user.setCode(null);
        user.setActive(true);
        userRepository.save(user);
        return true;
    }

    public User getUser(String login) {
        return userRepository.findUserBylogin(login);
    }

    public HashMap<String, Double> getDate(String login, String type){

        User user = userRepository.findUserBylogin(login);
        Long id = user.getId();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        String sb = String.format("SELECT category.category_name, SUM(%s.sum) FROM %s LEFT JOIN category ON %s.category_id = category.category_id WHERE %s.person_id=%d AND %s.%s_dt between date_trunc('month', current_date) and date_trunc('month', current_date) + interval '1 month'- interval '1 second' GROUP BY category.category_name", type, type, type, type, id, type, type);

        List<Sum> sums = namedParameterJdbcTemplate.query(sb, new RowMapper<Sum>() {
            @Override
            public Sum mapRow(ResultSet resultSet, int i) throws SQLException {
                Sum sum = new Sum();
                sum.setName(resultSet.getString(1));
                sum.setSum(resultSet.getDouble(2));
                return sum;
            }
        });

        Double count = 0.0;

        HashMap<String, Double> result = new HashMap<>();
        for(Sum summa : sums) {
            result.put(summa.getName(), summa.getSum());
            count += summa.getSum();
        }
        result.put(type + "_sum", count);
        return result;
    }
}
