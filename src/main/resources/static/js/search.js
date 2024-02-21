const searchBox = document.getElementById("searchBox");
const searchResult = document.getElementById("searchResult");

searchBox.addEventListener("keyup", find);

searchBox.addEventListener("blur", event => {
    setTimeout(() => {    // my own idea to stop instant blur so that link can be opened
        searchResult.style.display = "none";
    }, 500);
});
searchBox.addEventListener("focus", event => {
    searchResult.style.display = "block";
});







function find(event) {
    // Clear the search results
    while (searchResult.firstChild) {
        searchResult.removeChild(searchResult.firstChild);
    }

    if (searchBox.value.trim() === '') {
        searchResult.style.display = "none";
    } else {
        searchResult.style.display = "block";
    }

    if (searchBox.value.trim() !== '')
        fetch(`http://localhost:8080/search/${searchBox.value}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);

                data.forEach(element => {
                    let form = document.createElement('form');
                    form.action = `/user/contact/${element.cid}`;
                    form.method = 'post';

                    let link = document.createElement('a');
                    link.href = '#';
                    link.className = 'list-group-item';
                    link.innerHTML = `<img src="/img/uploaded-images/${element.image}" alt="NA" class="profile-pic">` + ' ' + element.name;
                    link.onclick = function () { form.submit(); };

                    form.appendChild(link);

                    link.addEventListener("mouseover", event => {
                        link.classList.add("active");
                    })
                    link.addEventListener("mouseout", event => {
                        link.classList.remove("active");
                    })

                    searchResult.appendChild(form);
                });

            })
            .catch(e => console.error(e));
}
