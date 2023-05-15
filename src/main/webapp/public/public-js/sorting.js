/**
 * 
 */

function choosingSort() {
    var dropdown = document.getElementById("drop");
    var selectedOption = dropdown.options[dropdown.selectedIndex].text;
    console.log(selectedOption);
    if (selectedOption == "None") {
        console.log(selectedOption)
        document.getElementById("type-drop").style.display = "none";
        document.getElementById("category-drop").style.display = "none";
        document.getElementById("price-drop").style.display = "none";
        document.getElementById("rating-drop").style.display = "none";

        window.location.href = "sortProductServlet?sort-by="
                + selectedOption + "&sub-sort=None";
    } else if (selectedOption == "Price") {
        document.getElementById("price-drop").style.display = "";
        document.getElementById("type-drop").style.display = "none";
        document.getElementById("category-drop").style.display = "none";
        document.getElementById("rating-drop").style.display = "none";
    } else if (selectedOption == "Type") {
        document.getElementById("category-drop").style.display = "none";
        document.getElementById("type-drop").style.display = "";
        document.getElementById("price-drop").style.display = "none";
        document.getElementById("rating-drop").style.display = "none";
    } else if (selectedOption == "Category") {
        document.getElementById("type-drop").style.display = "none";
        document.getElementById("category-drop").style.display = "";
        document.getElementById("price-drop").style.display = "none";
        document.getElementById("rating-drop").style.display = "none";
    } else if (selectedOption == "Rating") {
        document.getElementById("rating-drop").style.display = "";
        document.getElementById("price-drop").style.display = "none";
        document.getElementById("type-drop").style.display = "none";
        document.getElementById("category-drop").style.display = "none";
    }
}

function choosingSortType() {
    var dropdown = document.getElementById("type-drop");
    var selectedOption = dropdown.options[dropdown.selectedIndex].text;

    if (selectedOption == "Menswear") {
        window.location.href = "sortProductServlet?sort-by="
                + selectedOption + "&sub-sort=Type";
    } else {
        console.log(selectedOption);
        window.location.href = "sortProductServlet?sort-by="
                + selectedOption + "&sub-sort=Type";
    }
}

function choosingSortCategory() {
    var dropdown = document.getElementById("category-drop");
    var selectedOption = dropdown.options[dropdown.selectedIndex].text;
    console.log(selectedOption);
    if (selectedOption == "Tops") {
        window.location.href = "sortProductServlet?sort-by="
                + selectedOption + "&sub-sort=Category";
    } else if (selectedOption == "Bottoms") {
        window.location.href = "sortProductServlet?sort-by="
                + selectedOption + "&sub-sort=Category";
    } else if (selectedOption == "Dresses") {
        window.location.href = "sortProductServlet?sort-by="
                + selectedOption + "&sub-sort=Category";
    } else if (selectedOption == "Underwear") {
        window.location.href = "sortProductServlet?sort-by="
                + selectedOption + "&sub-sort=Category";
    } else if (selectedOption == "Footwear") {
        window.location.href = "sortProductServlet?sort-by="
                + selectedOption + "&sub-sort=Category";
    } else if (selectedOption == "Accessories") {
        window.location.href = "sortProductServlet?sort-by="
                + selectedOption + "&sub-sort=Category";
    }
}

function choosingSortPrice() {
    var dropdown = document.getElementById("price-drop");
    var selectedOption = dropdown.options[dropdown.selectedIndex].text;

    if (selectedOption == "Low to High") {
        window.location.href = "sortProductServlet?sort-by="
                + selectedOption + "&sub-sort=Price";
    } else {
        window.location.href = "sortProductServlet?sort-by="
                + selectedOption + "&sub-sort=Price";
    }
}

function choosingSortRating() {
    var dropdown = document.getElementById("rating-drop");
    var selectedOption = dropdown.options[dropdown.selectedIndex].text;

    if (selectedOption == "Low to High") {
        window.location.href = "sortProductServlet?sort-by="
                + selectedOption + "&sub-sort=Rating";
    } else {
        window.location.href = "sortProductServlet?sort-by="
                + selectedOption + "&sub-sort=Rating";
    }
}