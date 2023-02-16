package sheridan.lotiar.assignment2.petdata.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class PetForm implements Serializable {

    private int id = 0;

    @NotBlank
    @Size(max = 30)
    @Pattern(regexp = "[A-Za-z]*")
    private String petname = "";


    @NotBlank
    @Pattern(regexp = "(Dog|Cat|Rabbit)?")
    private String petkind = "";

    @NotNull
    @Min(1)
    @Max(3)
    private int petsex = 1;

    private boolean petvax_yes = false;

    private boolean petvax_no = false;
}
