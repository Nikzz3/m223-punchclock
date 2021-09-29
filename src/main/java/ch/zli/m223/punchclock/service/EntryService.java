package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.repository.EntryRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class EntryService {
    private final EntryRepository entryRepository;
    private final UserService userService;

    public EntryService(EntryRepository entryRepository, UserService userService) {
        this.entryRepository = entryRepository;
        this.userService = userService;
    }

    public List<Entry> findAll(Principal principal) {
        if (userService.isAdmin(principal)) {
            return entryRepository.findAll();
        } else {
            ApplicationUser user = userService.getUserByUsername(principal.getName());
            return entryRepository.findAllByUser(user);
        }
    }

    public void validateTimes(Entry entry) throws Exception {
        if (entry.getCheckIn().isAfter(entry.getCheckOut())) {
            throw new Exception("CheckIn can not be after CheckOut");
        }
    }

    public Entry createEntry(Entry entry, Principal principal) throws Exception {
        validateTimes(entry);
        ApplicationUser user = userService.getUserByUsername(principal.getName());
        entry.setUser(user);
        // Check if admin or same user
        if (userService.isAdmin(principal) || user.getId() == entry.getUser().getId()) {
            return entryRepository.saveAndFlush(entry);
        } else throw new Exception("User is not admin!");
    }

    public Entry updateEntry(Long id, Entry entry, Principal principal) throws Exception {
        validateTimes(entry);
        Optional<Entry> entryToUpdate = entryRepository.findById(id);
        ApplicationUser user = userService.getUserByUsername(principal.getName());
        if (entryToUpdate.isPresent()) {
            entry.setUser(entryToUpdate.get().getUser());
            if (entry.getCategory() == null) {
                entry.setCategory(entryToUpdate.get().getCategory());
            }
            if (userService.isAdmin(principal) || entryToUpdate.get().getUser().getUsername().equals(user.getUsername())) {
                return entryRepository.save(entry);
            } else throw new Exception("User is not admin!");
        } else throw new Exception("User is not admin!");
    }

    public void deleteEntry(Long id, Principal principal) throws Exception {
        ApplicationUser user = userService.getUserByUsername(principal.getName());
        Optional<Entry> entry = entryRepository.findById(id);
        if(!entry.isPresent()) throw new Exception("User is not admin!");
        if (userService.isAdmin(principal) || user.getId() == entry.get().getUser().getId()) {
            entryRepository.deleteById(id);
        } else throw new Exception("User is not admin!");
    }
}
