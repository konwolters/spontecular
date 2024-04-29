document.body.addEventListener('htmx:beforeRequest', function() {
    document.getElementById('loadingIndicator').classList.remove('hidden');
});

document.body.addEventListener('htmx:beforeSwap', function() {
    document.querySelectorAll('.continueButton').forEach(button => { button.remove()});
});

document.body.addEventListener('htmx:afterSettle', function(event) {
    // Check if the affected area is the constraintsDiv
    if (event.detail.elt.id === 'constraintsDiv') {
        var continueButton = document.querySelector('#constraintsDiv .continueButton');
        if (continueButton) {
            continueButton.style.display = 'none';  // This hides the button
        }
    }
});

document.body.addEventListener('htmx:afterRequest', function() {
    document.getElementById('loadingIndicator').classList.add('hidden');
});