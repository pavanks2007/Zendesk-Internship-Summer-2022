$(document).ready(function(){

    let current_page = 1;
    updateCurrentPageDisplay(current_page);

    $('#search-id-button').click(function () {
        getAllTickets(1).done(function(result) {
            if(result) {
                current_page = 1;
                updateCurrentPageDisplay(current_page);
            }
        });
    });

    $('#previous_page').click(function () {
        getAllTickets(current_page - 1).done(function(result) {
            if(result) {
                current_page -= 1;
                updateCurrentPageDisplay(current_page);
            }
        });
    });

    $('#next_page').click(function () {
        getAllTickets(current_page + 1).done(function(result) {
            if(result) {
                current_page += 1;
                updateCurrentPageDisplay(current_page);
            }
        });
    });
});

function updateCurrentPageDisplay(current_page) {
    $("#current_page").text("Page: " + current_page);
}

function getAllTickets(current_page) {
    let deferred = $.Deferred();
    let bool = false;
    $.ajax({
        url: "/getAllTickets/" + current_page + "/" + $("#number_of_tickets").val(),
        type: 'GET',
        success: function(data){
            const str = JSON.stringify(data, null, 2);
            $("#tickets").text(str);
            bool = true;
        },
        error: function(xhr) {
            console.log(xhr.responseText);
            alert(xhr.responseText);
        },
        complete: function() {
            deferred.resolve(bool);
        }
    });
    return deferred.promise();
}
