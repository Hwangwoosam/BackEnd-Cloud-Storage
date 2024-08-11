document.addEventListener('DOMContentLoaded',function() {
    const aside = document.querySelector('.aside');
    const section = document.querySelector('.section');

    const observer = new ResizeObserver(entries =>{
        for(let entry of entries){
            const width = entry.contentRect.width;
            const maxWidth = section.offsetWidth*0.3;

            if(width > maxWidth){
            aside.style.width = maxWidth + 'px';
            }
        }
    });

    observer.observe(aside);
});

document.querySelectorAll('.drive > span').forEach(item => {
  item.addEventListener('click', function() {
    const submenu = this.nextElementSibling;
    if (submenu.style.display === 'block') {
      submenu.style.display = 'none';
      this.style.transform = 'rotate(0deg)';
    } else {
      submenu.style.display = 'block';
      this.style.transform = 'rotate(180deg)';
    }
  });
})

//
//function deleteFunc(fileSeqVal){
//      var userNameVal = document.getElementById("userName").getAttribute('value');
//      var includeDirVal = document.getElementById("includeDir").getAttribute('value');
//      var form = document.createElement('form');
//      form.action = "/file/delete/" + fileSeqVal;
//      form.method = 'post';
//
//      var userName = document.createElement('input');
//      userName.type = 'hidden';
//      userName.name = 'userName';
//      userName.value = userNameVal;
//      form.appendChild(userName);
//
//      var fileSeq = document.createElement('input');
//      fileSeq.type = 'hidden';
//      fileSeq.name = 'fileSeq';
//      fileSeq.value = fileSeqVal;
//      form.appendChild(fileSeq);
//
//      var curDir = document.createElement('input');
//      curDir.type = 'hidden';
//      curDir.name = 'includeDir';
//      curDir.value = includeDirVal;
//      form.appendChild(curDir);
//
//      document.body.appendChild(form);
//      form.submit();
//  }
//
//function chooseFolder(fileSeqVal){
//    var userNameVal = document.getElementById("userName").getAttribute('value');
//    var includeDirVal = document.getElementById("includeDir").getAttribute('value');
//
//    var userName = document.createElement('input');
//    userName.type = 'hidden';
//    userName.name = 'userName';
//    userName.value = userNameVal;
//    form.appendChild(userName);
//
//    var fileSeq = document.createElement('input');
//    fileSeq.type = 'hidden';
//    fileSeq.name = 'fileSeq';
//    fileSeq.value = fileSeqVal;
//    form.appendChild(fileSeq);
//
//    var curDir = document.createElement('input');
//    curDir.type = 'hidden';
//    curDir.name = 'includeDir';
//    curDir.value = includeDirVal;
//    form.appendChild(curDir);
//}
//
//$(document).ready(function(){
//
//  $(document).contextmenu(function(e){
//    var container = $(document); // 컨텍스트 메뉴를 표시할 영역에 대한 선택자
//
//    var rows = container.find('.edrive-table-data-row');
//    var closestRow = findClosestRow(e.pageX, e.pageY, rows);
//
//     if(closestRow != null){
//        var fileSeqVal = closestRow.attr('value');
//        var userNameVal = document.getElementById("userName").getAttribute('value');
//        var includeDirVal = document.getElementById("includeDir").getAttribute('value');
//
//        var params ={
//            fileSeq: fileSeqVal,
//            userName: userNameVal
//        };
//        var queryString = Object.keys(params).map(function(key){
//            return key + '=' + params[key];
//        }).join('&');
//
//        document.getElementById("delete").setAttribute('onclick',"deleteFunc("+ fileSeqVal +")");
//        document.getElementById("download").setAttribute('href','/file/download?' + queryString);
//        document.getElementById("move").setAttribute('href','/file/download/' + fileSeqVal);
//        document.getElementById("copy").setAttribute('href','/file/copy/' + fileSeqVal);
//     }
//    var winWidth = $(document).width();
//    var winHeight = $(document).height();
//
//    var posX = e.pageX;
//    var posY = e.pageY;
//
//    var menuWidth = $(".contextmenu").width();
//    var menuHeight = $(".contextmenu").height();
//    var secMargin = 10;
//    if(posX + menuWidth + secMargin >= winWidth
//    && posY + menuHeight + secMargin >= winHeight){
//      posLeft = posX - menuWidth - secMargin + "px";
//      posTop = posY - menuHeight - secMargin + "px";
//    }
//    else if(posX + menuWidth + secMargin >= winWidth){
//      posLeft = posX - menuWidth - secMargin + "px";
//      posTop = posY + secMargin + "px";
//    }
//    else if(posY + menuHeight + secMargin >= winHeight){
//      posLeft = posX + secMargin + "px";
//      posTop = posY - menuHeight - secMargin + "px";
//    }
//    else {
//      posLeft = posX + secMargin + "px";
//      posTop = posY + secMargin + "px";
//    };
//    $(".contextmenu").css({
//      "left": posLeft,
//      "top": posTop
//    }).show();
//
//    return false;
//  });
//
//  $(document).click(function(){
//    $(".contextmenu").hide();
//  });
//
//  function findClosestRow(mouseX, mouseY, rows) {
//      var closestRow = null;
//
//      rows.each(function() {
//        var row = $(this);
//        var rect = this.getBoundingClientRect();
//        var width = rect.left + rect.width;
//        var height = rect.top + rect.height;
//        if(rect.left <= mouseX && mouseX <= width && rect.top <= mouseY && mouseY <= height){
//            closestRow = row;
//        }
//      });
//
//      return closestRow;
//  }
//});
