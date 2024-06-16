# maple-m-doljub <img src="./src/main/resources/static/images/basic.gif">
`메이플스토리 모바일 정보 서비스` 프로젝트 입니다. <br/><br/>
`2024.04 ~ 2024.06` 동안 `Spring Boot`를 사용해 구현했습니다.<br>
<br>

## 개발 환경 <img src="./src/main/resources/static/images/search.gif">
 
| 사용 기술        |
|--------------|
| Spring Boot 3.2.4 |
| Gradle       |
| Java         |
| Thymeleaf    |
| JPA          |
| Spring Security |

<br>

# 목차 <img src="./src/main/resources/static/images/characterList.gif">

- [주요 기능](#주요-기능-)
- [ERD (2024.6.16 기준)](#erd-2024616-기준-)
- [회원가입 로그인](#회원가입-로그인-)
- [캐릭터, 길드 검색](#캐릭터-길드-검색-)
- [캐릭터 관리](#캐릭터-관리-)
- [캐릭터 정보](#캐릭터-정보-)
- [길드 목록](#길드-목록-)
- [회원 기능](#회원-기능-)
- [프로젝트 구조](#프로젝트-구조-)

<br>

## 주요 기능 <img src="./src/main/resources/static/images/search.gif">

- 로그인 로그아웃
- 캐릭터, 길드 검색
- 캐릭터 관리
- 길드 관리
- 회원 기능
- ~~길드 보스 일정 관리(예정)~~

<br>

## ERD (2024.6.16 기준) <img src="./src/main/resources/static/images/search.gif">
<img src="./images/ERD.png" >

<br>

## 회원가입 로그인 <img src="./src/main/resources/static/images/search.gif">
- 회원가입
<img src="./images/회원가입.png">

- 로그인
<img src="./images/로그인.png">

<br>

### 캐릭터, 길드 검색 <img src="./src/main/resources/static/images/search.gif">
<img src="./images/메인.png">

<br>

## 캐릭터 관리 <img src="./src/main/resources/static/images/search.gif">

- 나의 캐릭터 목록
<img src="./images/캐릭터관리.png">

- 새로운 캐릭터 등록
<img src="./images/캐릭터등록.png">

- 나의 캐릭터 삭제
<img src="./images/캐릭터삭제.png">

<br>

## 캐릭터 정보 <img src="./src/main/resources/static/images/search.gif">

- 메이플스토리 모바일 아이템 기준 [일반(X) < 레어(blue) < 에픽(보라) < 유니크(노랑) < 레전드(초록)]

<img src="./images/캐릭터1.png">
<img src="./images/캐릭터2.png">

<br>

## 길드 목록 <img src="./src/main/resources/static/images/search.gif">
- 길드 목록
<img src="./images/길드.png">

- 길드원 목록
<img src="./images/길드정보.png">

<br>

## 회원 기능 <img src="./src/main/resources/static/images/search.gif">
- 회원 정보
<img src="./images/내정보.png">

- 회원 수정
<img src="./images/내정보수정.png">

- 회원 탈퇴
<img src="./images/회원탈퇴.png">

<br>

## 프로젝트 구조 <img src="./src/main/resources/static/images/basic.gif">
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── maple
│   │   │       └── doljub
│   │   │           ├── DoljubApplication.java
│   │   │           ├── common
│   │   │           │   ├── auditing
│   │   │           │   │   ├── BaseCreateByEntity.java
│   │   │           │   │   ├── BaseTimeEntity.java
│   │   │           │   │   └── MemberAuditorAware.java
│   │   │           │   ├── config
│   │   │           │   │   ├── RestTemplateClient.java
│   │   │           │   │   └── SecurityConfig.java
│   │   │           │   ├── exception
│   │   │           │   │   ├── CustomException.java
│   │   │           │   │   └── ErrorCode.java
│   │   │           │   ├── util
│   │   │           │   │   ├── EquipmentItemFilterUtil.java
│   │   │           │   │   └── JobTranslator.java
│   │   │           │   └── validation
│   │   │           │       ├── ValidationGroups.java
│   │   │           │       └── ValidationSequence.java
│   │   │           ├── controller
│   │   │           │   ├── CharacterController.java
│   │   │           │   ├── GuildController.java
│   │   │           │   ├── MainController.java
│   │   │           │   ├── MemberController.java
│   │   │           │   └── PartyController.java
│   │   │           ├── domain
│   │   │           │   ├── Character.java
│   │   │           │   ├── CharacterParty.java
│   │   │           │   ├── Guild.java
│   │   │           │   ├── Member.java
│   │   │           │   ├── Party.java
│   │   │           │   └── Role.java
│   │   │           ├── dto
│   │   │           │   ├── CharacterDeleteDto.java
│   │   │           │   ├── CharacterInfoResDto.java
│   │   │           │   ├── CharacterRegisterReqDto.java
│   │   │           │   ├── CustomUserDetails.java
│   │   │           │   ├── LoginDto.java
│   │   │           │   ├── MemberDeleteDto.java
│   │   │           │   ├── MemberResDto.java
│   │   │           │   ├── MemberSignUpReqDto.java
│   │   │           │   ├── MemberUpdateReqDto.java
│   │   │           │   └── maple
│   │   │           │       ├── CharacterMapleResDto.java
│   │   │           │       ├── EquipmentItemDto.java
│   │   │           │       ├── GuildMapleResDto.java
│   │   │           │       └── OcidMapleResDto.java
│   │   │           ├── repository
│   │   │           │   ├── CharacterPartyRepository.java
│   │   │           │   ├── CharacterRepository.java
│   │   │           │   ├── GuildRepository.java
│   │   │           │   ├── MemberRepository.java
│   │   │           │   └── PartyRepository.java
│   │   │           └── service
│   │   │               ├── CharacterService.java
│   │   │               ├── CustomUserDetailsService.java
│   │   │               ├── GuildService.java
│   │   │               └── MemberService.java


```