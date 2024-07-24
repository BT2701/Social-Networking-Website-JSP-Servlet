document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.search-left-content .btn').forEach(button => {
        button.addEventListener('click', function() {
            const type = this.getAttribute('datatype');
            fetchResults(type);
        });
    });

    function fetchResults(type) {
        const keyword = ' '; // Fixed keyword

        fetch(`${window.location.origin}/search?keyword=${keyword}&type=${type}`)
            .then(response => response.text())
            .then(data => {
                window.location.href = 'http://localhost:8080/search?keyword='+keyword+'&type='+type;
            })
            .catch(error => console.error('Error fetching results:', error));
    }
});