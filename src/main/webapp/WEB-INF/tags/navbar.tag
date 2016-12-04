<%@ tag description="Navbar tag" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<style>
    #navbar > ul > li.dropdown.open > a:hover {
        background-color: darkgoldenrod;
        color: white;
    }
    #navbar > ul > li.dropdown.open > ul {
        background-color: darkgoldenrod;
        color: white;
        border: 1px solid white;
    }
    ul.dropdown-menu > li > a {
        color: white;
        font-weight: 700;
    }
    .navbar-default .navbar-nav > .open > a {
        background: darkgoldenrod none;
        color: white;
        border: 1px solid white;
    }
    .link {
        color: #966c1e;
    }
    .bold {
        font-weight: 700;
    }
    .menu {
        background-color: #edae00;
        border: double white;
    }
</style>
<nav class="navbar navbar-default">
    <div class="container-fluid menu">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed link" data-toggle="collapse" data-target="#navbar" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <%--<a class="navbar-brand" href="#">Brand</a>--%>
        </div>
        <div class="collapse navbar-collapse" id="navbar">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle link bold" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Users<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="${spring:mvcUrl('MC#getIndex').build()}">List</a></li>
                        <li><a href="${spring:mvcUrl('UC#newUser').build()}">New</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle link bold" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Roles<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${spring:mvcUrl('MC#getRoles').build()}">List</a></li>
                            <li><a href="${spring:mvcUrl('RC#newRole').build()}">New</a></li>
                        </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>