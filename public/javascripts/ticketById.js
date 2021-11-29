$(document).ready(function(){
    $('#search-id-button').click(function () {
        $("#ticket_info").text("");
        $.ajax({
            url: "/getTicketInfoById/" + $("#ticket_id").val(),
            type: 'GET',
            success: function(data){
                var str = JSON.stringify(data.ticket, null, 2);
                $("#ticket_info").text(str);
            },
            error: function(xhr) {
                alert(xhr.responseText);
            }
        });

    });
});