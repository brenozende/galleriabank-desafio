package br.com.galleriabank.desafio.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "cpf")
        }
)
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @CPF
    private String cpf;
    private String phoneNumber;
}
