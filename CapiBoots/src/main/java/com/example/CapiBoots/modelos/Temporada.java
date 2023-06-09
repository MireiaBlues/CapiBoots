package com.example.CapiBoots.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="temporada")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Temporada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;


    @ManyToOne
    @JoinColumn(name = "idSerie")
    private Series serie;

    @OneToMany(mappedBy = "idtemporada", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Contenidos> contenidos;

}
