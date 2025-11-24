package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.AdComment;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface AdCommentRepository extends JpaRepository<AdComment, Integer> {
    List<AdComment> findAllByAdPk(Integer adId);
}
