let numeroOrdenSeleccionado = null;
let puestoActivo = null;

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("scrollJugadores").style.display = "none";
    document.getElementById("placeholder-lista").style.display = "flex";

    document.querySelectorAll(".tarjeta-jugador").forEach(card => {
        console.log("card puesto:", card.dataset.puesto);
        card.classList.add("oculto");
    });
});

document.getElementById("campoBuscar").addEventListener("input", function (e) {
    const busqueda = e.target.value.toLowerCase().trim();

    document.querySelectorAll(".tarjeta-jugador").forEach(card => {
        const nombre = card.dataset.nombre.toLowerCase();
        const coincideNombre = nombre.includes(busqueda);
        const coincidePuesto = puestoActivo === "TODOS" || card.dataset.puesto === puestoActivo;

        if (coincideNombre && coincidePuesto) {
            card.classList.remove("oculto");
        } else {
            card.classList.add("oculto");
        }
    });
});

// eslint-disable-next-line no-unused-vars
function activarRanura(idRanura, puesto) {
    console.log("puesto:", puesto);
    const ranura = document.getElementById(idRanura);

    if (ranura.classList.contains("ranura-ocupada")) return;

    puestoActivo = puesto; // ← guardar el puesto activo

    document.getElementById("campoBuscar").value = "";

    numeroOrdenSeleccionado = parseInt(ranura.dataset.orden);

    document.querySelectorAll(".numero-orden").forEach(input => {
        input.value = numeroOrdenSeleccionado;
    });

    document.querySelectorAll(".ranura-absoluta, .ranura-suplente").forEach(s => {
        s.classList.remove("activa-ahora");
    });

    const ranuraClickeada = document.getElementById(idRanura);
    if (ranuraClickeada) {
        ranuraClickeada.classList.add("activa-ahora");
    }

    document.getElementById("placeholder-lista").style.display = "none";
    document.getElementById("scrollJugadores").style.display = "block";

    document.querySelectorAll(".tarjeta-jugador").forEach(card => {
        if (puesto === "TODOS") {
            card.classList.remove("oculto");
            return;
        }

        if (card.dataset.puesto === puesto) {
            card.classList.remove("oculto");
        } else {
            card.classList.add("oculto");
        }
    });
}