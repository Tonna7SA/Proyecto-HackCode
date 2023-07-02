// Obtener referencias a los elementos del DOM
const modal = document.getElementById('confirmModal');
const cancelButton = document.getElementById('cancelButton');
const confirmButton = document.getElementById('confirmButton');
const btnEliminar = document.querySelector('.btnEliminar');

// Función para mostrar el modal
function mostrarModal() {
  modal.style.display = 'block';
}

// Función para ocultar el modal
function ocultarModal() {
  modal.style.display = 'none';
}

// Asociar eventos a los botones
btnEliminar.addEventListener('click', mostrarModal);
cancelButton.addEventListener('click', ocultarModal);
confirmButton.addEventListener('click', () => {
  // Aquí puedes agregar la lógica para realizar la eliminación
  // Puedes redirigir a una URL o ejecutar una función, según tus necesidades
  // Por ejemplo:
  // window.location.href = '/empleados/eliminar/' + empleados.legajoDni;
  console.log('Eliminar');
});