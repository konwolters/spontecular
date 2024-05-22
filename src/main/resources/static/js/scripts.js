document.body.addEventListener('htmx:beforeRequest', function() {
    document.getElementById('loadingIndicator').classList.remove('hidden');
});

// document.body.addEventListener('htmx:beforeSwap', function() {
//     document.querySelectorAll('.continueButton').forEach(button => { button.remove()});
// });

document.body.addEventListener('htmx:afterSettle', function(event) {
    if (event.detail.elt.id === 'constraintsDiv') {
        var continueButton = document.querySelector('#constraintsDiv .continueButton');
        if (continueButton) {
            continueButton.style.display = 'none';
        }
    }
});

document.addEventListener("htmx:afterSettle", function(evt) {
    console.log(evt.detail.target)
    var currentDiv = evt.detail.target;
    var nextDiv = currentDiv.nextElementSibling;

    if (nextDiv) {
        var continueButton = currentDiv.querySelector(".continueButton");

        if (nextDiv.innerHTML.trim() !== "") {
            if (continueButton) {
                continueButton.style.display = "none";
            }
        }
    }
});

document.body.addEventListener('htmx:afterRequest', function() {
    document.getElementById('loadingIndicator').classList.add('hidden');
});