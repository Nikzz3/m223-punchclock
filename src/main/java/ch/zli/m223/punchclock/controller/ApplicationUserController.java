package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.domain.Role;
import ch.zli.m223.punchclock.repository.ApplicationUserRepository;
import ch.zli.m223.punchclock.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class ApplicationUserController {
    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleService roleService;

    public ApplicationUserController(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationUser> getAllEntries() {
        return applicationUserRepository.findAll();
    }


    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser applicationUser) {
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        Optional<Role> role = roleService.findById(1L);
        if(role.isPresent()){
            applicationUser.setRole(role.get());
        }
        applicationUserRepository.save(applicationUser);
    }
}
