document.body.addEventListener('htmx:afterOnLoad', function() {
    adjustIndicatorHeight();
});

function adjustIndicatorHeight() {
    const indicators = document.querySelectorAll('span.htmx-indicator');
    indicators.forEach(indicator => {
        indicator.style.height = '0';
    });
}
