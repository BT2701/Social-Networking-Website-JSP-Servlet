//XỬ LÝ THÊM HÌNH ẢNH VÀ VIDEO CHO BÀI POST
document.getElementById("chooseFileIcon").onclick = function () {
  document.getElementById("fileInput").click();
};

document.getElementById("fileInput").onchange = function (event) {
  const files = event.target.files;
  const containImgVideo = document.querySelector(".contain-img-video");

  // Iterate over each selected file
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
          mediaElement.src = e.target.result;
          mediaElement.alt = file.name;
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

  // Reset the file input to allow re-selection of the same file
  event.target.value = "";
};

//XỬ LÝ HIỂN THỊ BOX NOTIFICATION,MESSENGER AND USER OPERATION
document.addEventListener("DOMContentLoaded", () => {
  // Function to handle dropdown toggling
  function setupDropdown(iconId, dropdownClass) {
    const icon = document.getElementById(iconId);
    const dropdown = document.querySelector(dropdownClass);

    // Toggle the dropdown visibility
    icon.addEventListener("click", (event) => {
      //   event.stopPropagation(); // Prevent the click event from propagating to the document
      dropdown.classList.toggle("d-block");
    });
    // Hide dropdown when clicking outside
    document.addEventListener("click", (event) => {
      if (!icon.contains(event.target) && !dropdown.contains(event.target)) {
        dropdown.classList.remove("d-block");
      }
    });
  }
  // Setup each icon with its corresponding dropdown
  setupDropdown("icon-messenger", ".messenger-box");
  setupDropdown("icon-notification", ".notification-box");
  setupDropdown("icon-user-operation", ".user-operation-box");
});

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
