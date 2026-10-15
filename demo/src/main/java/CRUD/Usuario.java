package CRUD;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.time.LocalDate;

@Entity
@Data
@SQLDelete(sql = "UPDATE usuario SET ativo = false WHERE id = ?")
@SQLRestriction("ativo = true")
public class Usuario {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataNascimento;


    @Column(unique = true)
    private String email;

    private String senha;


    private boolean ativo = true;
}
