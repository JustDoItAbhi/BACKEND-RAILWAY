package userService.subjects.servie;

import org.springframework.web.multipart.MultipartFile;
import userService.subjects.Subjects;
import userService.subjects.dtos.SubjectRequestDto;
import userService.subjects.dtos.SubjectResponseDto;

import java.io.IOException;
import java.util.List;

public interface SubjectService {
    SubjectResponseDto addSubjectByYear(SubjectRequestDto dto);

    SubjectResponseDto getSubhectByYear(String Year);
    SubjectResponseDto updateSubject(long id,SubjectRequestDto dto);
    List<SubjectResponseDto> transferAllListOfStubjecsFromCsvFile(MultipartFile file) throws IOException;
    List<SubjectResponseDto> getAllSubjects() ;

}
