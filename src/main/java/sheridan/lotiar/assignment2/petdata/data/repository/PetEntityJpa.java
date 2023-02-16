package sheridan.lotiar.assignment2.petdata.data.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "pet")
public class PetEntityJpa {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pet_name")
    private String petname = "";

    @Column(name = "pet_kind")
    private String petkind = "";

    @Column(name = "pet_sex")
    private int petsex = 0;

    @Column(name = "pet_vax_yes")
    private Boolean petvax_yes = false;

    @Column(name = "pet_vax_no")
    private Boolean petvax_no = false;
}
