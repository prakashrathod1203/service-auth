package tech.sarthee.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.sarthee.auth.model.entity.OrganizationEntity;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, String> {
}
