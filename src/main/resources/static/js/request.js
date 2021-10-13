const get = function (url,data){
    return new Promise(resolve => {
        var ajax=new XMLHttpRequest();
        if (data && data!=={}){
            url+='?';
            for(let key in data){
                url = url + key + "=" + encodeURI(data[key]) + "&"
            }
        }
        ajax.open('get',url);
        ajax.send();
        ajax.onreadystatechange=function(){
            if(ajax.readyState==4&&ajax.status==200){
                resolve(JSON.parse(ajax.responseText))
            }
        }
    })
}

const post = function (url,data){
    return new Promise(resolve => {
        var ajax=new XMLHttpRequest();
        ajax.open('post',url);
        ajax.setRequestHeader("content-type","application/json");
        if(data){
            ajax.send(JSON.stringify(data));
        }else{
            ajax.send();
        }
        ajax.onreadystatechange=function(){
            if(ajax.readyState==4&&ajax.status==200){
                resolve(JSON.parse(ajax.responseText))
            }
        }
    })
}

function copyToClipboard(txt) {
    //开始复制链接
    var input = document.getElementById("input_copy");
    console.log(input)
    input.value = txt; // 修改文本框的内容
    input.select(); // 选中文本
    document.execCommand("copy");
}

const fromType = ["input","select","radio","date","datetime","checkbox ",
    "textarea","image","images","switch"]


const queryType = ["like","eq","lt","le","gt","ge","ne"]