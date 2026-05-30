let numeroOrdenSeleccionado = null;


document.addEventListener("DOMContentLoaded", function () {
  document.getElementById("scrollJugadores").style.display = "none";
  document.getElementById("placeholder-lista").style.display = "flex";

  document.querySelectorAll(".tarjeta-jugador").forEach(card => {
    card.classList.add("oculto");
  });
});


document.getElementById("campoBuscar").addEventListener("input", function (e) {
  const busqueda = e.target.value.toLowerCase().trim();

  document.querySelectorAll(".tarjeta-jugador").forEach(card => {
    const nombre = card.dataset.nombre.toLowerCase();
    const coincideNombre = nombre.includes(busqueda);

    /*  Solo mostramos si coincide el puesto Y el nombre */
    if (coincideNombre) {
      card.classList.remove("oculto");
    } else {
      card.classList.add("oculto");
    }
  });
});

// eslint-disable-next-line no-unused-vars
function activarRanura(idRanura, puesto) {
  const ranura = document.getElementById(idRanura);
  // si la ranura ya tiene jugador (clase ocupada), salir
  if (ranura.classList.contains("ranura-ocupada")) return;

  /*  Limpiar el buscador cada vez que cambias de posición */
  document.getElementById("campoBuscar").value = "";

  //para el numero de orden asociado a cada puesto
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