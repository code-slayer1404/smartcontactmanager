const sidebar = document.getElementById("sidebar");
const content = document.getElementById("content");

const openbar = document.getElementById("openbar");

const temp = openbar.innerHTML;

function toggle() {
    // open sidebar
    if(window.getComputedStyle(sidebar).display === "none"){
        sidebar.style.display = "block";
        content.style.left= "15%";  //adjust the margin
        openbar.innerHTML="";
    }else{
        //close sidebar
        sidebar.style.display = "none";
        content.style.left= "0";   //go back to default
        openbar.innerHTML=temp;          //show the button again
    }
}