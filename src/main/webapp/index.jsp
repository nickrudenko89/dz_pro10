<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <style>
            table {
                border: 1px solid grey;
                border-collapse: collapse;
            }
            th, td {border: 1px solid grey;}
            #fieldSize {width: 30px;}
            a {color: white;}
        </style>
        <title>$Title$</title>
    </head>
    <body>
    <table>
        <c:if test="${crosses==null}">
            <form action="/do/start" method="post">
                Размер поля: <input type="text" name="size" id="fieldSize"><br>
                <input type="submit" value="Начать">
            </form>
        </c:if>
        <c:if test="${crosses!=null}">
            <c:set var="counter" value="0"/>
            <tr>
            <c:forEach items="${crosses}" var="item">
                <c:if test="${item==-1}">
                    <td><a href="/do/move?id=${counter}">E</a></td>
                </c:if>
                <c:if test="${item==0}">
                    <td>O</td>
                </c:if>
                <c:if test="${item==1}">
                    <td>X</td>
                </c:if>
                <c:if test="${(counter+1)%size==0}">
                    </tr>
                </c:if>
                <c:set var="counter" value="${counter+1}"/>
            </c:forEach>
        </c:if>
    </table>
    ${crosses}
    </body>
</html>
