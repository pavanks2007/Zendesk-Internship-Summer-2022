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
    $("#results").empty();
    let deferred = $.Deferred();
    let bool = false;
    $.ajax({
        url: "/getAllTickets/" + current_page + "/" + $("#number_of_tickets").val(),
        type: 'GET',
        success: function(data){
            const stringifiedData = JSON.stringify(data, null, 2);
            const parsedData = JSON.parse(stringifiedData.replaceAll("\\\\n", ""));
            if(parsedData.length === 0){
                alert('End of tickets!');
            } else {
                for (let index = 0; index < parsedData.length; ++index) {
                    const element = parsedData[index];
                    const ticketHeading = "id: " + element.id + "    subject: " + element.subject;
                    $("#results").append('<button class="collapsible" id="button_' + index + '">' + ticketHeading + ' <i class="fas fa-angle-down rotate-icon"></i></button>');
                    $("#results").append('<pre class="content"> <p>' + JSON.stringify(element, null, 2) + '</p> </pre>');
                    $("#button_" + index).on("click", function () {
                        this.classList.toggle("active");
                        const content = this.nextElementSibling;
                        if (content.style.maxHeight) {
                            content.style.maxHeight = null;
                        } else {
                            $(".collapsible").each(function(){
                                this.nextElementSibling.style.maxHeight = null;
                            });
                            content.style.maxHeight = content.scrollHeight + "px";
                        }
                        this.classList.toggle("active");
                    });
                }
                bool = true;
            }
        },
        error: function(xhr) {
            alert(xhr.responseText);
        },
        complete: function() {
            deferred.resolve(bool);
        }
    });
    return deferred.promise();
}
