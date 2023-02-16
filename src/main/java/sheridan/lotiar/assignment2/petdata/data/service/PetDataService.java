package sheridan.lotiar.assignment2.petdata.data.service;

import sheridan.lotiar.assignment2.petdata.model.PetForm;

import java.util.List;

public interface PetDataService {

    void insertPetForm(PetForm form);

    List<PetForm> getAllPetForms();

    void deleteAllPetForms();

    void deletePetForm(int id);

    PetForm getPetForm(int id);

    void updatePetForm(PetForm form);
}
