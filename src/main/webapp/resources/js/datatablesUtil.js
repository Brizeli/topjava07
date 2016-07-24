function makeEditable() {
    $('#add').click(function () {
        $('#id').val(0);
        $('#editRow').modal();
    });

    $('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
        }
    });
}

function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        updateTableByData(data);
    });
}

function save() {
    var form = $('#detailsForm');
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
        }
    });
}