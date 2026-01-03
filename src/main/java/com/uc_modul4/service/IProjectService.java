package com.uc_modul4.service;

import com.uc_modul4.entity.Project;

import java.util.List;

public interface IProjectService {
    Project createProject(Project project, Long ownerId);
    void addMember(Long projectId, Long userId);
    void removeMember(Long projectId, Long userId);
    List<Project> getProjectsByUser(Long userId);
}
