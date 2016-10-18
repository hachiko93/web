<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : login
    Created on : Aug 2, 2016, 12:18:11 PM
    Author     : Hachiko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Log In</title>
        <jsp:include page="include.jsp"/>
    </head>
    <body>
        <div class="container">
            <div class="row" id="login-container" style="background-color: white;">

                <form class="form-signin" action="/web/action/login" method="POST" >
                    <h2 class="form-signin-heading">Log In</h2>
                   <label for="inputEmail" class="sr-only">Email address</label>
                    <input name="email" type="email" id="inputEmail" class="form-control" placeholder="Email address" required="" autofocus="">
                    <br>
                    <label for="inputPassword" class="sr-only">Password</label>
                    <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required=""> 
                    <div>
                        <table width="100%" border="0">
                            <tbody>
                                <tr>
                                    <td class="text-left"><button type="button" class="text-left btn btn-default" data-toggle="modal" data-target="#modalForgotPass"> Forgot your password?</button></td>
                                    <td class="text-right"><button type="button" class="text-right btn btn-default" data-toggle="modal" data-target="#modalSignUp"> Sign up </button></td>
                                </tr>
                            </tbody>
                        </table>
                        <br>
                    </div>
                    <button id="asd" class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                    <br>
                    <div id="messages">
                        <c:if test="${error != null}">
                            <div id="error" class="alert alert-danger" role="alert">${error}</div>
                        </c:if>
                        <c:if test="${success != null}">
                            <div id="success" class="alert alert-info" role="alert">${success}</div>
                        </c:if> 
                    </div>
                </form>
            </div>

            <!-- Modal Sign up form -->
            <div class="modal fade" id="modalSignUp" role="dialog">
                <div class="modal-dialog">

                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Sign Up</h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-signin" action="/web/action/register" method="POST">
                                <label for="inputEmailR" class="sr-only">Email address</label>
                                <input name="emailR" type="email" id="inputEmailR" class="form-control" placeholder="Email address" required="" autofocus="">
                                <br>
                                <label for="inputPasswordR" class="sr-only">Password</label>
                                <input name="passwordR" type="password" id="inputPasswordR" class="form-control" placeholder="Password" required="">
                                <br>
                                <label for="inputNickname" class="sr-only">Nickname</label>
                                <input name="nickname" type="text" id="inputNickname" class="form-control" placeholder="Nickname" required="" autofocus="">
                                <br>
                                <label for="inputName" class="sr-only">Name and Surname</label>
                                <input name="name" type="text" id="inputName" class="form-control" placeholder="Name and Surname" required="" autofocus="">
                                <br>
                                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
                                <br>
                                <input type="checkbox" required=""> I agree with <a href="#">Terms and conditions</a>.
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Modal Forgot your password form -->
            <div class="modal fade" id="modalForgotPass" role="dialog">
                <div class="modal-dialog">

                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Forgot Your Password</h4>
                            <br>
                            <p> Enter your email address and the nickname. If you entered the correct information, we will send the link to reset Your password to Your email address. </p>
                        </div>
                        <div class="modal-body">
                            <form class="form-signin" action="/web/action/forgot_password" method="POST">
                                <label for="inputEmailR" class="sr-only">Email address</label>
                                <input name="emailF" type="email" id="inputEmailR" class="form-control" placeholder="Email address" required="" autofocus="">
                                <br>
                                <label for="inputNickname" class="sr-only">Nickname</label>
                                <input name="nicknameF" type="text" id="inputNickname" class="form-control" placeholder="Nickname" required="" autofocus="">
                                <br>
                                <label for="inputNameF" class="sr-only">Name and Surname</label>
                                <input name="nameF" type="text" id="inputName" class="form-control" placeholder="Name and Surname" required="" autofocus="">
                                <br>
                                <button class="btn btn-lg btn-primary btn-block" type="submit">Forgot password</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div> 
                    <script>
                      // document.getElementById("asd").click();
                    </script>
    </body>
</html>
