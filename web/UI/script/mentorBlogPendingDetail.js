
// $('#summernote').summernote('disable');
$('#summernote').summernote('code', blogContent);
//$('#summernote').hide();

var backupContent = $('#summernote').summernote('code');
var backupThumbnail = $('#txtImageUrl').val();

$('.note-editor').hide();
$("#undoDeleteBtn").hide();

function deleteThumbnail() {
    $('#imgReview').hide();
    $('#txtImageUrl').val("");

    $("#deleteThumnailBtn").hide();
    $('#deleteThumnailBtn').hide();
    $("#undoDeleteBtn").show();

}

function undoDeleteThumbnail() {
    $('#imgReview').show();
    $('#txtImageUrl').val(backupThumbnail);

    $('#deleteThumnailBtn').show();
    $("#undoDeleteBtn").hide();
}

function EnableEditAndSave() {
    $('#summernote').summernote('enable');
    $("#editBtn").hide();
    $("#saveBtn").show();
    $("#undoBtn").show();

    // Show View
    $('#blog-view').css("visibility", "hidden");
    // Show Edtior
    //$('#summernote').show();
    $('.note-editor').show();
    // $('#summernote').summernote('enable');
}

function SaveEdit() {
    $("#editBtn").show();
    $("#saveBtn").hide();
    $("#undoBtn").hide();
    $("#updateBtn").show();

    // Show View
    $('#blog-view').css("visibility", "visible");
    // Hide Editor
    //$('#summernote').hide();
    $('.note-editor').hide();
    // $('#summernote').summernote('disable');

    // Copy Editor Into View
    $('.blog-view').html($('#summernote').summernote('code'));
}

function Undo() {
    if (confirm('Are you sure you want undo everything?')) {
        $('#summernote').summernote('code', backupContent);
        $("#editBtn").show();
        $("#saveBtn").hide();
        $("#undoBtn").hide();
        $("#updateBtn").hide();
        SaveEdit();
    } else {

    }
}
