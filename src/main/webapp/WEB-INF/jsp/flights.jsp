<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <link rel="stylesheet" href="<c:url value="/resources/css/status.css"/>" type="text/css"/>
    <script type="text/javascript" src="/resources/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript"  src="/resources/js/jquery.autocomplete.min.js"></script>
</head>

<body>
<h1>Find your flight</h1>

<div>
    <form:form method="POST" action="/flights/fare-search" modelAttribute="flightConnection">
        Origin
        <input type="text"  id="origin-search" value=""/>
        Destination
        <input type="text"  id="destination-search" value=""/>

        <form:hidden  id="origin-code" path="origin"/>
        <form:hidden id="destination-code" path="destination"/>

        <input type="submit" value="Submit"/>
    </form:form>
</div>

<div>
    <table>
        <tr>
            <th>Fare</th>
            <th>Origin</th>
            <th>Destination</th>
        </tr>

        <tr>
            <td>${flightDetails.fare.amount} ${flightDetails.fare.currency}</td>
            <td><c:out value="${flightDetails.origin.code} ${flightDetails.origin.name}" /></td>
            <td><c:out value="${flightDetails.destination.code} ${flightDetails.destination.name}" /></td>
        </tr>
    </table>
</div>

<script>
    $(document).ready(function() {

        $('#origin-search').autocomplete({
            serviceUrl: 'flight-search/findBy',
            paramName: "keyword",
            delimiter: ",",
            transformResult: function(response) {

                return {
                    suggestions: $.map($.parseJSON(response), function(item) {
                        return { value: item.description, data: item.code };
                    })
                };
            },
            onSelect: function (suggestion) {
                document.getElementById("origin-code").value = suggestion.data;
            }

        });

        $('#destination-search').autocomplete({
            serviceUrl: 'flight-search/findBy',
            paramName: "keyword",
            delimiter: ",",
            transformResult: function(response) {

                return {
                    suggestions: $.map($.parseJSON(response), function(item) {
                        return { value: item.description, data: item.code };
                    })
                };
            },
            onSelect: function (suggestion) {
                document.getElementById("destination-code").value = suggestion.data;
            }
        });
    });
</script>

</body>
</html>