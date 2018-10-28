<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="refresh" content="0; url=download?filename=${filename}&artifactPath=${artifactPath}" />
		<style>
			body {
				font-family: sans-serif;
    			font-size: 180px;
			}
		</style>
		<script>
			(function() {
				setInterval(function() {
					var ellipses = document.getElementById('ellipses');
					if (ellipses.innerHTML.length >= 4) {
						ellipses.innerHTML = '';
					} else {
						ellipses.innerHTML = ellipses.innerHTML + '.';
					}
				}, 1000);
			})();
		</script>
	</head>
	<body><div>Downloading ${filename}<span id="ellipses"></span></div></body>
</html>