package userService.teachers.teachersDtos;

import lombok.Getter;
import lombok.Setter;
import userService.registrations.entities.Users;

@Getter
@Setter
public class TeacherResponseDto {
    private long teacherId;
    private String teacherName;
    private String teacherYear;
    private String subject;
    private String teacherEmail;
    private Users users;

}
