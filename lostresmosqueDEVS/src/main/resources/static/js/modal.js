var btnModal = document.getElementById('btnModal');
var modal = document.getElementById('modal');
var closeModal = document.getElementsByClassName('close')[0];

btnModal.addEventListener('click', function() {
  modal.style.display = 'block';
});

closeModal.addEventListener('click', function() {
  modal.style.display = 'none';
});

window.addEventListener('click', function(event) {
  if (event.target == modal) {
    modal.style.display = 'none';
  }
});
