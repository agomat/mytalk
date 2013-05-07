$(document).ready(function() {
	video=document.getElementById("video_tutorial");
	video.setAttribute("onclick","javascript:startVideo(this)");
});

function startVideo(video){
	video.style.cursor="default";
	video.style.opacity="1";
	video.style.border="none";
	video.setAttribute("controls","");
	video.play();
}

