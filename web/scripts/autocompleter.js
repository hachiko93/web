/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
        $(function() {
                $("#search").autocomplete({     
                source : function(request, response) {
                $.ajax({
                        url : "/web/SearchController",
                        type : "GET",
                        data : {
                                term : request.term
                        },
                        dataType : "json",
                        success : function(data) {
                                response(data);
                        }
                });
        }
});
});
});