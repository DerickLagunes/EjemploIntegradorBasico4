<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="layout/header.jsp" %>

<link href="https://cdn.jsdelivr.net/npm/simple-datatables@9.0.3/dist/style.min.css" rel="stylesheet">

<div class="row g-4">
    <div class="col-12">
        <h1 class="mb-4">Bienvenidos a la veterinaria</h1>
    </div>

    <div class="col-md-7">
        <div class="row mb-3">
            <h4 class="text-secondary col-6">Aquí están todas las mascotas</h4>
            <a href="mascota" class="btn btn-primary col-6 align-content-center text-center carga"><i class="bi bi-arrow-clockwise"></i> Cargar mascotas</a>
        </div>

        <c:choose>
            <c:when test="${empty listaMascotas}">
                <div class="alert alert-info text-center mt-4" role="alert">
                    <i class="bi bi-info-circle-fill"></i> No hay mascotas registradas en este momento.
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table id="tablaMascotas" class="table table-striped table-hover mt-4 align-middle">
                        <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Especie</th>
                            <th>Edad</th>
                            <th>Personalidad</th>
                            <th>Foto</th>
                            <th>Vacunada</th>
                            <th>Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listaMascotas}" var="mascota">
                            <tr>
                                <td><strong>${mascota.id}</strong></td>
                                <td>${mascota.nombre}</td>
                                <td><span class="badge bg-secondary">${mascota.especie}</span></td>
                                <td>${mascota.edad} años</td>
                                <td>${mascota.personalidad}</td>
                                <td>
                                    <img src="${mascota.foto}" alt="${mascota.nombre}" class="img-thumbnail" style="width: 60px; height: 60px; object-fit: cover;">
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${mascota.vacunada}">
                                            <span class="text-success"><i class="bi bi-check-circle-fill"></i> Sí</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-danger"><i class="bi bi-x-circle-fill"></i> No</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="d-flex gap-2">
                                        <button class="btn btn-warning btn-sm" title="Editar"
                                                onclick="prepararEdicion(${mascota.id}, '${mascota.nombre}', '${mascota.especie}', ${mascota.edad}, '${mascota.personalidad}', '${mascota.foto}', ${mascota.vacunada})">
                                            <i class="bi bi-pencil-fill"></i>
                                        </button>
                                        <button class="btn btn-danger btn-sm" title="Eliminar"
                                                onclick="prepararEliminacion(${mascota.id})">
                                            <i class="bi bi-trash-fill"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="col-md-5">
        <div class="card shadow-sm">
            <div class="card-body">
                <h4 class="card-title text-primary mb-4" id="formTitle"><i class="bi bi-plus-circle-fill"></i> ¡Registra a tu mascota!</h4>

                <form action="mascota" method="POST" enctype="multipart/form-data" id="mascotaForm">
                    <input type="hidden" name="action" id="formAction" value="create">
                    <input type="hidden" name="id" id="mascotaId" value="0">
                    <input type="hidden" name="fotoActual" id="fotoActual" value="">

                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre de la Mascota</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ej: Firulais" required>
                    </div>

                    <div class="mb-3">
                        <label for="especie" class="form-label">Especie</label>
                        <select class="form-select" id="especie" name="especie" required>
                            <option value="" selected disabled>Selecciona una opción...</option>
                            <option value="Perro">Perro</option>
                            <option value="Gato">Gato</option>
                            <option value="Ave">Ave</option>
                            <option value="Roedor">Roedor</option>
                            <option value="Otro">Otro</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="edad" class="form-label">Edad (Años)</label>
                        <input type="number" class="form-control" id="edad" name="edad" placeholder="Ej: 3" required min="0" max="30">
                    </div>

                    <div class="mb-3">
                        <label for="personalidad" class="form-label">Personalidad / Descripción</label>
                        <input type="text" class="form-control" id="personalidad" name="personalidad" placeholder="Ej: Juguetón y muy cariñoso" required>
                    </div>

                    <div class="mb-3">
                        <label for="foto" class="form-label">Subir Foto</label>
                        <input type="file" class="form-control" id="foto" name="foto" accept="image/*">
                        <div class="form-text" id="fotoHelp">Selecciona una imagen de tu dispositivo.</div>
                    </div>

                    <div class="mb-4 form-check form-switch">
                        <input class="form-check-input" type="checkbox" role="switch" id="vacunada" name="vacunada" value="true">
                        <label class="form-check-input-label" for="vacunada">¿Se encuentra vacunada?</label>
                    </div>

                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary carga" id="btnSubmit"><i class="bi bi-save"></i> Guardar</button>
                        <button type="button" class="btn btn-secondary d-none" id="btnCancelarEdicion" onclick="cancelarEdicion()"><i class="bi bi-x-circle"></i> Cancelar Edición</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modalEliminar" tabindex="-1" aria-labelledby="modalEliminarLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalEliminarLabel">Confirmar Eliminación</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                ¿Estás seguro de que deseas eliminar permanentemente el registro de esta mascota?
            </div>
            <div class="modal-footer">
                <form action="mascota" method="POST" class="w-100 d-flex justify-content-between">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" id="deleteId" value="0">
                    <button type="submit" class="btn btn-danger"><i class="bi bi-trash"></i> Eliminar</button>
                    <button type="button" class="btn btn-dark text-white" data-bs-dismiss="modal"><i class="bi bi-x-circle"></i> Cancelar</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/simple-datatables@9.0.3" type="text/javascript"></script>
<script src="assets/js/gestion-mascotas.js"></script>

<%@ include file="layout/footer.jsp" %>