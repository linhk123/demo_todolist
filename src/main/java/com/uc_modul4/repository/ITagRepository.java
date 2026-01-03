package com.uc_modul4.repository;

import com.uc_modul4.entity.Tag;
import com.uc_modul4.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
interface ITagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}

@Repository
interface ICommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTaskIdOrderByCreatedAtAsc(Long taskId);
}
