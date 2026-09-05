document.addEventListener('DOMContentLoaded', function() {
    const btnsPlan = document.querySelectorAll('.plan-btn');
    const btnsEvento = document.querySelectorAll('.btn-event');
    const plantillas = document.querySelectorAll('.item-plantilla');

    const lblPlan = document.getElementById('lblPlan');
    const lblCategoria = document.getElementById('lblCategoria');

    const categorias = ['boda', 'xv', 'cumple', 'baby', 'graduacion', 'otros'];
    const nombresCat = ['Bodas', 'XV Años', 'Cumpleaños', 'Baby Shower', 'Graduaciones', 'Bautizos / Otros'];

    let planActual = { nombre: 'Plan Normal', precio: 'Q75' };
    let catIndexActual = 0;

    // 1. Manejo del Cambio de Plan
    btnsPlan.forEach((btn, index) => {
        btn.addEventListener('click', function() {
            btnsPlan.forEach(b => b.classList.remove('active'));
            this.classList.add('active');

            if (index === 0) planActual = { nombre: 'Plan Normal', precio: 'Q75' };
            if (index === 1) planActual = { nombre: 'Plan Premium', precio: 'Q150' };
            if (index === 2) planActual = { nombre: 'Plan VIP', precio: 'Q300' };

            lblPlan.textContent = `${planActual.nombre} (${planActual.precio})`;

            document.querySelectorAll('.precio-tag').forEach(el => el.textContent = planActual.precio);

            document.querySelectorAll('a[href^="/formulario-creacion"]').forEach(enlace => {
                let url = new URL(enlace.href, window.location.origin);
                url.searchParams.set('plan', planActual.nombre.split(' ')[1].toLowerCase());
                enlace.href = url.pathname + url.search;
            });
        });
    });

    // 2. Manejo del Cambio de Categoría
    btnsEvento.forEach((btn, index) => {
        btn.addEventListener('click', function() {
            btnsEvento.forEach(b => b.classList.remove('active'));
            this.classList.add('active');

            catIndexActual = index;
            lblCategoria.textContent = nombresCat[catIndexActual];

            const catSeleccionada = categorias[catIndexActual];

            plantillas.forEach(plantilla => {
                if (plantilla.classList.contains(catSeleccionada)) {
                    plantilla.classList.remove('d-none');
                } else {
                    plantilla.classList.add('d-none');
                }
            });
        });
    });
});