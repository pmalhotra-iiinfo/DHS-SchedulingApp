package gov.dhs.uscis.odos2.useradmin.service;


import gov.dhs.uscis.odos2.useradmin.exception.InvalidUserException;
import gov.dhs.uscis.odos2.useradmin.exception.UserAlreadyExistsException;
import gov.dhs.uscis.odos2.useradmin.model.Roles;
import gov.dhs.uscis.odos2.useradmin.model.UserRoles;
import gov.dhs.uscis.odos2.useradmin.model.Users;
import gov.dhs.uscis.odos2.useradmin.repository.RolesRepository;
import gov.dhs.uscis.odos2.useradmin.repository.UserRolesRepository;
import gov.dhs.uscis.odos2.useradmin.repository.UsersRepository;
import gov.dhs.uscis.odos2.useradmin.service.UserAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Collections;

@Service
public class UserAdminServiceImpl implements UserAdminService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UserRolesRepository userRolesRepository;

    @Override
    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public List<Users> findAllUsers() {
        return (List<Users>)usersRepository.findAll();
    }

    @Override
    public Users createNewUser(Users user) throws UserAlreadyExistsException, InvalidUserException {

        if (null == user.getUserName() || null == user.getFirstName() || null == user.getLastName()) {
            throw new InvalidUserException("Invalid input");
        }

        if ( usersRepository.findByUsername(user.getUserName()) != null ) {
            throw new UserAlreadyExistsException("User already exists");
        }

        UUID userID = UUID.randomUUID();
        user.setId(userID);
        user.setCreatedDate(LocalDateTime.now());
        user.setCreatedBy(userID);
        user.setUpdatedBy(userID);
        user.setUpdatedDate(LocalDateTime.now());

        if (null == user.getRoles()) {
            Roles role = rolesRepository.findByRole("ROLE_REQUESTOR");
            List<Roles> roles = Collections.singletonList(role);
            user.setRoles(roles);
        }

        return saveUserandRolesFromUser(user);
    }

    @Override
    public Users modifyExistingUser(UUID id, Users user) throws InvalidUserException {

        Users existingUser = usersRepository.findUsersById(id);

        if ( existingUser == null ) {
            throw new InvalidUserException("User does not exist");
        }
        
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setUpdatedDate(LocalDateTime.now());
        
        return saveUserandRolesFromUser(existingUser);
    }

    @Override
    public void deleteUser(UUID userID) throws InvalidUserException {
        Users userToDelete = findUserByID(userID);
        if ( usersRepository.findByUsername(userToDelete.getUserName()) == null ) {
            throw new InvalidUserException("User does not exist");
        }
        deleteUserandRolesFromUser(userToDelete);
    }

    private Users findUserByID(UUID userID) {
        String username = "";
        for (Users users : this.findAllUsers()) {
            if (userID == users.getId()) {
                username = users.getUserName();
            }
        }
        return this.findByUsername(username);
    }

    private Users saveUserandRolesFromUser(Users user) {

        usersRepository.save(user);

        for (Roles roles : user.getRoles()) {
            UserRoles userRoles = new UserRoles();
            userRoles.setRoleId(roles.getId());
            userRoles.setUserId(user.getId());
            userRolesRepository.save(userRoles); 
        }

        return user;
    }

    private void deleteUserandRolesFromUser(Users user) {

        for (UserRoles userRoles : userRolesRepository.findAll()) {
            if (userRoles.getUserId() ==  user.getId()) {
                userRolesRepository.delete(userRoles);
            }
        }        
        usersRepository.delete(user);
    }
}