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
document.querySelectorAll('.btn.btn-primary.add-friend').forEach(button => {
    button.addEventListener('click', function() {
        const action = this.innerText.trim();
        const friendId = this.getAttribute('data-friend-id');

        let url = '';
        if (action === 'Thêm bạn bè') {
            url = `${window.location.origin}/search/addFriend`;
        } else if (action === 'Hủy kết bạn') {
            url = `${window.location.origin}/search/removeFriend`;
        }

        if (url) {
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ friendId: friendId })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        // Cập nhật nút và giao diện người dùng nếu cần thiết
                        this.innerText = action === 'Thêm bạn bè' ? 'Hủy kết bạn' : 'Thêm bạn bè';
                    } else {
                        // Xử lý lỗi nếu có
                        console.error('Action failed');
                    }
                })
                .catch(error => console.error('Error:', error));
        }
    });
});