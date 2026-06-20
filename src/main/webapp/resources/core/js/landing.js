document.addEventListener("DOMContentLoaded", () => {
  const numeros = document.querySelectorAll(".scoreboard-numero");

  numeros.forEach((el) => {
    const objetivo = parseInt(el.dataset.target, 10);
    const duracion = 900;
    const inicio = performance.now();

    function actualizar(ahora) {
      const progreso = Math.min((ahora - inicio) / duracion, 1);
      const valor = Math.floor(progreso * objetivo);
      el.textContent = String(valor).padStart(el.dataset.target.length, "0");
      if (progreso < 1) requestAnimationFrame(actualizar);
    }

    requestAnimationFrame(actualizar);
  });
});