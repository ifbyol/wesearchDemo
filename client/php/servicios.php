<?php
	$count = count($_POST);
	/*echo 'POST: ' . implode(",", $_POST);
	echo 'Count: ' . $count;*/
	$params = array_keys($_POST);
	$json = '';
	for($i=0;$i<$count;$i++) {
		if(strcmp($params[$i], 'url') != 0) {
			$json .= $params[$i] . '=' . $_POST[$params[$i]] . '&';
		}
	}
	$json = trim($json, "&");
	$url = urldecode($_POST["url"]);
	$ch = curl_init($url);
	curl_setopt( $ch, CURLOPT_HEADER, false );
	curl_setopt( $ch, CURLOPT_POST, true );
	curl_setopt( $ch, CURLOPT_POSTFIELDS, $json );
	curl_setopt($ch, CURLOPT_HTTPHEADER, Array('Content-Type: text/plain'));
	curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10);
	$result = curl_exec($ch);
	curl_close($ch);
	
	$resultjson = substr($result, 3);
	
	echo $resultjson;
?>