package at.kacharino.workouttrackerstronger.mappers;

import at.kacharino.workouttrackerstronger.dtos.RegisterRequestDto;
import at.kacharino.workouttrackerstronger.dtos.UserDto;
import at.kacharino.workouttrackerstronger.entities.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapUserToUserDto() {
        var user = new User();
        user.setId(1L);
        user.setFirstName("Kacha");
        user.setLastName("Rino");
        user.setEmail("test@mail.com");
        user.setBirthdate(LocalDate.of(2000, 1, 1));
        user.setWeight(75.3);
        user.setHeight(180.0);

        UserDto dto = mapper.toDto(user);

        assertEquals(user.getId(), dto.getId());
        assertEquals("Kacha", dto.getFirstName());
        assertEquals("test@mail.com", dto.getEmail());
    }

    @Test
    void shouldMapUserDtoToUser() {
        var dto = new UserDto();
        dto.setId(2L);
        dto.setFirstName("Ana");
        dto.setLastName("Kacha");
        dto.setEmail("ana@mail.com");
        dto.setBirthdate(LocalDate.of(1995, 5, 5));
        dto.setWeight(60.0);
        dto.setHeight(170.0);

        User user = mapper.toEntity(dto);

        assertEquals(dto.getId(), user.getId());
        assertEquals("Ana", user.getFirstName());
        assertEquals("ana@mail.com", user.getEmail());
    }

    @Test
    void shouldMapRegisterRequestDtoToUser() {
        var req = new RegisterRequestDto();
        req.setFirstName("Max");
        req.setLastName("Mustermann");
        req.setEmail("max@test.com");
        req.setPassword("secret123");

        User user = mapper.toEntity(req);

        assertNull(user.getId());
        assertEquals("Max", user.getFirstName());
        assertEquals("max@test.com", user.getEmail());
    }
}