package com.tallerwebi.dominio.excepcion;

public class EquipoJugadorNoEncontradoException extends Exception {
    public EquipoJugadorNoEncontradoException(String message) {
        super(message);
    }
}



 //public class EquipoNoEncontradoException extends RuntimeException {

    // Tuve que usar RunTimeException porq con exception solo tiraba error
   // private static final long serialVersionUID = 1L;

    //public EquipoNoEncontradoException(String message) {
     //   super(message);
  //  }
//}
