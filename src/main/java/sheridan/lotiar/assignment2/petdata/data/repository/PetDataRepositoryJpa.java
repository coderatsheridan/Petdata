package sheridan.lotiar.assignment2.petdata.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetDataRepositoryJpa extends JpaRepository<PetEntityJpa, Integer> {
}
