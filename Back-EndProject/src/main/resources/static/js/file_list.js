$(document).ready(function(){

  $(document).contextmenu(function(e){
    var container = $(document); // 컨텍스트 메뉴를 표시할 영역에 대한 선택자

    var rows = container.find('.edrive-table-data-row');
    var closestRow = findClosestRow(e.pageX, e.pageY, rows);

     if(closestRow != null){
        var fileSeqVal = closestRow.attr('value');
        var userNameVal = document.getElementById("contextMenu").getAttribute('value');
        console.log(userNameVal);
        var params ={
            fileSeq: fileSeqVal,
            userName: userNameVal
        };
        var queryString = Object.keys(params).map(function(key){
            return key + '=' + params[key];
        }).join('&');

        document.getElementById("delete").setAttribute('value',fileSeqVal);
        document.getElementById("download").setAttribute('href','/file/download?' + queryString);
        document.getElementById("move").setAttribute('href','/file/download/' + fileSeqVal);
        document.getElementById("copy").setAttribute('href','/file/copy/' + fileSeqVal);
     }
    var winWidth = $(document).width();
    var winHeight = $(document).height();

    var posX = e.pageX;
    var posY = e.pageY;

    var menuWidth = $(".contextmenu").width();
    var menuHeight = $(".contextmenu").height();
    var secMargin = 10;
    if(posX + menuWidth + secMargin >= winWidth
    && posY + menuHeight + secMargin >= winHeight){
      posLeft = posX - menuWidth - secMargin + "px";
      posTop = posY - menuHeight - secMargin + "px";
    }
    else if(posX + menuWidth + secMargin >= winWidth){
      posLeft = posX - menuWidth - secMargin + "px";
      posTop = posY + secMargin + "px";
    }
    else if(posY + menuHeight + secMargin >= winHeight){
      posLeft = posX + secMargin + "px";
      posTop = posY - menuHeight - secMargin + "px";
    }
    else {
      posLeft = posX + secMargin + "px";
      posTop = posY + secMargin + "px";
    };
    $(".contextmenu").css({
      "left": posLeft,
      "top": posTop
    }).show();

    return false;
  });

  $(document).click(function(){
    $(".contextmenu").hide();
  });

  function findClosestRow(mouseX, mouseY, rows) {
      var closestRow = null;

      rows.each(function() {
        var row = $(this);
        var rect = this.getBoundingClientRect();
        var width = rect.left + rect.width;
        var height = rect.top + rect.height;
        if(rect.left <= mouseX && mouseX <= width && rect.top <= mouseY && mouseY <= height){
            closestRow = row;
        }
      });

      return closestRow;
  }
});
