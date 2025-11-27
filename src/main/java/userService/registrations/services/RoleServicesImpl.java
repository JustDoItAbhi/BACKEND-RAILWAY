package userService.registrations.services;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import userService.registrations.dtos.RolesRequestDto;
import userService.registrations.dtos.reponseDtos.RoleResponseDto;
import userService.registrations.entities.Roles;
import userService.registrations.repos.RolesRepository;

import java.util.Optional;

@Service
public class RoleServicesImpl implements RoleService{
    @Autowired
    private  RolesRepository rolesRepository;
    private  EntityManagerFactory entityManagerFactory;

    public RoleServicesImpl(RolesRepository rolesRepository,
                           EntityManagerFactory entityManagerFactory) {
        this.rolesRepository = rolesRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public RoleResponseDto createRoles(RolesRequestDto dto) {
        Optional<Roles>exsitingRoles=rolesRepository.findByRoleName(dto.getRoles());
        if(exsitingRoles.isPresent()){
            throw new RuntimeException("role already exists"+dto.getRoles());
        }
        Roles roles=new Roles();
        roles.setRoleName(dto.getRoles());
        rolesRepository.save(roles);
        return roleMapper(roles);
    }
    private RoleResponseDto roleMapper(Roles roles){
        RoleResponseDto dto=new RoleResponseDto();
        dto.setRoles(roles.getRoleName());
        return dto;
    }
}
