$(document).ready(function(){

    let current_page = 1;
    updateCurrentPageDisplay(current_page);

    $('#search-id-button').click(function () {
        if(getAllTickets(1)) current_page = 1;
        updateCurrentPageDisplay(current_page);
    });

    $('#previous_page').click(function () {
        current_page -= 1;
        if(getAllTickets(current_page)) current_page += 1;
        updateCurrentPageDisplay(current_page);
    });

    $('#next_page').click(function () {
        current_page += 1;
        if(getAllTickets(current_page)) current_page -= 1;
        updateCurrentPageDisplay(current_page);
    });
});

function updateCurrentPageDisplay(current_page) {
    $("#current_page").text("Page: " + current_page);
}

function getAllTickets(current_page) {
    $.ajax({
        url: "/getAllTickets/" + current_page + "/" + $("#number_of_tickets").val(),
        type: 'GET',
        success: function(data){
            const str = JSON.stringify(data, null, 2);
            $("#tickets").text(str);
            return true;
        },
        error: function(xhr) {
            console.log(xhr.responseText);
            alert(xhr.responseText);
            return false;
        }
    });
}
