package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.domain.Role;
import ch.zli.m223.punchclock.repository.ApplicationUserRepository;
import ch.zli.m223.punchclock.service.RoleService;
import ch.zli.m223.punchclock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class ApplicationUserController {
    private final ApplicationUserRepository applicationUserRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    @Autowired
    public ApplicationUserController(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService, UserService userService) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
        this.userService = userService;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationUser> findAll(Principal principal) throws Exception {
        if (userService.isAdmin(principal)) {
            return applicationUserRepository.findAll();
        } else throw new Exception("User is not admin!");

    }

    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationUser> findAdmins(Principal principal) throws Exception {
        return userService.findAdmins(principal);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApplicationUser updateUser(@PathVariable Long id, @RequestBody ApplicationUser user, Principal principal) throws Exception {
        user.setId(id);
        return userService.update(user, principal);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id, Principal principal) throws Exception {
        userService.delete(id, principal);
    }


    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser applicationUser) {
        if (applicationUserRepository.existsByUsername(applicationUser.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Username already exists"
            );
        }
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        // Needs to be here so the current code works.
        Optional<Role> adminRole = roleService.findById(1L);
        adminRole.ifPresent(applicationUser::setRole);
        applicationUserRepository.save(applicationUser);
    }
}
