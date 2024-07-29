
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MySocial</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <link rel="stylesheet" href="../../Static/CSS/home.css">
</head>
<style>

</style>

<body>
    <header class="text-white p-3 d-flex justify-content-between align-items-center">
        <div>
            <h1 class="h3 m-0">MySocial</h1>
        </div>
        <div class="d-flex">
            <div class="messenger" onclick="displayMessenger()">
                <i class="fas fa-envelope mx-2 icon-M icon-affect"></i>
                <div class="messenger-box">
                    <div class="messenger-box-header">
                        Chats
                    </div>
                    <div class="messenger-box-body custom-scrollbar">
                        <div class="inbox">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <div class="inbox-relative">
                                <div class="friend-name">
                                    <span>Nguyen Nhat Truong</span>
                                </div>
                                <div class="newest-chat">
                                    <span class="truncate-1line">Xi di choi bida khong, mia nay chan vl</span>
                                </div>
                            </div>
                        </div>

                        <div class="inbox">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <div class="inbox-relative">
                                <div class="friend-name">
                                    <span>Nguyen Nhat Truong</span>
                                </div>
                                <div class="newest-chat">
                                    <span class="truncate-1line">Xi di choi bida khong, mia nay chan vl</span>
                                </div>
                            </div>
                        </div>

                        <div class="inbox">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <div class="inbox-relative">
                                <div class="friend-name">
                                    <span>Nguyen Nhat Truong</span>
                                </div>
                                <div class="newest-chat">
                                    <span class="truncate-1line">Xi di choi bida khong, mia nay chan vl</span>
                                </div>
                            </div>
                        </div>

                        <div class="inbox">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <div class="inbox-relative">
                                <div class="friend-name">
                                    <span>Nguyen Nhat Truong</span>
                                </div>
                                <div class="newest-chat">
                                    <span class="truncate-1line">Xi di choi bida khong, mia nay chan vl</span>
                                </div>
                            </div>
                        </div>

                        <div class="inbox">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <div class="inbox-relative">
                                <div class="friend-name">
                                    <span>Nguyen Nhat Truong</span>
                                </div>
                                <div class="newest-chat">
                                    <span class="truncate-1line">Xi di choi bida khong, mia nay chan vl</span>
                                </div>
                            </div>
                        </div>

                        <div class="inbox">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <div class="inbox-relative">
                                <div class="friend-name">
                                    <span>Nguyen Nhat Truong</span>
                                </div>
                                <div class="newest-chat">
                                    <span class="truncate-1line">Xi di choi bida khong, mia nay chan vl</span>
                                </div>
                            </div>
                        </div>

                        <div class="inbox">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <div class="inbox-relative">
                                <div class="friend-name">
                                    <span>Nguyen Nhat Truong</span>
                                </div>
                                <div class="newest-chat">
                                    <span class="truncate-1line">Xi di choi bida khong, mia nay chan vl</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="messenger-box-footer text-center">
                        <button>See all chats</button>
                    </div>
                </div>
            </div>

            <div class="notification" onclick="displayNotification()">
                <i class="fas fa-bell mx-2 icon-M icon-affect"></i>
                <div class="notification-box">
                    <div class="notification-box-header">Notification</div>
                    <div class="notification-box-body custom-scrollbar">
                        <div class="notification-item d-flex">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <span class="notification-content truncate-2line">Nguyen Nhat Truong moi ban danh bac tai
                                song bac
                                201
                            </span>
                        </div>
                        <div class="notification-item d-flex">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <span class="notification-content truncate-2line">Nguyen Nhat Truong gui loi moi ket
                                ban</span>
                        </div>
                        <div class="notification-item d-flex">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <span class="notification-content truncate-2line">Nguyen Nhat Truong gui loi moi ket
                                ban</span>
                        </div>
                        <div class="notification-item d-flex">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <span class="notification-content truncate-2line">Nguyen Nhat Truong moi ban tham gia danh
                                nhau
                                tai
                                hoi nghi thuong dinh thuong nien
                            </span>
                        </div>
                        <div class="notification-item d-flex">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <span class="notification-content truncate-2line">Nguyen Nhat Truong gui loi moi ket
                                ban</span>
                        </div>
                        <div class="notification-item d-flex">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <span class="notification-content truncate-2line">Nguyen Nhat Truong gui loi moi ket
                                ban</span>
                        </div>
                        <div class="notification-item d-flex">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <span class="notification-content truncate-2line">Nguyen Nhat Truong gui loi moi ket
                                ban</span>
                        </div>
                        <div class="notification-item d-flex">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <span class="notification-content truncate-2line">Nguyen Nhat Truong share mot video</span>
                        </div>
                        <div class="notification-item d-flex">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <span class="notification-content truncate-2line">Nguyen Nhat Truong dang mot anh moi</span>
                        </div>
                        <button>See privious notifications</button>
                    </div>
                    <!-- <div class="notification-box-footer">

                    </div> -->
                </div>

            </div>
            <div class="user-operation" onclick="displayUserOperator()">
                <i class="fas fa-user mx-2 icon-M icon-affect"></i>
                <ul class="user-operation-box">
                    <li class="user-operation-box-item">
                        <i class="fa-regular fa-user"></i>
                        <span>Profile</span>
                    </li>
                    <li class="user-operation-box-item">
                        <i class="fa-solid fa-right-from-bracket"></i>
                        <span>Logout</span>
                    </li>
                    <li class="user-operation-box-item">
                        <i class="fa-solid fa-gear"></i>
                        <span>Setting</span>
                    </li>
                </ul>
            </div>
        </div>
    </header>
    <main class="container mt-4 mr-1">
        <div class="row">
            <div class="col-md-8 d-flex">
                <div class="col-md-4 navigation">

                </div>
                <div class="col-md-8">
                    <!-- Main feed content -->
                    <section class="post-form mb-4 d-flex">
                        <img src="../../Static/Images/pizzabanner.png" alt="Contact 3"
                            class="rounded-circle border border-1  me-3" width="40" height="40">
                        <input id="postContent" data-bs-toggle="modal" data-bs-target="#formCreatePost"
                            class="form-control rounded-pill" rows="3" placeholder="What's on your mind?"></input>
                    </section>
                    <section class="feed" id="feed">
                        <!-- Posts will appear here -->
                        <div class="post">
                            <div class="post-header">
                                <div class="avt-user">
                                    <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                        class="rounded-circle border border-1 me-3" width="40" height="40">
                                </div>
                                <div class="info-relative">
                                    <div class="name-user">
                                        <span>Nguyen Nhat Truong</span>
                                    </div>
                                    <div class="posting-time">8 hour ago</div>
                                </div>

                            </div>
                            <div class="post-body">
                                <img src="../../Static/Images/pizzabanner.png" alt="">
                                <div class="overlay">
                                    <div class="number-hidden-item">
                                        +6
                                    </div>
                                </div>
                            </div>
                            <div class="post-footer">
                                <!-- <i class="fa-regular fa-heart icon-L"></i> -->
                                <i class="fa-solid fa-heart icon-L heart-red"></i>
                                <i class="fa-regular fa-comment icon-L"></i>
                                <i class="fa-solid fa-retweet icon-L"></i>

                            </div>
                            <div class="comment-section d-flex">
                                <img src="../../Static/Images/pizzabanner.png" alt="Contact 3"
                                    class="rounded-circle border border-1  me-3" width="40" height="40">
                                <input class="form-control rounded-pill" rows="3"
                                    placeholder="Write your comment"></input>
                            </div>
                        </div>



                        <div class="post">
                            <div class="post-header">
                                <div class="avt-user">
                                    <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                        class="rounded-circle border border-1 me-3" width="40" height="40">
                                </div>
                                <div class="info-relative">
                                    <div class="name-user">
                                        <span>Nguyen Nhat Truong</span>
                                    </div>

                                    <div class="posting-time">8 hour ago</div>
                                </div>
                            </div>
                            <div class="post-body">
                                <img src="../../Static/Images/pizzabanner.png" alt="">
                            </div>
                            <div class="post-footer">
                                <i class="fa-regular fa-heart icon-L"></i>
                                <!-- <i class="fa-solid fa-heart icon-L heart-red"></i> -->
                                <i class="fa-regular fa-comment icon-L"></i>
                                <i class="fa-solid fa-retweet icon-L"></i>
                            </div>
                            <div class="comment-section d-flex">
                                <img src="../../Static/Images/pizzabanner.png" alt="Contact 3"
                                    class="rounded-circle border border-1  me-3" width="40" height="40">
                                <input class="form-control rounded-pill" rows="3"
                                    placeholder="Write your comment"></input>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
            <div class="col-md-4">
                <!-- Contacts and group chats -->
                <section class="contacts custom-scrollbar mb-4">
                    <h4>Contacts</h4>
                    <ul class="list-group">
                        <li class="list-group-item d-flex align-items-center border-0">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <span class="rounded-circle border border-1 active"></span>
                            <span>Nguyen Nhat Truong</span>
                        </li>
                        <li class="list-group-item d-flex align-items-center border-0">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <span>Nguyen Nhat Truong</span>
                        </li>

                    </ul>
                    <hr>
                    <h4>Group chat</h4>
                    <ul class="list-group">
                        <li class="list-group-item d-flex align-items-center border-0">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <span class="rounded-circle border border-1 active"></span>
                            <span>Group fintech</span>
                        </li>
                        <li class="list-group-item d-flex align-items-center border-0">
                            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                class="rounded-circle border border-1 me-3" width="40" height="40">
                            <span class="rounded-circle border border-1"></span>
                            <span>Group fintech</span>
                        </li>

                        <li class="list-group-item d-flex align-items-center border-0">
                            <i class="fa-solid fa-plus rounded-circle border border-1 me-2 p-2 ms-1 bg-secondary"></i>
                            <span>Create new group</span>
                        </li>
                    </ul>
                </section>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="formCreatePost" tabindex="-1" aria-labelledby="formCreatePostLabel"
                aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title text-center" id="formCreatePostLabel">Create post</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="creator d-flex">
                                <div class="avt-user">
                                    <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                        class="rounded-circle border border-1 me-3" width="40" height="40">
                                </div>
                                <div class="info-relative">
                                    <div class="name-user">
                                        <span>Nguyen Nhat Truong</span>
                                    </div>
                                    <div class="post-access">
                                        <button class="btn btn-secondary dropdown-toggle" type="button"
                                            data-bs-toggle="dropdown" aria-expanded="false">
                                            <i class="fa-solid fa-lock"></i>
                                            Private
                                        </button>
                                        <ul class="dropdown-menu">
                                            <li><a class="dropdown-item" href="#">

                                                    <i class="fa-solid fa-earth-americas"></i>
                                                    Public
                                                </a></li>
                                            <li><a class="dropdown-item" href="#">
                                                    <i class="fa-solid fa-user-group"></i>
                                                    Friends
                                                </a></li>
                                            <li><a class="dropdown-item" href="#">
                                                    <i class="fa-solid fa-lock"></i>
                                                    Private
                                                </a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <textarea class="status w-100 border-0" name="" id="" placeholder="What's on your mind?"
                                rows="3"></textarea>
                            <div class="contain-img-video">
                                <div class="img-video-item position-relative">
                                    <img src="../../Static/Images/pizzabanner.png" alt="">
                                    <i class="fa-solid fa-x icon-M position-absolute"></i>
                                </div>
                            </div>
                            <div class="addition d-flex border borde-1 p-2 rounded">
                                <span class="me-3">Add photo/video</span>
                                <i class="fa-regular fa-image icon-L" id="chooseFileIcon"></i>
                                <input type="file" id="fileInput">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary w-100">Post</button>
                        </div>

                    </div>
                </div>
            </div>
        </div>

    </main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="../../Static/JS/home.js"></script>
    <script>
        document.getElementById('chooseFileIcon').onclick = function () {
            document.getElementById('fileInput').click();
        };

        document.getElementById('fileInput').onchange = function (event) {
            const files = event.target.files;
            const containImgVideo = document.querySelector('.contain-img-video');
            // containImgVideo.innerHTML = ''; // Clear previous images

            Array.from(files).forEach(file => {
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        const img = document.createElement('img');
                        img.src = e.target.result;
                        containImgVideo.appendChild(img);
                    };
                    reader.readAsDataURL(file);
                }
            });
        };


        function selectOption(option) {
            document.getElementById('selectedOption').innerText = 'Selected Option: ' + option;
            document.getElementById('dropdownMenuButton').innerText = option;
        }



        //display notification
        function displayNotification() {
            let notificatioBox = document.querySelector(".notification-box");
            notificatioBox.classList.toggle("d-block");
        }

        function displayUserOperator() {
            let userOperator = document.querySelector(".user-operation-box");
            userOperator.classList.toggle("d-block");
        }

        function displayMessenger() {
            let userOperator = document.querySelector(".messenger-box");
            userOperator.classList.toggle("d-block");
        }
    </script>

</body>

</html>