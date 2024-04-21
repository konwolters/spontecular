document.body.addEventListener('htmx:beforeRequest', function() {
    document.getElementById('loadingIndicator').classList.remove('hidden');
});

document.body.addEventListener('htmx:beforeSwap', function() {
    document.querySelectorAll('.continueButton').forEach(button => { button.remove()});
});

document.body.addEventListener('htmx:afterRequest', function() {
    document.getElementById('loadingIndicator').classList.add('hidden');
});