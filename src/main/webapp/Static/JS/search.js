document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.search-left-content .btn').forEach(button => {
        button.addEventListener('click', function() {
            const type = this.getAttribute('data-type');
            fetchResults(type);
        });
    });

    function fetchResults(type) {
        const keyword = 'tr'; // Fixed keyword

        fetch(`${window.location.origin}/search?keyword=${keyword}&type=${type}`)
            .then(response => response.text())
            .then(data => {

            })
            .catch(error => console.error('Error fetching results:', error));
    }
});