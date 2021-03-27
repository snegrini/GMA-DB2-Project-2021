<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Error <%= request.getAttribute("javax.servlet.error.status_code") %></title>
    <link href="css/bulma.min.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">
</head>
<body class="has-background-light">
<section class="container py-5 vh100">
    <div class="column">
        <div class="has-text-centered mb-6">
            <h1 class="title">Error <%= request.getAttribute("javax.servlet.error.status_code") %></h1>
        </div>

        <div class="is-size-5 has-text-centered my-5"><%= request.getAttribute("javax.servlet.error.message") %></div>
    </div>
</section>
</body>
</html>