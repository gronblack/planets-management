let ctx = {
    ajaxPath: "/api/" + document.getElementById("about").getAttribute("about") + "/",
    page: 0,
    size: 10,
    order: "",
    idle: null,
    tableDone: false,
    modalBlock: null,
    location: window.location.origin
}

document.addEventListener("DOMContentLoaded", function () {
    ctx.modalBlock = $('#modal');
    loadContent();

    $("[data-reload]").click(loadContent);
    $("[data-modal-create]").click(function() {
        modalShow();
    });
    $("[data-modal-close]").click(function() {
        modalHide();
    });
    $("[data-modal-send]").click(function() {
        save(getModalData());
    });
});

function loadContent() {
    let idle = document.getElementById("idle");
    if (idle !== null) {
        ctx.idle = !!idle.checked;
    }
    ctx.order = document.getElementById("order").value;
    ctx.size = parseInt(document.getElementById("size").value);

    let suffix = (ctx.idle ? "/idle" : "") + "?order=" + ctx.order + "&size=" + ctx.size + "&page=" + ctx.page;
    let objects = JSON.parse(get(ctx.ajaxPath + suffix).responseText);
    let objectsCount = get(ctx.ajaxPath + "/count" + suffix).responseText;
    createPaging(ctx.size, objectsCount, ctx.page);

    if (objects.length > 0) {
        let table = document.getElementById("mainTable");
        if (!ctx.tableDone) {
            createTableTH(table, objects[0], 2);
            ctx.tableDone = true;
        }
        let tableBody = table.querySelector("tbody");
        tableBody.innerHTML = "";

        for (let i = 0; i < objects.length; i++) {
            let tr = document.createElement("tr");
            for (let prop in objects[i]) {
                let td = document.createElement("td");
                td.appendChild(document.createTextNode(objects[i][prop]));
                tr.appendChild(td);
            }

            let updateBtn = document.createElement("button");
            updateBtn.className = "btn btn-outline-primary";
            updateBtn.innerText = "Edit";
            updateBtn.addEventListener('click', ev => updateRow(objects[i]['id']));
            let updateTd = document.createElement("td");
            updateTd.className = "text-center col-md-1";
            updateTd.appendChild(updateBtn);
            tr.appendChild(updateTd);

            let deleteBtn = document.createElement("button");
            deleteBtn.className = "btn btn-outline-danger";
            deleteBtn.innerText = "Delete";
            deleteBtn.addEventListener('click', ev => deleteRow(objects[i]['id']));
            let deleteTd = document.createElement("td");
            deleteTd.className = "text-center col-md-1";
            deleteTd.appendChild(deleteBtn);
            tr.appendChild(deleteTd);

            tableBody.appendChild(tr);
        }
    }
}

function modalShow(objectToUpdate) {
    let idDisplay, action;
    if (objectToUpdate == null) {
        idDisplay = "none";
        action = "create";
    } else {
        idDisplay = "flex";
        action = "update";
    }
    ctx.modalBlock.find('.modal-title').text(capitalizeFirstLetter(action));
    ctx.modalBlock.find("[data-input-group-id]").css("display", idDisplay);
    setModalFields(ctx.modalBlock, objectToUpdate);
    ctx.modalBlock.modal('show');
}

function modalHide() {
    ctx.modalBlock.modal('hide');
    ctx.modalBlock.find('form')[0].reset();
    ctx.modalBlock.find('[data-response-status]').text("").hide();
}

function updateRow(id) {
    modalShow( JSON.parse(get(ctx.ajaxPath + id).responseText) );
}

function deleteRow(id) {
    sendRequest("DELETE", ctx.ajaxPath + id, null);
    loadContent();
}

function createTableTH(table, object, additional) {
    let thead = table.querySelector("thead");
    let tr = document.createElement("tr");
    for (let prop in object) {
        let th = document.createElement("th");
        th.appendChild(document.createTextNode(capitalizeFirstLetter(prop)));
        tr.appendChild(th);
    }
    for (let i = 0; i < additional; i++) {
        let th = document.createElement("th");
        tr.appendChild(th);
    }
    thead.appendChild(tr);
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function createPaging(objectsInPage, objectsSummary, currentPage) {
    let pagingBar = document.getElementById("paging-bar");
    pagingBar.innerHTML = "";
    let pagesCount = objectsSummary / objectsInPage;
    if (pagesCount > 1) {
        for (let i = 0; i < pagesCount; i++) {
            let li = document.createElement("li");
            if (i === currentPage) {
                li.setAttribute("class", "page-item disabled");
            } else {
                li.setAttribute("class", "page-item");
            }
            let a = document.createElement("a");
            a.setAttribute("class", "page-link");
            a.setAttribute("href", "#");
            a.appendChild(document.createTextNode(i + 1));
            li.appendChild(a);
            pagingBar.appendChild(li);

            a.addEventListener('click', ev => {
                ctx.page = parseInt(ev.target.innerText) - 1;
                loadContent();
            });
        }
    }
}

function save(data) {
    let url, type;
    if (data['id'] == null || data['id'].length === 0) {
        url = ctx.ajaxPath;
        type = "POST";
    } else {
        url = ctx.ajaxPath + data['id'];
        type = "PUT";
    }
    let response = sendRequest(type, url, JSON.stringify(data));
    if (response.status >= 200 && response.status <= 299) {
        modalHide();
    } else {
        ctx.modalBlock.find('[data-response-status]').text("Response status: " + response.status).show();
    }
    loadContent();
}

function getModalData() {
    let data = {};
    $.each(ctx.modalBlock.find('form').serializeArray(), function(_, kv) {
        data[kv.name] = kv.value;
    });
    return data;
}

function get(requestUrl) {
    return sendRequest("GET", requestUrl, null);
}

function sendRequest(type, url, body) {
    let Httpreq = new XMLHttpRequest();
    Httpreq.open(type, url, false);
    if (type === "POST" || type === "PUT") {
        Httpreq.setRequestHeader("Content-type", "application/json;charset=UTF-8");
    }
    Httpreq.send(body);
    return Httpreq;
}