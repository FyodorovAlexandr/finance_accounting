package ru.innopolis.university.stc27.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.university.stc27.domain.Income;
import ru.innopolis.university.stc27.domain.User;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Transactional
    List<Income> findIncomesByUser (User user);
}
