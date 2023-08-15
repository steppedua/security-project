package com.steppedua.securityproject.controller;

import com.steppedua.securityproject.entity.TeamEntity;
import com.steppedua.securityproject.model.Team;
import com.steppedua.securityproject.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final TeamRepository teamRepository;

    @GetMapping
    @PreAuthorize("hasRole('client_user')")
    public List<TeamEntity> team() throws InterruptedException {
        Thread.sleep(400); // демо ожидание для семинара по VueJS
        return teamRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public TeamEntity teamUserById(@PathVariable Long id) {
       Optional<TeamEntity> op = teamRepository.findById(id);
       return op.orElseThrow(()->new RuntimeException());
    }

    @PostMapping
    @PreAuthorize("hasRole('client_admin')")
    public void create(@RequestBody Team team) {
        TeamEntity teamEntity = TeamEntity.builder()
                .name(team.getName())
                .email(team.getEmail())
                .position(team.getPosition())
                .build();
        teamRepository.save(teamEntity);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public void update(@RequestBody Team team) {
        TeamEntity teamEntity = TeamEntity.builder()
                .name(team.getName())
                .email(team.getEmail())
                .position(team.getPosition())
                .id(team.getId())
                .build();
        teamRepository.save(teamEntity);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public void delete(@PathVariable long id) {
        teamRepository.deleteById(id);
    }
}
