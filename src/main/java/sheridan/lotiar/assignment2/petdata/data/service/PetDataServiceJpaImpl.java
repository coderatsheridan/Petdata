package sheridan.lotiar.assignment2.petdata.data.service;

import sheridan.lotiar.assignment2.petdata.data.repository.PetDataRepositoryJpa;
import sheridan.lotiar.assignment2.petdata.data.repository.PetEntityJpa;
import sheridan.lotiar.assignment2.petdata.model.PetForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PetDataServiceJpaImpl implements PetDataService {

    private final PetDataRepositoryJpa petDataRepositoryJpa;

    PetDataServiceJpaImpl(PetDataRepositoryJpa petDataRepositoryJpa){
        log.trace("constructor() is called");
        this.petDataRepositoryJpa = petDataRepositoryJpa;
    }

    private static void copyFormToEntity(PetForm form, PetEntityJpa pet){
        log.trace("copyFormToEntity() is called");
        pet.setPetname(form.getPetname());
        pet.setPetkind(form.getPetkind());
        pet.setPetsex(form.getPetsex());
        pet.setPetvax_yes(form.isPetvax_yes());
        pet.setPetvax_no(form.isPetvax_no());
    }

    private static void copyEntityToForm(PetEntityJpa pet, PetForm form){
        log.trace("copyEntityToForm() is called");
        form.setId(pet.getId());
        form.setPetname(pet.getPetname());
        form.setPetkind(pet.getPetkind());
        form.setPetsex(pet.getPetsex());
        form.setPetvax_yes(pet.getPetvax_yes());
        form.setPetvax_no(pet.getPetvax_no());
    }

    public void insertPetForm(PetForm form) {
        log.trace("insertPetForm() is called");
        log.debug("insert Pet form " + form);
        PetEntityJpa pet = new PetEntityJpa();
        copyFormToEntity(form, pet);
        pet = petDataRepositoryJpa.save(pet);
        form.setId(pet.getId());
    }

    public List<PetForm> getAllPetForms() {
        log.trace("getAllPetForms() is called");
        List<PetForm> formList = new ArrayList<>();
        List<PetEntityJpa> petList = petDataRepositoryJpa.findAll();
        for(PetEntityJpa pet: petList){
            PetForm form = new PetForm();
            copyEntityToForm(pet, form);
            formList.add(form);
        }
        log.trace("retrieved {} form objects", formList.size());
        return formList;
    }

    public void deleteAllPetForms() {
        log.trace("deleteAllPetForms() is called");
        log.debug("deleting all pet forms");
        petDataRepositoryJpa.deleteAll();
    }

    public void deletePetForm(int id) {
        log.trace("deletePetForm() is called");
        log.debug("deleting Pet form for id=" + id);
        petDataRepositoryJpa.deleteById(id);
    }

    public PetForm getPetForm(int id) {
        log.trace("getPetForm() is called");
        log.debug("getting pet form for id=" + id);
        Optional<PetEntityJpa> result = petDataRepositoryJpa.findById(id);
        if(result.isPresent()){
            PetForm form = new PetForm();
            PetEntityJpa pet = result.get();
            copyEntityToForm(pet, form);
            log.debug("the form for id={} is retrieved", id);
            return form;
        }
        log.debug("the form for id={} is not found", id);
        return null;
    }

    public void updatePetForm(PetForm form) {
        log.trace("updatePetForm() is called");
        log.debug("form=" + form);
        Optional<PetEntityJpa> result = petDataRepositoryJpa.findById(form.getId());
        if(result.isPresent()){
            PetEntityJpa pet = result.get();
            copyFormToEntity(form, pet);
            petDataRepositoryJpa.save(pet);
        }
    }
}

