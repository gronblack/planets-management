let ctx = {
    page: 0,
    tableDone: false,
    modalBlock: null,
    editablePros: "",
    countParams: "",
    useCommonSuffix: true,
    createPaging: true,
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

function updateCommonSuffix() {
    let orderNode = document.getElementById("order");
    let sizeNode = document.getElementById("size");

    let order = orderNode ? orderNode.value : "";
    ctx.size = sizeNode ? parseInt(sizeNode.value) : 0;
    ctx.suffix = "?order=" + order + "&size=" + ctx.size + "&page=" + ctx.page;
}

function loadContent() {
    if (ctx.useCommonSuffix) {
        updateCommonSuffix();
    }
    let suffix = prepareAndGetSpecialSuffix();
    let objects = JSON.parse(get(ctx.ajaxPath + suffix).responseText);

    if (ctx.createPaging) {
        let objectsCount = get(ctx.ajaxPath + "/count" + ctx.countParams).responseText;
        createPaging(ctx.size, objectsCount, ctx.page);
    }

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
                let node;
                if (ctx.editablePros.includes(prop)) {
                    node = createInput("number", objects[i][prop], "form-control text-end w-25");
                    node.addEventListener('change', e => setLordRow(e.target, objects[i]['id']));
                } else {
                    node = document.createTextNode(objects[i][prop]);
                }
                td.appendChild(node);
                tr.appendChild(td);
            }

            let tdClassName = "text-center col-md-1";
            let updateTd = createTd(tdClassName);
            updateTd.appendChild(createButton("btn btn-outline-primary", "Edit", e => updateRow(objects[i]['id'])));
            tr.appendChild(updateTd);

            let deleteTd = createTd(tdClassName);
            deleteTd.appendChild(createButton("btn btn-outline-danger", "Delete", e => deleteRow(objects[i]['id'])));
            tr.appendChild(deleteTd);

            tableBody.appendChild(tr);
        }
    }
}

function createInput(type, value, className) {
    let input = document.createElement("input");
    input.type = type;
    input.value = value;
    input.className = className;
    return input;
}

function createButton(className, text, clickHandler) {
    let btn = document.createElement("button");
    btn.className = className;
    btn.innerText = text
    btn.addEventListener('click', clickHandler);
    return btn;
}

function createTd(className) {
    let td = document.createElement("td");
    td.className = className;
    return td;
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

function setLordRow(node, id) {
    let lordId = node.value;
    let url = ctx.ajaxPath + id + "?lordId=" + lordId;
    let response = sendRequest("PATCH", url, null);
    if (response.status >= 200 && response.status <= 299) {
        alert("Set Lord[id=" + lordId + "] for Planet[id=" + id + "]. Success!");
    } else {
        alert("Response status: " + response.status);
    }
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

function normalizeURL(url) {
    return url.replaceAll("//", "/");
}

function sendRequest(type, url, body) {
    let Httpreq = new XMLHttpRequest();
    Httpreq.open(type, normalizeURL(url), false);
    if (type === "POST" || type === "PUT") {
        Httpreq.setRequestHeader("Content-type", "application/json;charset=UTF-8");
    }
    Httpreq.send(body);
    return Httpreq;
}