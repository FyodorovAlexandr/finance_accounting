package ru.innopolis.university.stc27.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.innopolis.university.stc27.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserBylogin (String login);

    User findUserByCode (String code);
}
