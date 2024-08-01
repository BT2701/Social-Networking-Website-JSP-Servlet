document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.search-left-content .btn').forEach(button => {
        button.addEventListener('click', function() {
            const type = this.getAttribute('datatype');
            fetchResults(type).then(() => {
                changeColorBtn();
            });
        });
    });

    function fetchResults(type) {
        const keyword = ' '; // Fixed keyword

        return fetch(`${window.location.origin}/search?keyword=${keyword}&type=${type}`)
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
                    console.error('Element with id "search-content" not found.');
                }
            })
            .catch(error => console.error('Error fetching results:', error));
    }

    function changeColorBtn() {
        document.querySelectorAll('.handle-request').forEach(button => {
            const buttonText = button.textContent; // Sử dụng textContent

            if (buttonText.trim() === "Remove friend") {
                button.classList.remove('btn-primary'); // Loại bỏ lớp btn-primary
                button.classList.add('btn-danger');     // Thêm lớp btn-danger
            }
            else if (buttonText.trim() === "Confirm"){
                button.classList.remove('btn-primary'); // Loại bỏ lớp btn-primary
                button.classList.add('btn-success');     // Thêm lớp btn-danger
            }
            else if(buttonText.trim()=== "Cancel request"){
                button.classList.remove('btn-primary'); // Loại bỏ lớp btn-primary
                button.classList.add('btn-secondary');
            }
        });
    }
});


document.addEventListener('DOMContentLoaded', function (){
    document.querySelectorAll('.handle-request').forEach(button=>{
        const buttonText = button.textContent; // Sử dụng textContent

        if (buttonText.trim() === "Remove friend") {
            button.classList.remove('btn-primary'); // Loại bỏ lớp btn-primary
            button.classList.add('btn-danger');     // Thêm lớp btn-danger
        }
        else if (buttonText.trim() === "Confirm"){
            button.classList.remove('btn-primary'); // Loại bỏ lớp btn-primary
            button.classList.add('btn-success');     // Thêm lớp btn-danger
        }
        else if(buttonText.trim()=== "Cancel request"){
            button.classList.remove('btn-primary'); // Loại bỏ lớp btn-primary
            button.classList.add('btn-secondary');
        }
    });


});
document.querySelectorAll('.btn.btn-primary.handle-request').forEach(button => {
    button.addEventListener('click', function() {
        const action = this.innerText.trim();
        const friendId = this.getAttribute('data-friend-id');

        let url = '';
        if (action === 'Add friend') {
            url = `${window.location.origin}/search/addFriend`;
        } else if (action === 'Remove friend') {
            url = `${window.location.origin}/search/removeFriend`;
        } else if(action==="Cancel request"){
            url= `${window.location.origin}/search/cancel`;
        } else if(action==="Confirm"){
            url=`${window.location.origin}/search/confirm`;
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
                        switch (this.innerText) {
                            case 'Add friend':
                                this.innerText = 'Cancel request';
                                button.classList.remove('btn-primary');
                                button.classList.add('btn-secondary');
                                break;
                            case 'Remove friend':
                                this.innerText = 'Add friend';
                                button.classList.remove('btn-danger');
                                button.classList.add('btn-primary');
                                break;
                            case 'Cancel request':
                                this.innerText = 'Add friend';
                                button.classList.remove('btn-secondary');
                                button.classList.add('btn-primary');
                                break;
                            case 'Confirm':
                                this.innerText = 'Remove friend';
                                button.classList.remove('btn-success');
                                button.classList.add('btn-danger');
                                break;
                            default:
                                console.log('Unknown button state');
                        }
                    } else {
                        // Xử lý lỗi nếu có
                        console.error('Action failed');
                    }
                })
                .catch(error => console.error('Error:', error));
        }
    });
});

document.querySelectorAll('.refuse').forEach(button=>{
    button.addEventListener('click', function (){
        const friendId = this.getAttribute('data-friend-id');
        let url=`${window.location.origin}/search/refuse`;
        if(url){
            fetch(url,{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ friendId: friendId })
            }).then(response => response.json())
                .then(data => {
                    if (data.success) {

                    } else {
                        // Xử lý lỗi nếu có
                        const parser = new DOMParser();
                        const doc = parser.parseFromString(data, 'text/html');

                        // Select the content of the element with class 'content'
                        const content = doc.querySelector('.search-content');
                        // Chọn một phần tử trên trang để hiển thị kết quả
                        const resultContainer = document.getElementById('search-content');
                        if (resultContainer) {
                            resultContainer.innerHTML = content.innerHTML; // Chèn mã HTML vào phần tử
                        } else {
                            console.error('Element with id "search-content" not found.');
                        }
                        console.error('Action failed');
                    }
                })
                .catch(error => console.error('Error:', error));
        }
    })
})