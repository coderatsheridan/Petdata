package sheridan.lotiar.assignment2.petdata.controller;

import sheridan.lotiar.assignment2.petdata.model.PetForm;
import sheridan.lotiar.assignment2.petdata.data.service.PetDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class PetDataController {

    private static final String[] kind = {
            "--- Select Pet Kind ---",
            "Dog", "Cat",
            "Rabbit"};

    private final PetDataService petDataService;

    public PetDataController(PetDataService petDataService){
        this.petDataService = petDataService;
    }

    @GetMapping(value={"/", "/index"})
    public String index(){
        log.trace("index() is called");
        return "Index";
    }

    @GetMapping("/add-pet")
    public ModelAndView addPet(){
        log.trace("addPet() is called");
        ModelAndView modelAndView =
                new ModelAndView("AddPet",
                        "form", new PetForm());
        modelAndView.addObject("kind", kind);
        return modelAndView;
    }

    @PostMapping("/insert-pet")
    public String insertPet(
            @Validated @ModelAttribute("form") PetForm form,
            BindingResult bindingResult,
            Model model){
        log.trace("insertPet() is called");
        log.debug("form = " + form);
        // checking for the input validation errors
        if (bindingResult.hasErrors()) {
            log.trace("input validation errors");
            //model.addAttribute("form", form);
            model.addAttribute("kind", kind);
            return "AddPet";
        } else {
            log.trace("the user inputs are correct");
            petDataService.insertPetForm(form);
            log.debug("id = " + form.getId());
            return "redirect:confirm-insert/" + form.getId();
        }
    }

    @GetMapping("/confirm-insert/{id}")
    public String confirmInsert(@PathVariable(name = "id") String strId, Model model){
        log.trace("confirmInsert() is called");
        log.debug("id = " + strId);
        try {
            int id = Integer.parseInt(strId);
            log.trace("looking for the data in the database");
            PetForm form = petDataService.getPetForm(id);
            if (form == null) {
                log.trace("no data for this id=" + id);
                return "DataNotFound";
            } else {
                log.trace("showing the data");
                model.addAttribute("form", form);
                return "ConfirmInsert";
            }
        } catch (NumberFormatException e) {
            log.trace("the id in not an integer");
            return "DataNotFound";
        }
    }

    @GetMapping("/list-pets")
    public ModelAndView listPets() {
        log.trace("listPets() is called");
        List<PetForm> list = petDataService.getAllPetForms();
        log.debug("list size = " + list.size());
        return new ModelAndView("ListPets",
                "pets", list);
    }

    @GetMapping("/delete-all")
    public String deleteAll(){
        log.trace("deleteAll() is called");
        petDataService.deleteAllPetForms();
        return "redirect:list-pets";
    }

    @GetMapping("pet-details/{id}")
    public String petDetails(@PathVariable String id, Model model){
        log.trace("petDetails() is called");
        log.debug("id = " + id);
        try {
            PetForm form = petDataService.getPetForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("pet", form);
                return "PetDetails"; // show the student data in the form to edit
            } else {
                log.trace("no data for this id=" + id);
                return "DataNotFound";
            }
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "DataNotFound";
        }
    }

    @GetMapping("/delete-pet")
    public String deletePet(@RequestParam String id, Model model) {
        log.trace("deletePet() is called");
        try {
            PetForm form = petDataService.getPetForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("pet", form);
                return "DeletePet"; // ask "Do you really want to remove?"
            } else {
                return "redirect:list-pets";
            }
        } catch (NumberFormatException e) {
            return "redirect:list-pets";
        }
    }

    // a user clicks "Remove Record" button in "DeleteStudent" page,
    // the form submits the data to "RemoveStudent"
    @PostMapping("/remove-pet")
    public String removePet(@RequestParam String id) {
        log.trace("removePet() is called");
        log.debug("id = " + id);
        try {
            petDataService.deletePetForm(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
        }
        return "redirect:list-pets";
    }

    // a user clicks "Edit" link (in the table) to "EditStudent"
    @GetMapping("/edit-pet")
    public String editPet(@RequestParam String id, Model model) {
        log.trace("editPet() is called");
        log.debug("id = " + id);
        try {
            PetForm form = petDataService.getPetForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("pet", form);
                model.addAttribute("kind", kind);
                return "EditPet";
            } else {
                log.trace("no data for this id=" + id);
                return "redirect:list-pets";
            }
        } catch (NumberFormatException e) {
            log.trace("the id is missing or not an integer");
            return "redirect:list-pets";
        }
    }

    @PostMapping("/update-pet")
    public String updatePet(
            @Validated @ModelAttribute("pet") PetForm form,
            BindingResult bindingResult,
            Model model) {
        log.trace("updatePet() is called");
        log.debug("pet = " + form);
        // checking for the input validation errors
        if (bindingResult.hasErrors()) {
            log.trace("input validation errors");
            model.addAttribute("pet", form);
            model.addAttribute("kind", kind);
            return "EditPet";
        } else {
            log.trace("the user inputs are correct");
            petDataService.updatePetForm(form);
            log.debug("id = " + form.getId());
            return "redirect:pet-details/" + form.getId();
        }
    }
}
