package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class TorneoVirtual extends Torneo {



    @OneToOne
    private TorneoReal torneoReal;


    public TorneoVirtual() {
    }

}
