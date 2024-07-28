document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.search-left-content .btn').forEach(button => {
        button.addEventListener('click', function() {
            const type = this.getAttribute('datatype');
            fetchResults(type);
            changeColorBtn();
        });
    });

    function fetchResults(type) {
        const keyword = ' '; // Fixed keyword

        fetch(`${window.location.origin}/search?keyword=${keyword}&type=${type}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text(); // Parse response as text (HTML)
            })
            .then(data => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(data, 'text/html');

                // Select the content of the element with class 'content'
                const content = doc.querySelector('.search-content');
                // Chọn một phần tử trên trang để hiển thị kết quả
                const resultContainer = document.getElementById('search-content');
                if (resultContainer) {
                    resultContainer.innerHTML = content.innerHTML; // Chèn mã HTML vào phần tử
                } else {
                    console.error('Element with id "search-container" not found.');
                }
            })
            .catch(error => console.error('Error fetching results:', error));
    }
    function changeColorBtn(){
        document.querySelectorAll('.add-friend').forEach(button=>{
            const buttonText = button.textContent; // Sử dụng textContent

            if (buttonText.trim() === "Hủy kết bạn") {
                button.classList.remove('btn-primary'); // Loại bỏ lớp btn-primary
                button.classList.add('btn-danger');     // Thêm lớp btn-danger
            }
        });
    }
});

document.addEventListener('DOMContentLoaded', function (){
    document.querySelectorAll('.add-friend').forEach(button=>{
        const buttonText = button.textContent; // Sử dụng textContent

        if (buttonText.trim() === "Hủy kết bạn") {
            button.classList.remove('btn-primary'); // Loại bỏ lớp btn-primary
            button.classList.add('btn-danger');     // Thêm lớp btn-danger
        }
    });


});