package maple.doljub.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import maple.doljub.dto.CharacterRegisterReqDto;
import maple.doljub.dto.maple.CharacterMapleResDto;
import maple.doljub.dto.maple.GuildMapleResDto;
import maple.doljub.dto.maple.OcidMapleResDto;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
public class RestTemplateClient {

    private final RestTemplate restTemplate;

    public RestTemplateClient() {
        this.restTemplate = new RestTemplate();
    }

    @Value("${nexon.key}")
    String API_KEY;
    @Value("${nexon.url}")
    String URL;


    public String getOcid(CharacterRegisterReqDto characterRegisterReqDto) {
        // URI 빌드
        URI uri = UriComponentsBuilder.fromUriString("https://open.api.nexon.com/maplestorym/v1/id")
                .queryParam("character_name", characterRegisterReqDto.getName())
                .queryParam("world_name", characterRegisterReqDto.getWorld())
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 보내기
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        OcidMapleResDto ocidDto;
        try {
            ocidDto = objectMapper.readValue(responseEntity.getBody(), OcidMapleResDto.class);
            return ocidDto.getOcid();
        } catch (JsonProcessingException e) {
            // 처리할 수 없는 예외 처리
            e.printStackTrace();
            throw new RuntimeException();
        }


    }

    public CharacterMapleResDto getCharacterInfo(String ocid) {
        // URI 빌드
        URI uri = UriComponentsBuilder.fromUriString("https://open.api.nexon.com/maplestorym/v1/character/basic")
                .queryParam("ocid", ocid)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 보내기
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        // 바디 응답 값을 DTO 객체로 매핑
        ObjectMapper objectMapper = new ObjectMapper();
        CharacterMapleResDto characterInfoDTO;
        try {
            characterInfoDTO = objectMapper.readValue(responseEntity.getBody(), CharacterMapleResDto.class);
            return characterInfoDTO;
        } catch (JsonProcessingException e) {
            // 처리할 수 없는 예외 처리
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public String getGuildInfo(String ocid) {
        // URI 빌드
        URI uri = UriComponentsBuilder.fromUriString(URL + "/v1/character/guild")
                .queryParam("ocid", ocid)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 보내기
        HttpEntity<String> entity = new HttpEntity<>(headers);
//        return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        // 바디 응답 값을 DTO 객체로 매핑
        ObjectMapper objectMapper = new ObjectMapper();
        GuildMapleResDto guildInfoDTO;
        try {
            guildInfoDTO = objectMapper.readValue(responseEntity.getBody(), GuildMapleResDto.class);
            return guildInfoDTO.getGuildName();
        } catch (JsonProcessingException e) {
            // 처리할 수 없는 예외 처리
            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            throw new RuntimeException();
        }

    }
}

