package ru.innopolis.university.stc27.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.innopolis.university.stc27.domain.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserId(Long userId);

    List<Category> findByUserIdAndType (Long userId, Boolean type);

    Category findByUserIdAndName(Long userId, String name);
}
