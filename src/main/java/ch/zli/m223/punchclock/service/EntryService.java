package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.repository.EntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntryService {
    private EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public Entry createEntry(Entry entry) throws Exception {
        if (validateTimes(entry)) {
            return entryRepository.saveAndFlush(entry);
        }
        return entry;
    }

    public boolean validateTimes(Entry entry) throws Exception {
        if (entry.getCheckOut().isAfter(entry.getCheckIn())) {
            return true;
        }
        throw new Exception("CheckIn can not be after CheckOut");
    }

    public Entry updateEntry(Long id, Entry entry) {
        Optional<Entry> entry1 = entryRepository.findById(id);
        if (entry1.isPresent()) {
            entry1.get().setCheckIn(entry.getCheckIn());
            entry1.get().setCheckOut(entry.getCheckOut());
            entryRepository.save(entry1.get());
            return entry1.get();
        }
        return null;
    }

    public void deleteEntry(Long id) {
        entryRepository.deleteById(id);
    }

    public List<Entry> findAll() {
        return entryRepository.findAll();
    }
}
