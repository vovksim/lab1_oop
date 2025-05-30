<%--
  Created by IntelliJ IDEA.
  User: vovksim
  Date: 5/29/25
  Time: 10:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../fragments/header.jsp" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Available Tours</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background: #eef2f7;
      margin: 0; padding: 2rem;
    }
    h1 {
      text-align: center;
      margin-bottom: 2rem;
      color: #34495e;
    }
    .tour-list {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      gap: 2rem;
    }
    .tour-card-link {
      text-decoration: none;
      color: inherit;
      transition: transform 0.2s ease-in-out;
      display: block;
      width: 320px;
    }
    .tour-card-link:hover {
      transform: scale(1.05);
    }
    .tour-card {
      background-color: #fff;
      border-radius: 12px;
      box-shadow: 0 6px 14px rgba(0,0,0,0.1);
      overflow: hidden;
      display: flex;
      flex-direction: column;
      height: 400px;
    }
    .tour-image {
      width: 100%;
      height: 200px;
      object-fit: cover;
      border-bottom: 2px solid #2c3e50;
    }
    .tour-info {
      padding: 1rem 1.5rem;
      flex-grow: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      text-align: center;
    }
    .tour-info h3 {
      margin: 0 0 1rem;
      color: #2c3e50;
    }
    .tour-info p {
      color: #7f8c8d;
      font-size: 1rem;
      line-height: 1.3;
    }
  </style>
</head>
<body>
<h1>Available Tours</h1>
<div class="tour-list">
  <c:forEach var="tour" items="${tours}">
    <a href="detail?id=${tour.id}" class="tour-card-link">
      <div class="tour-card">
        <img src="${tour.imageUrl}" alt="${tour.name}" class="tour-image"/>
        <div class="tour-info">
          <h3>${tour.name}</h3>
          <p>${tour.description}</p>
        </div>
      </div>
    </a>
  </c:forEach>
</div>
<%@ include file="../fragments/footer.jsp" %>


