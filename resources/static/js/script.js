document.getElementById('openModalButton').addEventListener('click', openModal);

function openModal() {
    console.log("Abriendo modal...");
    var modal = document.getElementById('myModal');

    if (modal) { // Verificar si el elemento modal existe
        modal.style.display = 'block';
    } else {
        console.error("No se pudo encontrar el elemento modal con ID 'myModal'");
    }
}


document.addEventListener('DOMContentLoaded', function() {
        var inputNombreUsuario = document.getElementById('nombreUsuario');
        var mensajeError = document.createElement('div');
        mensajeError.className = 'alert alert-danger';
        mensajeError.style.display = 'none';
        inputNombreUsuario.parentNode.appendChild(mensajeError);

        inputNombreUsuario.addEventListener('blur', verificarNombreUsuario);
        inputNombreUsuario.addEventListener('input', function() {
            mensajeError.style.display = 'none';
            inputNombreUsuario.classList.remove('is-invalid');
        });

        function verificarNombreUsuario() {
            var nombreUsuario = inputNombreUsuario.value;
            if(nombreUsuario) { // Solo verificar si hay un nombre de usuario ingresado
                fetch('/verificar-nombre-usuario?nombreUsuario=' + encodeURIComponent(nombreUsuario))
                    .then(function(response) {
                        return response.json();
                    })
                    .then(function(data) {
                        if (data.existe) {
                            mensajeError.textContent = 'El nombre de usuario ya está en uso.';
                            mensajeError.style.display = 'block';
                            inputNombreUsuario.classList.add('is-invalid');
                        } else {
                            mensajeError.style.display = 'none';
                            inputNombreUsuario.classList.remove('is-invalid');
                        }
                    })
                    .catch(function(error) {
                        console.error('Error al verificar el nombre de usuario:', error);
                    });
            }
        }
    });