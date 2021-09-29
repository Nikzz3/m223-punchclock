package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.Role;
import ch.zli.m223.punchclock.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Role> getAllRoles(Principal principal) throws Exception {
        return roleService.findAll(principal);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Role> getRole(@PathVariable Long id) {
        return roleService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role createRole(@Valid @RequestBody Role role, Principal principal) throws Exception {
        return roleService.create(role, principal);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteRole(@PathVariable Long id, Principal principal) throws Exception {
        roleService.delete(id, principal);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Role updateRole(@PathVariable Long id, @RequestBody Role role, Principal principal) throws Exception {
        role.setId(id);
        return roleService.update(role, principal);
    }
}