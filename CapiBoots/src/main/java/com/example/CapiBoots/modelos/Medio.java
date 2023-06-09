package com.example.CapiBoots.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="medio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String tipo;

    @Column(columnDefinition = "VARCHAR(150)")
    private String nombre_completo;

    @OneToMany(mappedBy = "medio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pagos> pagos;
}
