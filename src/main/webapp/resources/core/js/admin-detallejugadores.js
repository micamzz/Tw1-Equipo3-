function ordenarTabla(idTabla, idSelect) {
    const criterio = document.getElementById(idSelect).value;
    const tbody = document.getElementById(idTabla).querySelector("tbody");
    const filas = Array.from(tbody.querySelectorAll("tr"));

    filas.sort((a, b) => {
        if (criterio === "nombre") {
            const nombreA = a.cells[1].textContent.trim();
            const nombreB = b.cells[1].textContent.trim();
            return nombreA.localeCompare(nombreB);
        }

        if (criterio === "apellido") {
            const apellidoA = a.cells[2].textContent.trim();
            const apellidoB = b.cells[2].textContent.trim();
            return apellidoA.localeCompare(apellidoB);
        }

        if (criterio === "edad") {
            const edadA = parseInt(a.cells[5].textContent.trim());
            const edadB = parseInt(b.cells[5].textContent.trim());
            return edadA - edadB;
        }

        return 0;
    });

    filas.forEach(fila => tbody.appendChild(fila));
}