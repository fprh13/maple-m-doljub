package maple.doljub.service;

import lombok.RequiredArgsConstructor;
import maple.doljub.domain.Guild;
import maple.doljub.repository.GuildRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuildService {

    private final GuildRepository guildRepository;

    public List<Guild> guildList() {
        return guildRepository.findGuildAll();
    }

    public Guild guildInfo(String name) {
        return guildRepository.findCharactersByName(name);
    }

    public Guild guildSearch(String name) {
        return guildRepository.findByName(name);
    }
}
