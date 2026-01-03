package com.uc_modul4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set; // Phải dùng java.util.Set

@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id // Dùng jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;

    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude // Tránh lỗi lặp vô hạn khi log dữ liệu
    @EqualsAndHashCode.Exclude
    private Set<Task> tasks = new HashSet<>();
}