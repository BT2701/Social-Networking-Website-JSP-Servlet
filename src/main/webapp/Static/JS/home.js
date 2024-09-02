userId = 1; //lay iduser thong qua cookie
fileImage = null;

//XỬ LÝ THÊM HÌNH ẢNH VÀ VIDEO CHO BÀI POST
document.getElementById("chooseFileIcon").onclick = function () {
  document.getElementById("fileInput").click();
};

document.getElementById("fileInput").onchange = function (event) {
  const files = event.target.files;

  const containImgVideo = document.querySelector(".contain-img-video");
  //  // Iterate over each selected file
  Array.from(files).forEach((file) => {
    if (file) {
      const reader = new FileReader();
      reader.onload = function (e) {
        // Create the parent div with the class 'img-video-item'
        const imgVideoItem = document.createElement("div");
        imgVideoItem.className = "img-video-item position-relative";

        // Create media element based on file type
        let mediaElement;
        if (file.type.startsWith("image/")) {
          mediaElement = document.createElement("img");
          mediaElement.src = URL.createObjectURL(file);
          fileImage = file;
        } else if (file.type.startsWith("video/")) {
          mediaElement = document.createElement("video");
          mediaElement.src = e.target.result;
          mediaElement.controls = true;
          mediaElement.style.maxWidth = "100%";
        } else {
          return; // Skip unsupported file types
        }

        // Create the delete icon
        const deleteIcon = document.createElement("i");
        deleteIcon.className = "fa-solid fa-x icon-M position-absolute";

        // Set up the click event for the delete icon to remove the img-video-item
        deleteIcon.onclick = function () {
          imgVideoItem.remove();
          fileImage = null;
        };

        // Append media element and delete icon to the parent div
        imgVideoItem.appendChild(mediaElement);
        imgVideoItem.appendChild(deleteIcon);

        // Append the parent div to the container
        containImgVideo.appendChild(imgVideoItem);
      };
      reader.readAsDataURL(file);
    }
  });

  //  console.log(btoa(fi%A%les[0].name));

  // Reset the file input to allow re-selection of the same file
  event.target.value = "";
};

//test chat box
document.addEventListener("DOMContentLoaded", () => {
  const chatBoxes = document.querySelectorAll(".chat-box");

  chatBoxes.forEach((chatBox) => {
    const input = chatBox.querySelector(".chat-input input");
    const sendButton = chatBox.querySelector(".chat-input button");
    const messagesArea = chatBox.querySelector(".chat-messages");

    sendButton.addEventListener("click", () => {
      const messageText = input.value.trim();

      if (messageText) {
        const messageDiv = document.createElement("div");
        messageDiv.textContent = messageText;
        messagesArea.appendChild(messageDiv);
        input.value = ""; // Clear input field
        messagesArea.scrollTop = messagesArea.scrollHeight; // Auto-scroll to the bottom
      }
    });

    input.addEventListener("keypress", (event) => {
      if (event.key === "Enter") {
        event.preventDefault(); // Prevent the default newline behavior
        sendButton.click();
      }
    });
  });
});

//close the chatbox
document.addEventListener("DOMContentLoaded", () => {
  // Function to handle closing the chat box
  function setupCloseButton() {
    const closeButton = document.querySelector(".close-chat");
    const chatBox = closeButton.closest(".chat-box");

    closeButton.addEventListener("click", () => {
      chatBox.style.display = "none"; // Hide the chat box
    });
  }
  setupCloseButton();
});

//set appear chat box
function appearChatBox() {
  let boxChat = document.getElementById("chatbox");
  //   boxChat.classList.toggle("d-flex");
  boxChat.style.display = "flex";
}

//XỬ LÝ TẠO LẤY DỮ LIỆU TỪ MODAL TẠO POST
function getDataModalCreatePost() {
  // Get data from form
  let statusText = document.querySelector(".modal-body .status").value;
  let formData = new FormData();
  formData.append("image", fileImage);
  formData.append("statusText", statusText);
  fetch(`/api/post/create`, {
    method: "POST",
    body: formData,
  })
    .then((response) => {
//      return response.text();
 return response.json(); // Expecting JSON response from the server
    })
    .then((data) => {
        addPostToPage(data);
        console.log(data);
    })
    .catch((error) => {
      console.error("Error:", error);
    });
  resetFormCreatePost();
}

function addPostToPage(post) {
    const postItem = document.createElement("div");
    postItem.classList.add('post');
    postItem.setAttribute("data-post-id", post.id);

    postItem.innerHTML = `
        <div class="post-header">
            <div class="avt-user">
                <img src="/uploads/${post.user.avt}" alt="Contact 1" class="rounded-circle border border-1 me-3" width="40" height="40">
            </div>
            <div class="info-relative">
                <div class="user-name">
                    <span>${post.user.name}</span>
                </div>
                <div class="posting-time">${new Date(post.timeline).toLocaleString()}</div>
            </div>
        </div>
        <div class="post__desc">
            <p>${post.content}</p>
        </div>
        <div class="post-body">
            ${post.image ? `
            <div class="post__image">
                <img src="/uploads/${post.image}" alt="image">
            </div>` : ''}
        </div>
        <div class="post-footer">
            <div class="like-action">
                <span class="numberLike">0</span>
                <button class="like-button ${post.likedByUser ? '' : 'appear'}"> <i class="fa-regular fa-heart icon-L"></i></button>
                <button class="unlike-button ${post.likedByUser ? 'appear' : ''}"><i class="fa-solid fa-heart icon-L heart-red"></i></button>
            </div>
            <div class="comment-action">
                <span class="numberComment">0</span>
                <button class="comment-button"><i class="fa-regular fa-comment icon-L"></i></button>
            </div>
        </div>
        <div class="comment-section">
            <div class="post__comment-input">
                <input placeholder="Nhập bình luận..." type="text">
                <button class="submit-comment-Btn">Submit</button>
            </div>
            <div class="post__comment-list"></div>
        </div>
    `;

    const likeButton = postItem.querySelector('.like-button');
    const unlikeButton = postItem.querySelector('.unlike-button');

    // Event listeners for like/unlike buttons
    likeButton.addEventListener("click", () => {
        likeButton.classList.remove("appear");
            unlikeButton.classList.add("appear");
            handleLikeButtonClicked(postItem);
    });

    unlikeButton.addEventListener("click", () => {
         unlikeButton.classList.remove("appear");
            likeButton.classList.add("appear");
            handleUnlikeButtonClicked(postItem);
    });

    // Event listener for submitting comments
    postItem.querySelector('.submit-comment-Btn').addEventListener("click", () => {
        handleSubmitComment(postItem);
    });

    postItem.querySelector(".post__comment-input input").addEventListener("keyup", (event) => {
        if (event.key === "Enter") {
            handleSubmitComment(postItem);
        }
    });

    // Event listener for showing comment section
    postItem.querySelector(".comment-button").addEventListener("click", () => {
        handleCommentButtonClicked(postItem);
    });

    // Insert the new post at the top of the post list
    const postList = document.querySelector(".feed");
    postList.prepend(postItem);
}



function resetFormCreatePost() {
  document.querySelector(".modal-body .status").value = "";
  document.querySelector(".contain-img-video").innerHTML = "";
//  document.getElementById('formCreatePost').style.display = "none";
}

document.querySelectorAll(".post").forEach((post) => {
  // like and unlike
  const likeButton = post.querySelector(".like-button");
  const unlikeButton = post.querySelector(".unlike-button");
  const submitCommentButton = post.querySelector(".submit-comment-Btn");
  const inputComment = post.querySelector(".post__comment-input input");
  const commentButton = post.querySelector(".comment-button");
  likeButton.addEventListener("click", () => {
    likeButton.classList.remove("appear");
    unlikeButton.classList.add("appear");
    handleLikeButtonClicked(post);
//    alert("hehe");
  });
  unlikeButton.addEventListener("click", () => {
    unlikeButton.classList.remove("appear");
    likeButton.classList.add("appear");
    handleUnlikeButtonClicked(post);
//alert("hehe");
  });

  submitCommentButton.addEventListener("click", () => {
    handleSubmitComment(post);
  });

  inputComment.addEventListener("keyup", (event) => {
    if (event.key === "Enter") {
      handleSubmitComment(post);
    }
  });
  commentButton.addEventListener("click", () => {
    handleCommentButtonClicked(post);
  });
});



function handleLikeButtonClicked(post) {
  const postId = post.getAttribute("data-post-id"); //get post ID
  const likePreview = post.querySelector(".numberLike");
  const numOfLikes = parseInt(likePreview.textContent);
  fetch(`/api/post/like?userId=${userId}&postId=${postId}`, {
    method: "POST",
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.message === "Like successful") {
        likePreview.innerHTML = (numOfLikes + 1).toString();

        alert("Liked the post successfully!");
        postToLike = null;
      } else {
        alert(data.message);
      }
    })
    .catch((error) => console.error("Error:", error));
  //    console.log("Like button clicked" + postId + "and " + numOfLikes);
}

function handleUnlikeButtonClicked(post) {
  const postId = post.getAttribute("data-post-id"); //get post ID
  const likePreview = post.querySelector(".numberLike");
  const numOfLikes = parseInt(likePreview.textContent);
  fetch(`/api/post/unLike?userId=${userId}&postId=${postId}`, {
    method: "POST",
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.message === "UnLike successful") {
        likePreview.innerHTML = (numOfLikes - 1).toString();

        alert("Liked the post successfully!");
        postToUnLike = null;
      } else {
        alert(data.message);
      }
    })
    .catch((error) => console.error("Error:", error));
  //    console.log("Unlike button click" + postId + "and " + numOfLikes);
}

function handleCommentButtonClicked(post) {
  const commentSection = post.querySelector(".comment-section"); // Replace with your actual element ID
  commentSection.style.display =
    commentSection.style.display === "none" ||
    commentSection.style.display === ""
      ? "block"
      : "none";
  //      inputComment.style.display = "block";
}

function handleSubmitComment(post) {
  const inputComment = post.querySelector(".post__comment-input input");
  const commentContent = inputComment.value;
  if (commentContent !== "") {
    const postId = post.getAttribute("data-post-id");
    const cmtPreview = post.querySelector(".numberComment");
    const numOfComments = parseInt(cmtPreview.textContent);
    const commentList = post.querySelector(".post__comment-list");
    //alert("PostID  " + postId + " ComemntContent " + commentContent + " numOfComments " + numOfComments);
    const formData = new FormData();
    formData.append("userId", userId);
    formData.append("postId", postId);
    formData.append("commentContent", commentContent);
    fetch(`/api/post/comment`, {
      method: "POST",
      body: formData,
    })
      .then((response) => response.json())
      .then((comment) => {
        const cmt = document.createElement("div");
        cmt.classList.add("post__comment-item");
        cmt.innerHTML = `
                        <a href="/profile?userId=${comment.user.id}"><img src="/uploads/${comment.user.avt}" alt="post__comment"></a>
                           <div>
                               <a href="/profile?userId=${comment.user.id}">${comment.user.name}</a>
                               <p>${comment.content}</p>
                           </div>
                    `;

        cmtPreview.innerHTML = (numOfComments + 1).toString();
        commentList.prepend(cmt);
        inputComment.value = "";
        //                    alert("Commented on the post successfully!");
      })
      .catch((error) => console.error("Error:", error));
  }
}


