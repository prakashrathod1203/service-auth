package in.om.repositories;

import in.om.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Prakash Rathod
 */
public interface OrganizationRepository extends JpaRepository<Organization, String> {

}
