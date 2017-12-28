<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        table {
            border: 1px solid grey;
            border-collapse: collapse;
        }

        td {
            border: 1px solid grey;
            width: 20px;
            height: 20px;
            text-align: center;
        }

        #fieldSize {
            width: 30px;
        }

        #invisible {
            color: white;
        }
    </style>
    <title>Крестики-нолики</title>
</head>
<body>
<table>
    <c:if test="${crosses==null}">
        <b>Крестики-нолики</b><br><br>
        <form action="/do/start" method="post">
            Размер поля: <input type="text" name="size" id="fieldSize" pattern="[3-9]{1}|[0-9]{2,}"><br>
            <input type="submit" value="Начать">
        </form>
    </c:if>
    <c:if test="${crosses!=null}">
        <c:set var="counter" value="0"/>
        <tr>
        <c:forEach items="${crosses}" var="item">
            <c:if test="${item==-1}">
                <c:if test="${haveWinner==false}">
                    <td><a id="invisible" href="/do/move?id=${counter}">V</a></td>
                </c:if>
                <c:if test="${haveWinner==true}">
                    <td></td>
                </c:if>
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
<c:if test="${haveWinner==true}">
    ${winner} win!!!<br><a href="/index.jsp">Начать новую игру</a>
</c:if>
</body>
</html>
