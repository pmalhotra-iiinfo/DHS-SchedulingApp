package gov.dhs.uscis.odos2.useradmin.repository;

import gov.dhs.uscis.odos2.useradmin.model.UserRoles;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface UserRolesRepository extends CrudRepository<UserRoles, UUID> {
    UserRoles save(UserRoles userrole);

    void delete(UserRoles userrole);
}