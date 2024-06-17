package maple.doljub.common.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import maple.doljub.common.exception.CustomException;
import maple.doljub.common.exception.ErrorCode;
import maple.doljub.dto.CharacterRegisterReqDto;
import maple.doljub.dto.maple.CharacterMapleResDto;
import maple.doljub.dto.maple.EquipmentItemDto;
import maple.doljub.dto.maple.GuildMapleResDto;
import maple.doljub.dto.maple.OcidMapleResDto;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 캐릭터의 nexonID ( ocid ) 를 조회
 * 2. 캐릭터 기본 정보를 조회
 * 3. 캐릭터 길드 정보를 조회
 * 3. 캐릭터 장비 정보를 조회
 */
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


    /**
     * 캐릭터의 nexonID ( ocid ) 를 조회
     */
    public String getOcid(CharacterRegisterReqDto characterRegisterReqDto) {
        URI uri = UriComponentsBuilder.fromUriString(URL + "/v1/id")
                .queryParam("character_name", characterRegisterReqDto.getName())
                .queryParam("world_name", characterRegisterReqDto.getWorld())
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-nxopen-api-key", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            // HTTP 요청 보내기
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            OcidMapleResDto ocidDto;
            ocidDto = objectMapper.readValue(responseEntity.getBody(), OcidMapleResDto.class);
            return ocidDto.getOcid();
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // HTTP 요청 예외 처리
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 캐릭터 기본 정보를 조회
     */
    public CharacterMapleResDto getCharacterInfo(String ocid) {
        URI uri = UriComponentsBuilder.fromUriString(URL + "/v1/character/basic")
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
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // HTTP 요청 예외 처리
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * 캐릭터 길드 정보 조회
     */
    public String getGuildInfo(String ocid) {
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
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        // 바디 응답 값을 DTO 객체로 매핑
        ObjectMapper objectMapper = new ObjectMapper();
        GuildMapleResDto guildInfoDTO;
        try {
            guildInfoDTO = objectMapper.readValue(responseEntity.getBody(), GuildMapleResDto.class);
            return guildInfoDTO.getGuildName();
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // HTTP 요청 예외 처리
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 캐릭터 장비 정보 조회
     */
    public List<EquipmentItemDto> getEquipmentItem(String ocid) {
        URI uri = UriComponentsBuilder.fromUriString(URL + "/v1/character/item-equipment")
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
        // 바디 응답 값을 리스트<DTO> 객체로 매핑
        ObjectMapper objectMapper = new ObjectMapper();
        List<EquipmentItemDto> equipmentItems = new ArrayList<>();
        try {
            // JSON 루트 item_equipment 키의 값을 추출
            JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());
            JsonNode itemEquipmentNode = rootNode.path("item_equipment");
            equipmentItems = objectMapper.readValue(itemEquipmentNode.toString(), new TypeReference<List<EquipmentItemDto>>() {});
            return equipmentItems;
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // HTTP 요청 예외 처리
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}

