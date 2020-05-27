<?php

include('db_connect.php');

$stmt = $conn->prepare("SELECT title, price, rating, image FROM products");

$stmt ->execute();
$stmt -> bind_result($title, $price, $rating, $image);

$products = array();

while($stmt ->fetch()){

    $temp = array();
	
	$temp['title'] = $title;
	$temp['price'] = $price;
	$temp['rating'] = $rating;
	$temp['image'] = $image;

	array_push($products,$temp);
	}

	echo json_encode($products);

?>