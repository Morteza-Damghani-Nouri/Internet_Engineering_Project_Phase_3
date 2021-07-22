let image_counter = 0;
let products_array = [];
let number_of_products_in_each_page = 15;
let current_page = 1;
console.log(window.localStorage.getItem("authToken"));



function get_products()
{
    let link = "http://127.0.0.1:9950/main/";
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", link);

    xhttp.onload = function() {
        const result = JSON.parse(this.response);
        console.log(this.response);
        console.log(result);

        for(let i = 0; i < 45; ++i) {
            let temp = [];
            temp.push(result[i][0]);
            if(result[i][1].toString().includes("2")) {
                temp.push("۲۰۰۰۰۰ تومان");
            }
            else {
                if(result[i][1].toString().includes("5")) {
                    temp.push("۱۵۰۰۰۰ تومان");


                }
                else {
                    if(result[i][1].toString().includes("7")) {
                        temp.push("۱۷۰۰۰۰ تومان");



                }
            }}

            if(result[i][2] == "first") {
                temp.push("دسته بندی یک")

            }
            if(result[i][2] == "second") {
                temp.push("دسته بندی دو")

            }
            if(result[i][2] == "third") {
                temp.push("دسته بندی سه")

            }


            if (result[i][3].includes("mountain_climbing_bag")) {
                temp.push("کوله پشتی کوه نوردی");
            }
            let template = temp[1];
            temp[1] = temp[3];
            temp[3] = template;
            products_array.push(temp);

        }
        slides_show();
        products_renderer(current_page);
        paging_controller();

    }
    xhttp.open("POST", link);
    xhttp.setRequestHeader('Access-Control-Allow-Origin', link);
    xhttp.setRequestHeader('Access-Control-Allow-Credentials', 'true');
    xhttp.setRequestHeader("Usecase", "GetProduct");
    xhttp.setRequestHeader("BaseName", "dateAdded"); // or pictureAddress or Category_name
    xhttp.setRequestHeader("SortType", "0"); // or 1
    xhttp.send();


}

function price_sorter() {
    products_array = [];
    let link = "http://127.0.0.1:9950/main/";
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", link);

    xhttp.onload = function() {
        const result = JSON.parse(this.response);
        console.log(this.response);
        console.log(result);

        for(let i = 0; i < 45; ++i) {
            let temp = [];
            temp.push(result[i][0]);
            if(result[i][1].toString().includes("2")) {
                temp.push("۲۰۰۰۰۰ تومان");
            }
            else {
                if(result[i][1].toString().includes("5")) {
                    temp.push("۱۵۰۰۰۰ تومان");


                }
                else {
                    if(result[i][1].toString().includes("7")) {
                        temp.push("۱۷۰۰۰۰ تومان");



                    }
                }}

            if(result[i][2] == "first") {
                temp.push("دسته بندی یک")

            }
            if(result[i][2] == "second") {
                temp.push("دسته بندی دو")

            }
            if(result[i][2] == "third") {
                temp.push("دسته بندی سه")

            }


            if (result[i][3].includes("mountain_climbing_bag")) {
                temp.push("کوله پشتی کوه نوردی");
            }
            let template = temp[1];
            temp[1] = temp[3];
            temp[3] = template;
            products_array.push(temp);

        }
        slides_show();
        products_renderer(current_page);
        paging_controller();

    }
    xhttp.open("POST", link);
    xhttp.setRequestHeader('Access-Control-Allow-Origin', link);
    xhttp.setRequestHeader('Access-Control-Allow-Credentials', 'true');
    xhttp.setRequestHeader("Usecase", "GetProduct");
    xhttp.setRequestHeader("BaseName", "price"); // or pictureAddress or Category_name
    xhttp.setRequestHeader("SortType", "0"); // or 1
    xhttp.send();

}








function slides_show(){
    if(image_counter == 0){
        document.getElementById("clock_image").src = "Images/clock1.png";
        ++ image_counter;

    }
    else{
        if(image_counter == 1){
            document.getElementById("clock_image").src = "Images/clock2.png";
            ++ image_counter;

        }
        else{
            if(image_counter == 2){
                document.getElementById("clock_image").src = "Images/clock0.png";
                image_counter = 0;

            }
        }

    }
    setTimeout(slides_show, 10000);
}


function paging_controller() {

    if(current_page == 1) {
        document.getElementById("previous_page_button").style.display = "none";
        document.getElementById("next_page_button").style.display = "block";
        if(current_page * number_of_products_in_each_page == products_array.length) {
            document.getElementById("next_page_button").style.display = "none";

        }



    }
    else {
        let temp1 = current_page;
        ++temp1;
        if ((temp1 - 1) * number_of_products_in_each_page >= products_array.length) {
            document.getElementById("next_page_button").style.display = "none";
            document.getElementById("previous_page_button").style.display = "block";
        }
        else {
            document.getElementById("next_page_button").style.display = "block";
            document.getElementById("previous_page_button").style.display = "block";

        }

    }


    for(let temp = 1;temp <= 8; ++temp) {
        if ((temp - 1) * number_of_products_in_each_page < products_array.length) {
            let index = temp.toString();
            let visible_page_number = document.getElementById("page" + index);
            visible_page_number.style.borderStyle = "wave";
            visible_page_number.style.fontSize = "small";
            visible_page_number.style.fontWeight = "normal";
            visible_page_number.style.borderColor = "grey";
            visible_page_number.style.backgroundColor = "#e2e2e2";
            visible_page_number.style.display = "block";

        }
        else {
            let index = temp.toString();
            document.getElementById("page" + index).style.display = "none";

        }


    }
    let current_page_button = document.getElementById("page" + current_page.toString());
    current_page_button.style.backgroundColor = "silver";
    current_page_button.style.borderStyle = "solid";
    current_page_button.style.borderColor = "black";
    current_page_button.style.fontWeight = "bold";
    current_page_button.style.fontSize = "medium";



}

function category_sorter() {
    products_array = [];
    let first_category_element = document.getElementsByName("first_category")[0];
    let second_category_element = document.getElementsByName("second_category")[0];
    let third_category_element = document.getElementsByName("third_category")[0];
    let forth_category_element = document.getElementsByName("forth_category")[0];

    if(first_category_element.checked) {
        let link = "http://127.0.0.1:9950/main/";
        let xhttp = new XMLHttpRequest();
        xhttp.open("GET", link);

        xhttp.onload = function() {
            const result = JSON.parse(this.response);
            console.log(this.response);
            console.log(result);

            for(let i = 0; i < 45; ++i) {
                if(result[i][2] == "first"){
                    let temp = [];
                    temp.push(result[i][0]);
                    if(result[i][1].toString().includes("2")) {
                        temp.push("۲۰۰۰۰۰ تومان");
                    }
                    else {
                        if(result[i][1].toString().includes("5")) {
                            temp.push("۱۵۰۰۰۰ تومان");


                        }
                        else {
                            if(result[i][1].toString().includes("7")) {
                                temp.push("۱۷۰۰۰۰ تومان");



                            }
                        }}

                    if(result[i][2] == "first") {
                        temp.push("دسته بندی یک")

                    }
                    if(result[i][2] == "second") {
                        temp.push("دسته بندی دو")

                    }
                    if(result[i][2] == "third") {
                        temp.push("دسته بندی سه")

                    }


                    if (result[i][3].includes("mountain_climbing_bag")) {
                        temp.push("کوله پشتی کوه نوردی");
                    }
                    let template = temp[1];
                    temp[1] = temp[3];
                    temp[3] = template;
                    products_array.push(temp);

                }

            }
            slides_show();
            products_renderer(current_page);
            paging_controller();

        }
        xhttp.open("POST", link);
        xhttp.setRequestHeader('Access-Control-Allow-Origin', link);
        xhttp.setRequestHeader('Access-Control-Allow-Credentials', 'true');
        xhttp.setRequestHeader("Usecase", "GetProduct");
        xhttp.setRequestHeader("BaseName", "price"); // or pictureAddress or Category_name
        xhttp.setRequestHeader("SortType", "0"); // or 1
        xhttp.send();

    }
    if(second_category_element.checked) {
        let link = "http://127.0.0.1:9950/main/";
        let xhttp = new XMLHttpRequest();
        xhttp.open("GET", link);

        xhttp.onload = function() {
            const result = JSON.parse(this.response);
            console.log(this.response);
            console.log(result);

            for(let i = 0; i < 45; ++i) {
                if(result[i][2] == "second"){
                    let temp = [];
                    temp.push(result[i][0]);
                    if(result[i][1].toString().includes("2")) {
                        temp.push("۲۰۰۰۰۰ تومان");
                    }
                    else {
                        if(result[i][1].toString().includes("5")) {
                            temp.push("۱۵۰۰۰۰ تومان");


                        }
                        else {
                            if(result[i][1].toString().includes("7")) {
                                temp.push("۱۷۰۰۰۰ تومان");



                            }
                        }}

                    if(result[i][2] == "first") {
                        temp.push("دسته بندی یک")

                    }
                    if(result[i][2] == "second") {
                        temp.push("دسته بندی دو")

                    }
                    if(result[i][2] == "third") {
                        temp.push("دسته بندی سه")

                    }


                    if (result[i][3].includes("mountain_climbing_bag")) {
                        temp.push("کوله پشتی کوه نوردی");
                    }
                    let template = temp[1];
                    temp[1] = temp[3];
                    temp[3] = template;
                    products_array.push(temp);

                }

            }
            slides_show();
            products_renderer(current_page);
            paging_controller();

        }
        xhttp.open("POST", link);
        xhttp.setRequestHeader('Access-Control-Allow-Origin', link);
        xhttp.setRequestHeader('Access-Control-Allow-Credentials', 'true');
        xhttp.setRequestHeader("Usecase", "GetProduct");
        xhttp.setRequestHeader("BaseName", "price"); // or pictureAddress or Category_name
        xhttp.setRequestHeader("SortType", "0"); // or 1
        xhttp.send();

    }
    if(third_category_element.checked) {
        let link = "http://127.0.0.1:9950/main/";
        let xhttp = new XMLHttpRequest();
        xhttp.open("GET", link);

        xhttp.onload = function() {
            const result = JSON.parse(this.response);
            console.log(this.response);
            console.log(result);

            for(let i = 0; i < 45; ++i) {
                if(result[i][2] == "third"){
                    let temp = [];
                    temp.push(result[i][0]);
                    if(result[i][1].toString().includes("2")) {
                        temp.push("۲۰۰۰۰۰ تومان");
                    }
                    else {
                        if(result[i][1].toString().includes("5")) {
                            temp.push("۱۵۰۰۰۰ تومان");


                        }
                        else {
                            if(result[i][1].toString().includes("7")) {
                                temp.push("۱۷۰۰۰۰ تومان");



                            }
                        }}

                    if(result[i][2] == "first") {
                        temp.push("دسته بندی یک")

                    }
                    if(result[i][2] == "second") {
                        temp.push("دسته بندی دو")

                    }
                    if(result[i][2] == "third") {
                        temp.push("دسته بندی سه")

                    }


                    if (result[i][3].includes("mountain_climbing_bag")) {
                        temp.push("کوله پشتی کوه نوردی");
                    }
                    let template = temp[1];
                    temp[1] = temp[3];
                    temp[3] = template;
                    products_array.push(temp);

                }

            }
            slides_show();
            products_renderer(current_page);
            paging_controller();

        }
        xhttp.open("POST", link);
        xhttp.setRequestHeader('Access-Control-Allow-Origin', link);
        xhttp.setRequestHeader('Access-Control-Allow-Credentials', 'true');
        xhttp.setRequestHeader("Usecase", "GetProduct");
        xhttp.setRequestHeader("BaseName", "price"); // or pictureAddress or Category_name
        xhttp.setRequestHeader("SortType", "0"); // or 1
        xhttp.send();




    }
    if(forth_category_element.checked) {
        let link = "http://127.0.0.1:9950/main/";
        let xhttp = new XMLHttpRequest();
        xhttp.open("GET", link);

        xhttp.onload = function() {
            const result = JSON.parse(this.response);
            console.log(this.response);
            console.log(result);

            for(let i = 0; i < 45; ++i) {
                if(result[i][2] == "forth"){
                    let temp = [];
                    temp.push(result[i][0]);
                    if(result[i][1].toString().includes("2")) {
                        temp.push("۲۰۰۰۰۰ تومان");
                    }
                    else {
                        if(result[i][1].toString().includes("5")) {
                            temp.push("۱۵۰۰۰۰ تومان");


                        }
                        else {
                            if(result[i][1].toString().includes("7")) {
                                temp.push("۱۷۰۰۰۰ تومان");



                            }
                        }}

                    if(result[i][2] == "first") {
                        temp.push("دسته بندی یک")

                    }
                    if(result[i][2] == "second") {
                        temp.push("دسته بندی دو")

                    }
                    if(result[i][2] == "third") {
                        temp.push("دسته بندی سه")

                    }


                    if (result[i][3].includes("mountain_climbing_bag")) {
                        temp.push("کوله پشتی کوه نوردی");
                    }
                    let template = temp[1];
                    temp[1] = temp[3];
                    temp[3] = template;
                    products_array.push(temp);

                }

            }
            slides_show();
            products_renderer(current_page);
            paging_controller();

        }
        xhttp.open("POST", link);
        xhttp.setRequestHeader('Access-Control-Allow-Origin', link);
        xhttp.setRequestHeader('Access-Control-Allow-Credentials', 'true');
        xhttp.setRequestHeader("Usecase", "GetProduct");
        xhttp.setRequestHeader("BaseName", "price"); // or pictureAddress or Category_name
        xhttp.setRequestHeader("SortType", "0"); // or 1
        xhttp.send();

    }
    if(!first_category_element.checked && !second_category_element.checked && !third_category_element.checked && !forth_category_element.checked) {
        get_products();

    }







    products_renderer(current_page);
    paging_controller();

}


function products_renderer(page_number) {
    if ((page_number - 1) * number_of_products_in_each_page < products_array.length) {
        let needed_products = [];
        for(let i = (page_number - 1) * number_of_products_in_each_page; i < page_number * number_of_products_in_each_page; ++ i) {
            needed_products.push(products_array[i]);

        }



        for (let i=1; i <= number_of_products_in_each_page; ++i){
            let index = i.toString();
            let temp = document.getElementById("product" + index);
            temp.style.display = "block";

            temp = document.getElementById("product_image" + index);
            temp.src = needed_products[i - 1][0];

            temp = document.getElementById("product_name" + index);
            temp.innerHTML = needed_products[i - 1][1];

            temp = document.getElementById("product_category" + index);
            temp.innerHTML = needed_products[i - 1][2];

            temp = document.getElementById("product_price" + index);
            temp.innerHTML = needed_products[i - 1][3];

        }
        for (let i=number_of_products_in_each_page + 1; i <= 15; ++i){
            let index = i.toString();
            let temp = document.getElementById("product" + index);
            temp.style.display = "none";

        }
        current_page = page_number;
        paging_controller();


    }


}



function slider_image_back() {

    if(image_counter == 0){
        document.getElementById("clock_image").src = "Images/clock2.png";
        image_counter = 2;

    }
    else {
        if(image_counter == 1){
            document.getElementById("clock_image").src = "Images/clock0.png";
            image_counter = 0;

        }
        else {
            if(image_counter == 2){
                document.getElementById("clock_image").src = "Images/clock1.png";
                image_counter = 1;

            }

        }

    }

}

async function gotoProfile()
{
    window.location.href = "../Part3/Profile_Bills.html"
}

async function gotoLogin()
{
    window.location.href = "../Part2/login_page.html"
}

showResultForGetUserInfo()
async function showResultForGetUserInfo() {
    let link = "http://127.0.0.1:9950/main/"
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", link);
    let loginStat = document.getElementById('loginStat');
    let menu_button = document.getElementById('menu_button');
    let drobptn = document.getElementById('drobptn');

    xhttp.onload = function() {
        const result = JSON.parse(this.response);
        if(parseInt(result.authToken) > 0)
        {
            drobptn.innerText = result.firstname
            loginStat.style.display = "inline-block"
            menu_button.style.display = "none"
        }
        console.log(this.response)
    }

    xhttp.open("POST", link);
    xhttp.setRequestHeader('Access-Control-Allow-Origin', link);
    xhttp.setRequestHeader('Access-Control-Allow-Credentials', 'true');
    xhttp.setRequestHeader("Usecase", "Getuserinfo");
    xhttp.setRequestHeader("AuthToken", window.localStorage.getItem("authToken"));

    xhttp.send();

}

async function logout()
{
    window.localStorage.setItem("authToken","0")
    window.location.href = "../Part1/main_page.html"
}

function slider_image_next() {

    if(image_counter == 0){
        document.getElementById("clock_image").src = "Images/clock1.png";
        image_counter = 1;

    }
    else {
        if(image_counter == 1){
            document.getElementById("clock_image").src = "Images/clock2.png";
            image_counter = 2;

        }
        else {
            if(image_counter == 2){
                document.getElementById("clock_image").src = "Images/clock0.png";
                image_counter = 0;

            }

        }

    }

}




function change_paging_button_handler() {
    let desired_value = document.getElementById("number_of_items").value;
    if(desired_value == 15 || desired_value == 9 || desired_value == 6) {
        number_of_products_in_each_page = parseInt(desired_value);
        products_renderer(current_page);
        paging_controller();



    }

}

function paging_button_handler(input_string) {

    if(input_string == "next") {
        let temp = current_page;
        ++temp;
        if((temp - 1) * number_of_products_in_each_page < 45){
            current_page = temp;
            products_renderer(current_page);
            paging_controller();

        }


    }
    if(input_string == "previous") {
        if(current_page != 1) {
            --current_page;
            products_renderer(current_page);
            paging_controller();

        }


    }

}


function onmouseenter_handler(element) {
    element.style.backgroundColor = "silver";
    element.style.borderStyle = "solid";
    element.style.borderColor = "black";
    element.style.fontWeight = "bold";
    element.style.fontSize = "medium";

}

function onmouseleave_handler(element) {
    let page_number = parseInt(element.id.charAt(element.id.length - 1));
    if(page_number != current_page) {
        element.style.borderStyle = "wave";
        element.style.fontSize = "small";
        element.style.fontWeight = "normal";
        element.style.borderColor = "grey";
        element.style.backgroundColor = "#e2e2e2";



    }


}

