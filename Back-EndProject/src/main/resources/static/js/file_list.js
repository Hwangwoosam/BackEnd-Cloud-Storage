document.addEventListener('DOMContentLoaded', () => {
    const rows = document.querySelectorAll('tbody > tr');

    rows.forEach(row => {
        row.draggable = true;
        row.addEventListener("dragstart", e => {
            row.classList.add('dragging');
            e.dataTransfer.setData('text/plain', row.id);
        });

        row.addEventListener('dragend', () => {
            row.classList.remove('dragging')
        });

    });
});

function getDragAfterElement(container, y) {
         const draggableElements = [...container.querySelectorAll('.draggable:not(.dragging)')]

         return draggableElements.reduce((closest, child) => {
            const box = child.getBoundingClientRect() //해당 엘리먼트에 top값, height값 담겨져 있는 메소드를 호출해 box변수에 할당
            const offset = y - box.top - box.height/2 //수직 좌표 - top값 - height값 / 2의 연산을 통해서 offset변수에 할당
            if (offset < 0 && offset > closest.offset) { // (예외 처리) 0 이하 와, 음의 무한대 사이에 조건
               return { offset: offset, element: child } // Element를 리턴
            } else {
               return closest
            }
         }, { offset: Number.NEGATIVE_INFINITY }).element
      };

const dropzone = document.querySelector('tbody');

dropzone.addEventListener('dragover', e => {
    e.preventDefault();
    const afterElement = getDragAfterElement(dropzone, e.clientY);
    const draggable = document.querySelector('.dragging');
    if (afterElement == null) {
    	dropzone.appendChild(draggable);
    } else {
    	dropzone.insertBefore(draggable, afterElement);
    }
});
