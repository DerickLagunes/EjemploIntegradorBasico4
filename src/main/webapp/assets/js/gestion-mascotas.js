document.addEventListener('DOMContentLoaded', function() {
    const tablaElement = document.getElementById('tablaMascotas');

    if(tablaElement) {
        const dataTable = new simpleDatatables.DataTable(tablaElement, {
            searchable: true,
            fixedHeight: false,
            labels: {
                placeholder: "Buscar mascota...",
                perPage: "registros por página",
                noRows: "No se encontraron mascotas",
                info: "Mostrando del {start} al {end} de {rows} mascotas",
                noResults: "No hay resultados que coincidan con tu búsqueda",
            }
        });
    }
});

// Función para rellenar el formulario en modo "Update"
function prepararEdicion(id, nombre, especie, edad, personalidad, foto, vacunada) {
    document.getElementById('formAction').value = 'update';
    document.getElementById('mascotaId').value = id;

    document.getElementById('nombre').value = nombre;
    document.getElementById('especie').value = especie;
    document.getElementById('edad').value = edad;
    document.getElementById('personalidad').value = personalidad;
    document.getElementById('vacunada').checked = vacunada;

    document.getElementById('fotoActual').value = foto;

    document.getElementById('formTitle').innerHTML = '<i class="bi bi-pencil-fill"></i> Actualizar mascota';
    document.getElementById('btnSubmit').innerHTML = '<i class="bi bi-arrow-repeat"></i> Actualizar';
    document.getElementById('btnSubmit').classList.replace('btn-primary', 'btn-warning');
    document.getElementById('btnCancelarEdicion').classList.remove('d-none');
    document.getElementById('fotoHelp').innerText = "Sube una nueva imagen solo si deseas reemplazar la actual.";
    document.getElementById('foto').required = false;

    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// Función para resetear el formulario a modo "Create"
function cancelarEdicion() {
    document.getElementById('mascotaForm').reset();
    document.getElementById('formAction').value = 'create';
    document.getElementById('mascotaId').value = '0';
    document.getElementById('fotoActual').value = '';

    document.getElementById('formTitle').innerHTML = '<i class="bi bi-plus-circle-fill"></i> ¡Registra a tu mascota!';
    document.getElementById('btnSubmit').innerHTML = '<i class="bi bi-save"></i> Guardar';
    document.getElementById('btnSubmit').classList.replace('btn-warning', 'btn-primary');
    document.getElementById('btnCancelarEdicion').classList.add('d-none');
    document.getElementById('fotoHelp').innerText = "Selecciona una imagen de tu dispositivo.";
}

// Función para abrir el modal
function prepararEliminacion(id) {
    document.getElementById('deleteId').value = id;

    let modalEl = document.getElementById('modalEliminar');
    let modalObj = new bootstrap.Modal(modalEl);
    modalObj.show();
}