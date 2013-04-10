<?php

	$json = $_POST["data"];
	$url = urldecode($_POST["url"]);
	$ch = curl_init($url);
	curl_setopt( $ch, CURLOPT_HEADER, false );
	curl_setopt( $ch, CURLOPT_POST, true );
	curl_setopt( $ch, CURLOPT_POSTFIELDS, $json );
	curl_setopt($ch, CURLOPT_HTTPHEADER, Array('Content-Type: application/json'));
	curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10);
	$result = curl_exec($ch);
	curl_close($ch);
	
	$resultjson = substr($result, 3);
	
	echo $resultjson;
?>