
function search() {
    const searching = (document.getElementById("search-bar")).value
            .toLowerCase();
    const items = (document.getElementById("container"));
    //const items = (document.getElementById("everything"));

    const item = document.querySelectorAll(".product");
    //const item = document.querySelectorAll(".book1");

    const productName = document.getElementsByClassName("productName");
    //const productName = document.getElementsByClassName("productName");

    for (var i = 0; i < productName.length; i++) {
        let match = item[i].getElementsByClassName("productName")[0];

        if (match) {
            let textValue = match.textContent || match.innerHTML;

            console.log(textValue);
            if (textValue.toLowerCase().indexOf(searching) > -1) {
                item[i].style.display = "";
            } else {
                item[i].style.display = "none";
            }
        }
    }
}