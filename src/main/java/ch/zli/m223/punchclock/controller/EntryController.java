package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.service.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/entries")
public class EntryController {
    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Entry> getAllEntries(Principal principal) {
        return entryService.findAll(principal);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entry createEntry(@Valid @RequestBody Entry entry, Principal principal) throws Exception {
        return entryService.createEntry(entry, principal);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Entry updateEntry(@PathVariable Long id, @Valid @RequestBody Entry entry, Principal principal) throws Exception {
        return entryService.updateEntry(id, entry, principal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEntry(@PathVariable Long id, Principal principal) throws Exception {
        entryService.deleteEntry(id, principal);
    }
}
