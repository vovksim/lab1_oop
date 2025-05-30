<%--
  Created by IntelliJ IDEA.
  User: vovksim
  Date: 5/29/25
  Time: 10:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Tour" %>
<jsp:include page="../fragments/header.jsp" />

<style>
  .detail-container {
    max-width: 900px;
    margin: 40px auto;
    display: flex;
    gap: 40px;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  }
  .image-gallery {
    flex: 1;
  }
  .main-image {
    width: 100%;
    height: 450px;
    object-fit: cover;
    border-radius: 12px;
    box-shadow: 0 0 15px rgba(0,0,0,0.15);
  }
  .thumb-images {
    margin-top: 15px;
    display: flex;
    gap: 10px;
  }
  .thumb-images img {
    width: 80px;
    height: 60px;
    object-fit: cover;
    border-radius: 8px;
    cursor: pointer;
    border: 2px solid transparent;
    transition: border-color 0.3s ease;
  }
  .thumb-images img:hover, .thumb-images img.active {
    border-color: #007bff;
  }
  .info-section {
    flex: 1;
    display: flex;
    flex-direction: column;
  }
  .info-section h1 {
    margin-bottom: 15px;
    font-size: 2.5rem;
    color: #222;
  }
  .info-section .price {
    font-size: 1.6rem;
    color: #28a745;
    font-weight: bold;
    margin-bottom: 15px;
  }
  .info-section .description {
    font-size: 1.1rem;
    color: #555;
    line-height: 1.5;
    margin-bottom: 25px;
  }
  .details-list {
    list-style: none;
    padding-left: 0;
    font-size: 1.1rem;
    color: #333;
    margin-bottom: 30px;
  }
  .details-list li {
    margin-bottom: 8px;
  }
  .btn-back {
    align-self: flex-start;
    background-color: #007bff;
    color: white;
    text-decoration: none;
    padding: 10px 22px;
    border-radius: 6px;
    font-weight: 600;
    transition: background-color 0.3s ease;
  }
  .btn-back:hover {
    background-color: #0056b3;
  }
  /* Comments Section */
  .comments-section {
    margin-top: 50px;
  }
  .comments-section h3 {
    font-size: 1.8rem;
    margin-bottom: 15px;
  }
  .comment {
    background-color: #f8f9fa;
    border-radius: 8px;
    padding: 15px 20px;
    margin-bottom: 15px;
    box-shadow: 0 0 8px rgba(0,0,0,0.05);
  }
  .comment .author {
    font-weight: 700;
    color: #007bff;
    margin-bottom: 6px;
  }
  .comment .text {
    font-size: 1rem;
    color: #333;
  }
</style>

<div class="detail-container">

  <div class="image-gallery">
    <img id="mainImage" class="main-image" src="${tour.imageUrl}" alt="${tour.name}" />
    <div class="thumb-images">
      <img src="${tour.imageUrl}" alt="${tour.name}" class="active" onclick="switchImage(this)" />
      <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjYSMQJToE4z85CJF5Zuey5tWS6FfapjEKMA&s" alt="Sample Image 2" onclick="switchImage(this)" />
      <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcScUmYCyxhpq_tV6kcly0SBYIoDNn-XMh5cqA&s" alt="Sample Image 3" onclick="switchImage(this)" />
      <img src="https://photographylife.com/wp-content/uploads/2023/05/Nikon-Z8-Official-Samples-00002.jpg" alt="Sample Image 4" onclick="switchImage(this)" />
    </div>
  </div>

  <div class="info-section">
    <h1>${tour.name}</h1>
    <div class="price">$${tour.price}</div>
    <p class="description">${tour.description}</p>

    <ul class="details-list">
      <li><strong>Location:</strong> ${tour.location}</li>
      <li><strong>Duration:</strong> ${tour.duration} days</li>
    </ul>

    <a href="home" class="btn-back">Back to Tours</a>

    <div class="comments-section">
      <h3>Customer Reviews</h3>
      <div class="comment">
        <div class="author">Alice</div>
        <div class="text">Amazing tour, had a fantastic time!</div>
      </div>
      <div class="comment">
        <div class="author">Bob</div>
        <div class="text">Great value for money and very organized.</div>
      </div>
      <div class="comment">
        <div class="author">Charlie</div>
        <div class="text">Loved the scenery and the guides were very helpful.</div>
      </div>
    </div>
  </div>

</div>

<script>
  function switchImage(img) {
    // Remove active class from all thumbs
    document.querySelectorAll('.thumb-images img').forEach(el => el.classList.remove('active'));
    // Set clicked thumb active
    img.classList.add('active');
    // Change main image src to clicked thumb src
    document.getElementById('mainImage').src = img.src;
  }
</script>

<jsp:include page="../fragments/footer.jsp" />


<jsp:include page="../fragments/footer.jsp" />

