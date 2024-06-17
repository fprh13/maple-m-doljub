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

    /**
     * Read : 길드 목록
     */
    public List<Guild> guildList() {
        return guildRepository.findGuildAll();
    }

    /**
     * Read : 길드원 목록
     */
    public Guild guildInfo(String name) {
        return guildRepository.findCharactersByName(name);
    }

    /**
     * Read : 길드 검색
     */
    public Guild guildSearch(String name) {
        return guildRepository.findByName(name);
    }
}
