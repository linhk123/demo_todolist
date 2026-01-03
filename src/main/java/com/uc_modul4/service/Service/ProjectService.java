package com.uc_modul4.service.Service;

import com.uc_modul4.entity.Project;
import com.uc_modul4.entity.User;
import com.uc_modul4.repository.IProjectRepository;
import com.uc_modul4.repository.IUserRepository;
import com.uc_modul4.service.IProjectService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProjectService implements IProjectService {
    @Autowired
    private IProjectRepository projectRepository;
    @Autowired private IUserRepository userRepository;

    @Override
    public Project createProject(Project project, Long ownerId) {
        User owner = userRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("Không tìm thấy chủ sở hữu"));
        project.setOwner(owner);
        return projectRepository.save(project);
    }

    @Override
    public void addMember(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        project.getMembers().add(user);
        projectRepository.save(project);
    }

    @Override
    public void removeMember(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        project.getMembers()
                .removeIf(m -> Long.valueOf(m.getId()).equals(userId));
        projectRepository.save(project);
    }

    @Override
    public List<Project> getProjectsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        return projectRepository.findProjectsByUserId(userId);    }
}
