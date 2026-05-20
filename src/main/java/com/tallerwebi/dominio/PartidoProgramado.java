package com.tallerwebi.dominio;

public class PartidoProgramado extends Partido {
    private int numeroFecha;
    private String fecha;
    private String hora;
    private String lugar;

    public PartidoProgramado(Equipo equipoLocal, Equipo equipoVisitante, int numeroFecha, String fecha, String hora,
            String lugar) {
        super(equipoLocal, equipoVisitante);
        this.numeroFecha = numeroFecha;
        this.fecha = fecha;
        this.hora = hora;
        this.lugar = lugar;
    }

    public int getNumeroFecha() {
        return numeroFecha;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getLugar() {
        return lugar;
    }

}
