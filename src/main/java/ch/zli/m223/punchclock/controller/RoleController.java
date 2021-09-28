package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.Role;
import ch.zli.m223.punchclock.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Role> getAllRoles() {
        return roleService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role createRole(@Valid @RequestBody Role role) {
        return roleService.create(role);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteRole(@PathVariable Long id) {
        roleService.delete(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        return roleService.update(role);
    }
}