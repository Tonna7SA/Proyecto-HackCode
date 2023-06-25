<script>
    function calcularEdad() {
        var fechaNacimiento = new Date(document.getElementById("fecha_nacimiento").value);
        var hoy = new Date();
        var edad = hoy.getFullYear() - fechaNacimiento.getFullYear();
        var mes = hoy.getMonth() - fechaNacimiento.getMonth();
        if (mes < 0 || (mes === 0 && hoy.getDate() < fechaNacimiento.getDate())) {
            edad--;
        }
        document.getElementById("edad").value = edad;
    }
</script>  