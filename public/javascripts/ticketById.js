$(document).ready(function(){
    $('#search-id-button').click(function () {
        $("#ticket_info").text("");
        $.ajax({
            url: "/getTicketInfoById/" + $("#ticket_id").val(),
            type: 'GET',
            success: function(data){
                const str = JSON.stringify(data, null, 2);
                $("#ticket_info").text(str.replaceAll("\\\\n", ""));
            },
            error: function(xhr) {
                alert(xhr.responseText);
            }
        });

    });
});