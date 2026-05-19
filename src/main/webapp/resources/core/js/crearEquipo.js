const nombreIngresadoInput = document.getElementById("nombreEquipo");
const botonSiguiente = document.querySelector(".boton-equipo");

/* Para que el boton SIGUIENTE se active solo al completar el input */
nombreIngresadoInput.addEventListener("input", () => {

    const textoIngresado = nombreIngresadoInput.value.trim();

    if (textoIngresado.length < 4) {
        botonSiguiente.disabled = true;
    } else {
        botonSiguiente.disabled = false;
    }
});