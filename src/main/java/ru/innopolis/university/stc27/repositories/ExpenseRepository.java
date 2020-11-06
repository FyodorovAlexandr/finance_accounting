package ru.innopolis.university.stc27.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.university.stc27.domain.Expense;
import ru.innopolis.university.stc27.domain.User;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Transactional
    List<Expense> findExpensesByUser (User user);
}
