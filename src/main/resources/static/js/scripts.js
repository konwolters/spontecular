document.body.addEventListener('htmx:afterSwap', function (event) {
        document.getElementById('saveButton').addEventListener('click', function () {
        var modalInput = document.getElementById('modalInput');
        var jsonInput = document.getElementById('jsonInput');
        jsonInput.value = modalInput.value;
        $('#classesModal').modal('hide');
    });
});